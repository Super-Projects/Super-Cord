package de.z1up.supercord.module.server.account;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.sql.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class AccountWrapper {

    private final String TABLE_NAME = "accounts";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_UUID = "UUID";
    private final String ATTRIBUTE_EMAIL = "EMAIL";
    private final String ATTRIBUTE_PW = "PASSWORD";

    private SQL sql;

    public AccountWrapper() {
        sql = Core.sql;
        createTable();
    }

    void createTable() {

        sql.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (" + ATTRIBUTE_ID + " int(10) unsigned NOT NULL AUTO_INCREMENT, "
                + ATTRIBUTE_UUID + " varchar(255) DEFAULT NULL, "
                + ATTRIBUTE_EMAIL + " varchar(255) DEFAULT NULL,"
                + ATTRIBUTE_PW + " varchar(255) DEFAULT NULL, PRIMARY KEY (`id`)"
                + ") DEFAULT CHARSET=utf8", null);
    }

    public void createAccount(Account account) {

        int id = account.getId();
        UUID uuid = account.getUuid();
        String email = account.getEmail();
        String pw = account.getPassword();

        sql.executeUpdate("INSERT INTO " + TABLE_NAME + " ("
                        + ATTRIBUTE_UUID + ", "
                        + ATTRIBUTE_EMAIL + ", "
                        + ATTRIBUTE_PW + ")"
                        + " VALUES (?, ?, ?)",
                Arrays.asList(uuid, email, pw));
    }

    public Account getAccount(int id) {

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME
                + " WHERE " + ATTRIBUTE_ID + "=?", Arrays.asList(id));

        try {
            rs.next();

            UUID uuid = UUID.fromString(rs.getString(ATTRIBUTE_UUID));
            String email = rs.getString(ATTRIBUTE_EMAIL);
            String pw = rs.getString(ATTRIBUTE_UUID);

            Account account = new Account(id, uuid, email, pw);
            return account;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public Account getAccount(UUID uuid) {

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME
                + " WHERE " + ATTRIBUTE_UUID + "=?", Arrays.asList(uuid));

        try {
            rs.next();

            int id = rs.getInt(ATTRIBUTE_ID);
            String email = rs.getString(ATTRIBUTE_EMAIL);
            String pw = rs.getString(ATTRIBUTE_UUID);

            Account account = new Account(id, uuid, email, pw);
            return account;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public boolean isEmailInUse(String email) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_EMAIL, email);
    }

    public boolean hasPlayerAccount(UUID uuid) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, uuid);
    }

}
