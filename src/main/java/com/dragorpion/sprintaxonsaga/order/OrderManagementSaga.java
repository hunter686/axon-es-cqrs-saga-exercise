package com.dragorpion.sprintaxonsaga.order;

import com.dragorpion.sprintaxonsaga.customer.command.OrderPayCommand;
import com.dragorpion.sprintaxonsaga.customer.event.OrderPaidEvent;
import com.dragorpion.sprintaxonsaga.customer.event.OrderPayFailedEvent;
import com.dragorpion.sprintaxonsaga.order.command.OrderFailCommand;
import com.dragorpion.sprintaxonsaga.order.command.OrderFinishCommand;
import com.dragorpion.sprintaxonsaga.order.event.OrderCreatedEvent;
import com.dragorpion.sprintaxonsaga.order.event.OrderFailedEvent;
import com.dragorpion.sprintaxonsaga.order.event.OrderFinishedEvent;
import com.dragorpion.sprintaxonsaga.ticket.command.OrderTicketMoveCommand;
import com.dragorpion.sprintaxonsaga.ticket.command.OrderTicketPreserveCommand;
import com.dragorpion.sprintaxonsaga.ticket.command.OrderTicketUnlockCommand;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketMovedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketPreserveFailedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketPreservedEvent;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class OrderManagementSaga {
    private static final Logger LOG = LoggerFactory.getLogger(OrderManagementSaga.class);

    @Autowired
    private transient CommandBus commandBus; // avoid serialization

    private String orderId;

    private String ticketId;

    private String customerId;

    private Double amount;

    @StartSaga
    @SagaEventHandler(associationProperty =  "orderId")
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.ticketId = event.getTicketId();
        this.customerId = event.getCustomerId();
        this.amount = event.getAmount();

        OrderTicketPreserveCommand command =
                new OrderTicketPreserveCommand(event.getTicketId(),
                        event.getOrderId(), event.getCustomerId());
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreservedEvent event) {
        OrderPayCommand command = new OrderPayCommand(customerId, orderId, amount);
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketPreserveFailedEvent event) {
        OrderFailCommand command = new OrderFailCommand(orderId, "Preserve Fail");
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPaidEvent event) {
        OrderTicketMoveCommand command = new OrderTicketMoveCommand(ticketId, orderId, customerId);
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderPayFailedEvent event) {
        OrderTicketUnlockCommand command = new OrderTicketUnlockCommand(ticketId, customerId);
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(command), LoggingCallback.INSTANCE);

        OrderFailCommand failCommand = new OrderFailCommand(orderId, "Paid fail");
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(failCommand), LoggingCallback.INSTANCE);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderTicketMovedEvent event) {
        OrderFinishCommand command = new OrderFinishCommand(orderId);
        commandBus.dispatch(GenericCommandMessage.asCommandMessage(command), LoggingCallback.INSTANCE);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFailedEvent event) {
        LOG.info("Order: {} failed.", event.getOrderId());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderFinishedEvent event) {
        LOG.info("Order: {} finished.", event.getOrderId());
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
