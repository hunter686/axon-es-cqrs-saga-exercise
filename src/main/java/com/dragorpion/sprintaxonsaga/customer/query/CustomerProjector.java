package com.dragorpion.sprintaxonsaga.customer.query;

import com.dragorpion.sprintaxonsaga.customer.event.CustomerChargedEvent;
import com.dragorpion.sprintaxonsaga.customer.event.CustomerCreatedEvent;
import com.dragorpion.sprintaxonsaga.customer.event.CustomerDepositedEvent;
import com.dragorpion.sprintaxonsaga.customer.event.OrderPaidEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerProjector {

    @Autowired
    private CustomerEntityRepository repository;

    @EventHandler
    public void on(CustomerCreatedEvent event) {
        CustomerEntity customer = new CustomerEntity(event.getCustomerId(), event.getName(), 0d);
        repository.save(customer);
    }

    @EventHandler
    public void on(CustomerChargedEvent event) {
        String customerId = event.getCustomerId();
        CustomerEntity accountView = repository.getOne(customerId);

        Double newDeposit = accountView.getDeposit() - event.getAmount();
        accountView.setDeposit(newDeposit);
        repository.save(accountView);
    }

    @EventHandler
    public void on(CustomerDepositedEvent event) {
        String customerId = event.getCustomerId();
        CustomerEntity accountView = repository.getOne(customerId);

        Double newDeposit = accountView.getDeposit() + event.getAmount();
        accountView.setDeposit(newDeposit);
        repository.save(accountView);
    }

    @EventHandler
    public void on(OrderPaidEvent event) {
        String customerId = event.getCustomerId();
        CustomerEntity accountView = repository.getOne(customerId);

        Double newDeposit = accountView.getDeposit() - event.getAmount();
        accountView.setDeposit(newDeposit);
        repository.save(accountView);
    }
}
