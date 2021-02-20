package com.dragorpion.sprintaxonsaga.ticket.query;

import com.dragorpion.sprintaxonsaga.ticket.Ticket;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketMovedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketPreservedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.OrderTicketUnlockedEvent;
import com.dragorpion.sprintaxonsaga.ticket.event.TicketCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketProjector {
    private static final Logger LOG = LoggerFactory.getLogger(TicketProjector.class);

    @Autowired
    private TicketEntityRepository ticketEntityRepository;

    @EventHandler
    public void on(TicketCreatedEvent event) {
        TicketEntity ticket = new TicketEntity();
        ticket.setId(event.getTicketId());
        ticket.setName(event.getName());
        ticket.setOwner(null);
        ticket.setLockUser(null);
        ticketEntityRepository.save(ticket);
        LOG.info("Executed create ticket Event:{} ", event);
    }

    @EventHandler
    public void on(OrderTicketPreservedEvent event) {
        TicketEntity ticket = ticketEntityRepository.getOne(event.getTicketId());
        ticket.setLockUser(event.getCustomerId());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed Event:{} ", event);

    }

    @EventHandler
    public void on(OrderTicketMovedEvent event) {
        TicketEntity ticket = ticketEntityRepository.getOne(event.getTicketId());
        ticket.setLockUser(null);
        ticket.setOwner(event.getCustomerId());
        ticketEntityRepository.save(ticket);
        LOG.info("Executed Event:{} ", event);
    }

    @EventHandler
    public void on(OrderTicketUnlockedEvent event) {
        TicketEntity ticket = ticketEntityRepository.getOne(event.getTicketId());
        ticket.setLockUser(null);
        ticketEntityRepository.save(ticket);
        LOG.info("Executed Event:{} ", event);
    }
}
