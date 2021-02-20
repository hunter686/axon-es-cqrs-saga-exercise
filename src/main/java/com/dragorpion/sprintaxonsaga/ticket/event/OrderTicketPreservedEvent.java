package com.dragorpion.sprintaxonsaga.ticket.event;

public class OrderTicketPreservedEvent {

    private String orderId;

    private String customerId;

    private String ticketId;

    public OrderTicketPreservedEvent() {}

    public OrderTicketPreservedEvent(String orderId, String customerId, String ticketId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.ticketId = ticketId;
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
}
