package com.dragorpion.sprintaxonsaga.ticket.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class TicketCreateCommand {

    @TargetAggregateIdentifier
    private String ticketId;

    private String name;

    public TicketCreateCommand(String ticketId, String name) {
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
