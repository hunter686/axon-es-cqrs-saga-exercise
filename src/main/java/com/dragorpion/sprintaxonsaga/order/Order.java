package com.dragorpion.sprintaxonsaga.order;

import com.dragorpion.sprintaxonsaga.order.command.OrderCreateCommand;
import com.dragorpion.sprintaxonsaga.order.command.OrderFailCommand;
import com.dragorpion.sprintaxonsaga.order.command.OrderFinishCommand;
import com.dragorpion.sprintaxonsaga.order.event.OrderCreatedEvent;
import com.dragorpion.sprintaxonsaga.order.event.OrderFailedEvent;
import com.dragorpion.sprintaxonsaga.order.event.OrderFinishedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Order {

    private static final Logger LOG = LoggerFactory.getLogger(Order.class);

    @AggregateIdentifier
    private String orderId;

    private String title;

    private String ticketId;

    private String customerId;

    private Double amount;

    private String status;

    private String reason;

    private ZonedDateTime createdDate;

    public Order() {
    }

    @CommandHandler
    public Order(OrderCreateCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId(),
                command.getCustomerId(), command.getTicketId(),
                command.getAmount(), command.getTitle(),
                ZonedDateTime.now()));
    }

    @CommandHandler
    public void on(OrderFinishCommand command) {
        apply(new OrderFinishedEvent(command.getOrderId()));
    }

    @CommandHandler
    public void on(OrderFailCommand command) {
        apply(new OrderFailedEvent(command.getOrderId(),
                command.getReason()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.ticketId = event.getTicketId();
        this.amount = event.getAmount();
        this.createdDate = event.getCreatedDate();
        this.customerId = event.getCustomerId();
        this.title = event.getTitle();
        this.status = "NEW";
        LOG.info("Executed Event:{} ", event);
    }

    @EventSourcingHandler
    public void on(OrderFinishedEvent event) {
        this.status = "FINISH";
        LOG.info("Executed Event:{}", event);
    }

    @EventSourcingHandler
    public void on(OrderFailedEvent event) {
        this.status = "FAILED";
        this.reason = event.getReason();
        LOG.info("Executed Event:{}", event);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
