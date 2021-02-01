package de.z1up.supercord.module.permission.wrapper;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.sql.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class GroupWrapper {

    private final String TABLE_NAME = "groups";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_NAME = "NAME";
    private final String ATTRIBUTE_DISPLAY = "DISPLAY";
    private final String ATTRIBUTE_DESC = "DESCRIPTION";
    private final String ATTRIBUTE_CC = "CC";
    private final String ATTRIBUTE_PERMS = "PERMISSIONS";
    private final String ATTRIBUTE_PRIORITY = "PRIORITY";

    private SQL sql;

    public GroupWrapper() {
        sql = Core.sql;
        createTable();
    }

    void createTable() {

        sql.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (" + ATTRIBUTE_ID + " int, "
                + ATTRIBUTE_NAME + " varchar(255), "
                + ATTRIBUTE_DISPLAY + " varchar(255), "
                + ATTRIBUTE_DESC + " varchar(255), "
                + ATTRIBUTE_CC + " varchar(1), "
                + ATTRIBUTE_PERMS + " varchar(255), "
                + ATTRIBUTE_PRIORITY + " int"
                + ")", null);

    }

    public void createGroup(Group group) {

        int id = group.getID();
        String name = group.getName();
        String display = group.getDisplayName();
        String desc = group.getDescription();
        String cc = group.getCC();
        String perms = formPerms(group.getPermissions());
        int priority = group.getPriority();

        sql.executeUpdate("INSERT INTO " + TABLE_NAME
                        + "("
                        + ATTRIBUTE_ID + ", "
                        + ATTRIBUTE_NAME + ", "
                        + ATTRIBUTE_DISPLAY + ", "
                        + ATTRIBUTE_DESC + ", "
                        + ATTRIBUTE_CC + ", "
                        + ATTRIBUTE_PERMS + ", "
                        + ATTRIBUTE_PRIORITY + ") "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                Arrays.asList(id, name, display, desc, cc, perms, priority));



    }

    public void updateGroup(Group group) {

        int id = group.getID();
        String name = group.getName();
        String display = group.getDisplayName();
        String desc = group.getDescription();
        String cc = group.getCC();
        String perms = formPerms(group.getPermissions());
        int priority = group.getPriority();

        sql.executeUpdate("UPDATE "
                        + TABLE_NAME
                        + " SET "
                        + ATTRIBUTE_NAME + "=?, "
                        + ATTRIBUTE_DISPLAY + "=?, "
                        + ATTRIBUTE_DESC + "=?, "
                        + ATTRIBUTE_CC + "=?, "
                        + ATTRIBUTE_PERMS + "=?, "
                        + ATTRIBUTE_PRIORITY + "=? "
                        + "WHERE " + ATTRIBUTE_ID + "=?",
                Arrays.asList(name, display, desc, cc, perms, priority, id));

    }

    public void updatePerms(Group group) {

        int id = group.getID();
        String perms = formPerms(group.getPermissions());

        sql.executeUpdate("UPDATE "
                        + TABLE_NAME
                        + " SET "
                        + ATTRIBUTE_PERMS + "=? "
                        + "WHERE " + ATTRIBUTE_ID + "=?",
                Arrays.asList(perms, id));
    }

    public void refresh(Group group) {

        int id = group.getID();

        ResultSet rs = sql.getResult("SELECT * FROM "
                        + TABLE_NAME
                        + " WHERE "
                        + ATTRIBUTE_ID + "=?",
                Arrays.asList(id));

        try {
            rs.next();

            String name = rs.getString(ATTRIBUTE_NAME);
            String display = rs.getString(ATTRIBUTE_DISPLAY);
            String desc = rs.getString(ATTRIBUTE_DESC);
            String cc = rs.getString(ATTRIBUTE_CC);
            String permsAsString = rs.getString(ATTRIBUTE_PERMS);
            int priority = rs.getInt(ATTRIBUTE_PRIORITY);

            group.setName(name);
            group.setDisplayName(display);
            group.setDescription(desc);
            group.setCc(cc);
            group.setPriority(priority);

            ArrayList<String> perms = formPerms(permsAsString);

            ArrayList<String> oldPerms = group.getPermissions();

            for(String perm : perms) {
                if(!oldPerms.contains(perm)) {
                    group.addPermission(perm);
                }
            }

            for(String perm : oldPerms) {
                if(!perms.contains(perm)) {
                    group.removePermission(perm);
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void refreshPerms(Group group) {

        int id = group.getID();

        ResultSet rs = sql.getResult("SELECT "
                        + ATTRIBUTE_PERMS
                        + " FROM "
                        + TABLE_NAME
                        + " WHERE "
                        + ATTRIBUTE_ID + "=?",
                Arrays.asList(id));

        try {
            rs.next();

            String permsAsString = rs.getString(ATTRIBUTE_PERMS);
            ArrayList<String> perms = formPerms(permsAsString);

            ArrayList<String> oldPerms = group.getPermissions();

            for(String perm : perms) {
                if(!oldPerms.contains(perm)) {
                    group.addPermission(perm);
                }
            }

            for(String perm : oldPerms) {
                if(!perms.contains(perm)) {
                    group.removePermission(perm);
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void deleteGroup(Group group) {

        String id = group.getName();

        sql.executeUpdate("DELTE * FROM "
                        + TABLE_NAME
                        + " WHERE "
                        + ATTRIBUTE_ID + "=? ",
                Arrays.asList(id));

    }

    public Group getGroup(String name) {

        ResultSet rs = sql.getResult("SELECT * FROM "
                        + TABLE_NAME
                        + " WHERE "
                        + ATTRIBUTE_NAME + "=?",
                Arrays.asList(name));

        try {
            rs.next();

            int id = rs.getInt(ATTRIBUTE_ID);
            String display = rs.getString(ATTRIBUTE_DISPLAY);
            String desc = rs.getString(ATTRIBUTE_DESC);
            String cc = rs.getString(ATTRIBUTE_CC);
            String permsAsString = rs.getString(ATTRIBUTE_PERMS);
            int priority = rs.getInt(ATTRIBUTE_PRIORITY);

            ArrayList<String> perms = formPerms(permsAsString);

            Group group = new Group(id, name, display, desc, cc, perms, priority);
            return group;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public Group getGroup(int id) {

        ResultSet rs = sql.getResult("SELECT * FROM "
                        + TABLE_NAME
                        + " WHERE "
                        + ATTRIBUTE_ID + "=?",
                Arrays.asList(id));

        try {
            rs.next();

            String name = rs.getString(ATTRIBUTE_NAME);
            String display = rs.getString(ATTRIBUTE_DISPLAY);
            String desc = rs.getString(ATTRIBUTE_DESC);
            String cc = rs.getString(ATTRIBUTE_CC);
            String permsAsString = rs.getString(ATTRIBUTE_PERMS);
            int priority = rs.getInt(ATTRIBUTE_PRIORITY);

            ArrayList<String> perms = formPerms(permsAsString);

            Group group = new Group(id, name, display, desc, cc, perms, priority);
            return group;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public Collection<Group> getAllGroups() {

        Collection<Group> groups = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT * FROM "
                        + TABLE_NAME,
                null);

        try {
            while(rs.next()) {
                int id = rs.getInt(ATTRIBUTE_ID);
                String name = rs.getString(ATTRIBUTE_NAME);
                String display = rs.getString(ATTRIBUTE_DISPLAY);
                String desc = rs.getString(ATTRIBUTE_DESC);
                String cc = rs.getString(ATTRIBUTE_CC);
                String permsAsString = rs.getString(ATTRIBUTE_PERMS);
                int priority = rs.getInt(ATTRIBUTE_PRIORITY);

                ArrayList<String> perms = formPerms(permsAsString);

                Group group = new Group(id, name, display, desc, cc, perms, priority);
                groups.add(group);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return groups;
    }

    public boolean ccInUse(String cc) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_CC, cc);
    }

    public boolean existsGroup(Group group) {
        return existsGroup(group.getID());
    }

    public boolean existsGroup(int id) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, id);
    }

    public boolean existsGroup(String name) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_NAME, name);
    }

    public String formPerms(ArrayList<String> e) {
        String s = "";
        if(e != null && !e.isEmpty()) {
            for(String perm : e) {
                s = s + perm + ";";
            }
        }
        return s;
    }

    public ArrayList<String> formPerms(String s) {
        ArrayList<String> perms = new ArrayList<>();
        if(s != null) {
            String[] permString = s.split(";");
            for (String permission : permString) {
                perms.add(permission);
            }
        }
        return perms;
    }

    public boolean existsID(int id) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, id);
    }

    public int createNewID() {
        int id = new Random().nextInt(9999);
        if(existsGroup(id)) {
            return createNewID();
        }
        return id;
    }

    public boolean existsPriority(int priority) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_PRIORITY, priority);
    }

    public Group getDefaultGroup() {

        String name = "Player";
        String display = "Player";
        String desc = "This is the default player Group.";
        String cc = "7";
        int priority = 100;
        int id = createNewID();

        if (!existsGroup(name)) {
            Group group = new Group(id, name, display, desc, cc, null, priority);
            createGroup(group);
        }
        return getGroup(name);
    }

    public AtomicBoolean hasGroupPermission(Group group, String targetPerm) {
        AtomicBoolean contains = new AtomicBoolean(false);
        group.getPermissions().forEach(perm -> {
            if(perm.equals(targetPerm)) {
                contains.set(true);
            }
        });
        return contains;
    }

}
