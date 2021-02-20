package com.dragorpion.sprintaxonsaga.customer;

import com.dragorpion.sprintaxonsaga.customer.command.CustomerChargeCommand;
import com.dragorpion.sprintaxonsaga.customer.command.CustomerCreateCommand;
import com.dragorpion.sprintaxonsaga.customer.command.CustomerDepositCommand;
import com.dragorpion.sprintaxonsaga.customer.command.OrderPayCommand;
import com.dragorpion.sprintaxonsaga.customer.event.CustomerChargedEvent;
import com.dragorpion.sprintaxonsaga.customer.event.CustomerCreatedEvent;
import com.dragorpion.sprintaxonsaga.customer.event.CustomerDepositedEvent;
import com.dragorpion.sprintaxonsaga.customer.event.OrderPaidEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Customer {
    private static final Logger LOG = LoggerFactory.getLogger(Customer.class);

    @AggregateIdentifier
    private String customerId;

    private String username;

    private String password;

    private Double deposit;

    public Customer() { }

    @CommandHandler
    public Customer(CustomerCreateCommand command) {
        apply(new CustomerCreatedEvent(command.getCustomerId(), command.getName(), command.getPassword()));
    }

    @CommandHandler
    public void handle(CustomerChargeCommand command) {
        if (deposit - command.getAmount() >= 0) {
            apply(new CustomerChargedEvent(command.getCustomerId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("not enough amount");
        }
    }

    @CommandHandler
    public void handle(CustomerDepositCommand command) {
        apply(new CustomerDepositedEvent(command.getCustomerId(), command.getAmount()));
    }

    @CommandHandler
    public void handle(OrderPayCommand command) {
        if (deposit - command.getAmount() >= 0) {
            apply(new OrderPaidEvent(command.getOrderId(),
                    command.getCustomerId(), command.getAmount()));
        } else {
            throw new IllegalArgumentException("not enough amount");
        }
    }

    @EventSourcingHandler
    protected void on(CustomerCreatedEvent event) {
        this.customerId = event.getCustomerId();
        this.username = event.getName();
        this.password = event.getPassword();
        this.deposit = 0d;
    }

    @EventSourcingHandler
    protected void on(CustomerDepositedEvent event) {
        this.deposit = deposit + event.getAmount();
        LOG.info("Executed event : {} ", event);
    }

    @EventSourcingHandler
    protected void on(CustomerChargedEvent event) {
        this.deposit = deposit - event.getAmount();
        LOG.info("Executed event : {} ", event);
    }

    @EventSourcingHandler
    protected void on(OrderPaidEvent event) {
        this.deposit = deposit - event.getAmount();
        LOG.info("Executed event : {} ", event);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
}
