package com.dragorpion.sprintaxonsaga.order.query;

import com.dragorpion.sprintaxonsaga.order.event.OrderCreatedEvent;
import com.dragorpion.sprintaxonsaga.order.event.OrderFailedEvent;
import com.dragorpion.sprintaxonsaga.order.event.OrderFinishedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProjector {
    private static final Logger LOG = LoggerFactory.getLogger(OrderProjector.class);

    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity order = new OrderEntity();

        order.setOrderId(event.getOrderId());
        order.setTicketId(event.getTicketId());
        order.setAmount(event.getAmount());
        order.setCreatedDate(event.getCreatedDate());
        order.setCustomerId(event.getCustomerId());
        order.setTitle(event.getTitle());
        order.setStatus("NEW");
        orderEntityRepository.save(order);
        LOG.info("Executed Event:{} ", event);
    }

    @EventHandler
    public void on(OrderFinishedEvent event) {
        OrderEntity order = orderEntityRepository.getOne(event.getOrderId());
        order.setStatus("FINISHED");
        orderEntityRepository.save(order);
        LOG.info("Executed Event:{}", event);
    }

    @EventHandler
    public void on(OrderFailedEvent event) {
        OrderEntity order = orderEntityRepository.getOne(event.getOrderId());
        order.setStatus("FAILED");
        order.setReason(event.getReason());
        orderEntityRepository.save(order);
        LOG.info("Executed Event:{}", event);
    }
}
