package de.z1up.supercord.module.player.wrapper;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.module.player.o.Player;
import de.z1up.supercord.sql.SQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class PlayerWrapper {

    private final String TABLE_NAME = "players";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_UUID = "UUID";
    private final String ATTRIBUTE_GROUP = "PLAYER_GROUP";
    private final String ATTRIBUTE_LAST_SEEN = "LAST_SEEN";
    private final String ATTRIBUTE_PERMS = "PERMISSIONS";

    private SQL sql;

    public PlayerWrapper() {
        sql = Core.sql;
        createTable();
    }

    void createTable() {

        sql.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (" + ATTRIBUTE_ID + " int, "
                + ATTRIBUTE_UUID + " varchar(255), "
                + ATTRIBUTE_GROUP + " varchar(255), "
                + ATTRIBUTE_LAST_SEEN + " varchar(255), "
                + ATTRIBUTE_PERMS + " varchar(255)"
                + ")", null);
    }

    public void createPlayer(Player player) {

        int id = player.getId();
        UUID uuid = player.getUUID();
        String groupName = player.getGroup().getName();
        String lastSeen = player.getLastSeen().getName();
        ArrayList<String> permissions = player.getPermissions();
        String formedPerms = formPerms(permissions);

        sql.executeUpdate("INSERT INTO " + TABLE_NAME + " ("
                        + ATTRIBUTE_ID + ", "
                        + ATTRIBUTE_UUID + ", "
                        + ATTRIBUTE_GROUP + ", "
                        + ATTRIBUTE_LAST_SEEN + ", "
                        + ATTRIBUTE_PERMS + ")"
                        + " VALUES (?, ?, ?, ?, ?)",
                Arrays.asList(id, uuid, groupName, lastSeen, formedPerms));

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

    public Player getPlayer(ProxiedPlayer player) {
        return getPlayer(player.getUniqueId());
    }

    public Player getPlayer(UUID uuid) {

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME
                + " WHERE " + ATTRIBUTE_UUID +"=?", Arrays.asList(uuid));

        try {
            rs.next();

            int id = rs.getInt(ATTRIBUTE_ID);
            String groupName = rs.getString(ATTRIBUTE_GROUP);
            Group group = Core.permission.getGroupWrapper().getGroup(groupName);
            String lastSeenString = rs.getString(ATTRIBUTE_LAST_SEEN);
            ServerInfo lastSeen = ProxyServer.getInstance().getServerInfo(lastSeenString);
            String formedPerms = rs.getString(ATTRIBUTE_PERMS);
            ArrayList<String> perms = formPerms(formedPerms);

            Player player = new Player(id, uuid, lastSeen, group, perms);
            return player;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public void updatePlayer(Player player) {

        UUID uuid = player.getUUID();
        String groupName = player.getGroup().getName();
        String lastSeen = player.getLastSeen().getName();
        ArrayList<String> permissions = player.getPermissions();
        String formedPerms = formPerms(permissions);

        sql.executeUpdate("UPDATE " + TABLE_NAME + " SET "
                        + ATTRIBUTE_GROUP + "=?, "
                        + ATTRIBUTE_LAST_SEEN + "=?, "
                        + ATTRIBUTE_PERMS + "=?"
                        + " WHERE UUID=?",
                Arrays.asList(groupName, lastSeen, formedPerms, uuid));
    }

    public void updateLastSeen(Player player) {
        String lastSeen = player.getLastSeen().getName();
        sql.executeUpdate("UPDATE " + TABLE_NAME + " SET "
                        + ATTRIBUTE_LAST_SEEN + "=?"
                        + " WHERE UUID=?",
                Arrays.asList(lastSeen, player.getUUID()));
    }


    public boolean existsPlayer(Player player) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, player.getUUID());
    }

    public boolean existsPlayer(ProxiedPlayer player) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, player.getUniqueId());
    }

    public boolean existsPlayer(UUID uuid) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_UUID, uuid);
    }

    public int createNewID() {
        return sql.countTableSize(TABLE_NAME)+1;
    }

    public ArrayList<Player> getPlayerWithPermission() {

        ArrayList<Player> players = new ArrayList<>();
        ResultSet rs = sql.getResult("SELECT * FROM "
                + TABLE_NAME
                + " WHERE "
                + ATTRIBUTE_PERMS
                + " IS NOT NULL", null);

        try {
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString(ATTRIBUTE_UUID));
                Player player = getPlayer(uuid);
                players.add(player);
            }
        }catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return players;
    }

    public int countGroupMembers(Group group) {
        return sql.countResult(TABLE_NAME, ATTRIBUTE_GROUP, group.getName());
    }

    public ArrayList<Player> getPlayersInGroup(Group group) {

        ArrayList<Player> players = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME
                + " WHERE " + ATTRIBUTE_GROUP +"=?", Arrays.asList(group.getName()));

        try {
            while (rs.next()){
                int id = rs.getInt(ATTRIBUTE_ID);
                UUID uuid = UUID.fromString(rs.getString(ATTRIBUTE_UUID));
                String groupName = rs.getString(ATTRIBUTE_GROUP);
                String lastSeenString = rs.getString(ATTRIBUTE_LAST_SEEN);
                ServerInfo lastSeen = ProxyServer.getInstance().getServerInfo(lastSeenString);
                String formedPerms = rs.getString(ATTRIBUTE_PERMS);
                ArrayList<String> perms = formPerms(formedPerms);

                Player player = new Player(id, uuid, lastSeen, group, perms);
                players.add(player);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return players;
    }
}
