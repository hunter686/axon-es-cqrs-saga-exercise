package com.dragorpion.sprintaxonsaga.order.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class OrderCreateCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String customerId;

    private String ticketId;

    private Double amount;

    private String title;

    public OrderCreateCommand(String orderId, String customerId, String ticketId, Double amount, String title) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.title = title;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
