package com.dragorpion.sprintaxonsaga.order.event;

import java.time.ZonedDateTime;

public class OrderCreatedEvent {

    private String orderId;

    private String customerId;

    private String ticketId;

    private Double amount;

    private String title;

    private ZonedDateTime createdDate;

    public OrderCreatedEvent(String orderId, String customerId, String ticketId, Double amount, String title, ZonedDateTime createdDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.ticketId = ticketId;
        this.amount = amount;
        this.title = title;
        this.createdDate = createdDate;
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

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
