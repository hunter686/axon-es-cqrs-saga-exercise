package com.dragorpion.sprintaxonsaga.customer.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class CustomerCreateCommand {
    @TargetAggregateIdentifier
    private String customerId;

    private String name;

    private String password;

    public CustomerCreateCommand(String customerId, String name, String password)
    {
        this.customerId = customerId;
        this.name = name;
        this.password = password;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
