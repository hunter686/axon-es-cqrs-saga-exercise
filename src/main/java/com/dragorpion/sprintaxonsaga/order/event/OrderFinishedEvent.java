package com.dragorpion.sprintaxonsaga.order.event;

public class OrderFinishedEvent {

    private String orderId;

    public OrderFinishedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
