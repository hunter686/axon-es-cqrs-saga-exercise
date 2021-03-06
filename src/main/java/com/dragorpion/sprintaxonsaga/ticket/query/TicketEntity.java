package com.dragorpion.sprintaxonsaga.ticket.query;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tb_ticket")
public class TicketEntity {

    @Id
    private String id;

    private String name;

    private String lockUser;

    private String owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TicketEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lockUser='" + lockUser + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
