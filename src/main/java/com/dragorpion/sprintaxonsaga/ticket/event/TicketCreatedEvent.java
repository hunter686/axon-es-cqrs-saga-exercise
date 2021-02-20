package com.dragorpion.sprintaxonsaga.ticket.event;

public class TicketCreatedEvent {

    private String ticketId;

    private String name;

    public TicketCreatedEvent() {}

    public TicketCreatedEvent(String ticketId, String name) {
        this.ticketId = ticketId;
        this.name = name;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
