package de.z1up.supercord.module.player.o;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.permission.o.Group;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.UUID;

public class Player {

    private int id;
    private UUID UUID;
    private ServerInfo lastSeen;
    private Group group;
    private ArrayList<String> permissions;

    public Player(int id, UUID UUID, ServerInfo lastSeen, Group group, ArrayList<String> permissions) {
        this.id = id;
        this.UUID = UUID;
        this.group = group;
        this.lastSeen = lastSeen;
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public UUID getUUID() {
        return UUID;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void addPermission(String e) {
        permissions.add(e);
    }

    public void removePermission(String e) {
        permissions.remove(e);
    }

    public boolean hasPermission(String e) {
        return permissions.contains(e);
    }

    public ServerInfo getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(ServerInfo lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void updateLocation(ServerInfo serverInfo) {
        setLastSeen(serverInfo);
        Core.player.getPlayerWrapper().updateLastSeen(this);
    }

    public void leaveGroup() {
        this.group = Core.permission.getGroupWrapper().getDefaultGroup();
        update();
    }

    public void joinGroup(Group group) {
        this.group = group;
        update();
    }

    public void update() {
        Core.player.getPlayerWrapper().updatePlayer(this);
    }
}
