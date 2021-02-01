package de.z1up.supercord.module.server.account;

import de.z1up.supercord.core.Core;

import java.util.UUID;

public class Account {

    private int id;
    private UUID uuid;
    private String email;
    private String password;

    public Account(int id, UUID uuid, String email, String password) {
        this.id = id;
        this.uuid = uuid;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void create() {
        Core.server.getAccountWrapper().createAccount(this);
    }
}
