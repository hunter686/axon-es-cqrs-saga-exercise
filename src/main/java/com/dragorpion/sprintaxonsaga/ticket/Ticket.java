package com.dragorpion.sprintaxonsaga.ticket;

import com.dragorpion.sprintaxonsaga.ticket.command.OrderTicketMoveCommand;
import com.dragorpion.sprintaxonsaga.ticket.command.OrderTicketPreserveCommand;
import com.dragorpion.sprintaxonsaga.ticket.command.OrderTicketUnlockCommand;
import com.dragorpion.sprintaxonsaga.ticket.command.TicketCreateCommand;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketMovedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketPreserveFailedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketPreservedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketUnlockedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.TicketCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Ticket {
    private static final Logger LOG = LoggerFactory.getLogger(Ticket.class);

    @AggregateIdentifier
    private String ticketId;

    private String name;

    private String lockUser;

    private String owner;

    public Ticket() { }

    @CommandHandler
    public Ticket(TicketCreateCommand command) {
        apply(new TicketCreatedEvent(command.getTicketId(), command.getName()));
    }

    @CommandHandler
    public void handle(OrderTicketPreserveCommand command) {
        if (this.owner != null) {
            LOG.error("Ticket is owned.");
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
        } else if (this.lockUser != null
                && this.lockUser.equals(command.getCustomerId())) {
            LOG.info("duplicated command");
        } else if (this.lockUser != null
                && !this.lockUser.equals(command.getCustomerId())) {
            apply(new OrderTicketPreserveFailedEvent(command.getOrderId()));
        } else if (this.lockUser == null) {
            apply(new OrderTicketPreservedEvent(command.getOrderId(),
                    command.getCustomerId(), command.getTicketId()));
        }
    }

    @CommandHandler
    public void handle(OrderTicketMoveCommand command) {
        if (this.lockUser == null) {
            LOG.error("Invalid command, ticket not locked");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            LOG.error("Invalid command, ticket not locked by customer.");
        } else {
            apply(new OrderTicketMovedEvent(command.getOrderId(),
                    command.getCustomerId(), command.getTicketId()));
        }
    }

    public void handle(OrderTicketUnlockCommand command) {
        if (this.lockUser == null) {
            LOG.error("Invalid command, ticket not locked");
        } else if (!this.lockUser.equals(command.getCustomerId())) {
            LOG.error("Invalid command, ticket not locked by customer.");
        } else {
            apply(new OrderTicketUnlockedEvent(command.getTicketId()));
        }
    }

    @EventSourcingHandler
    public void on(TicketCreatedEvent event) {
        this.ticketId = event.getTicketId();
        this.name = event.getName();
        LOG.info("Executed event : {}", event);
    }

    @EventSourcingHandler
    public void on(OrderTicketPreservedEvent event) {
        this.lockUser = event.getCustomerId();
        LOG.info("Executed event : {}", event);
    }

    @EventSourcingHandler
    public void on(OrderTicketMovedEvent event) {
        this.lockUser = null;
        this.owner = event.getCustomerId();
        LOG.info("Executed event: {}", event);
    }

    @EventSourcingHandler
    public void on(OrderTicketUnlockedEvent event) {
        this.lockUser = null;
        LOG.info("Executed event : {}", event);
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

    public String getLockUser() {
        return lockUser;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
