package com.dragorpion.sprintaxonsaga.customer.event;

public class OrderPayFailedEvent {

    private String orderId;

    public OrderPayFailedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderPayFailedEvent{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
