package com.dragorpion.sprintaxonsaga.order;

import com.dragorpion.sprintaxonsaga.customer.CustomerController;
import com.dragorpion.sprintaxonsaga.customer.query.CustomerEntityRepository;
import com.dragorpion.sprintaxonsaga.order.command.OrderCreateCommand;
import com.dragorpion.sprintaxonsaga.order.query.OrderEntity;
import com.dragorpion.sprintaxonsaga.order.query.OrderEntityRepository;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CommandGateway commandGateway;

    /*@Autowired
    private QueryGateway queryGateway;
*/
    @Autowired
    private OrderEntityRepository orderEntityRepository;

    @PostMapping("")
    public void create(@RequestBody Order order) {
        UUID orderId = UUID.randomUUID();
        OrderCreateCommand command = new OrderCreateCommand(orderId.toString(),
                order.getCustomerId(), order.getTicketId(), order.getAmount(), order.getTitle());

        commandGateway.send(command, LoggingCallback.INSTANCE);
    }

    @GetMapping("/{orderId}")
    public OrderEntity get(@PathVariable String orderId) throws ExecutionException {
        return orderEntityRepository.getOne(orderId);
    }
}
