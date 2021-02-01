package de.z1up.supercord.module.permission.o;

import com.sun.istack.internal.NotNull;
import de.z1up.supercord.core.Core;

import java.util.ArrayList;

public class Group {

    private int id;
    private String name;
    private String display;
    private String description;
    private String cc;
    private ArrayList<String> permissions;
    private int priority;

    public Group(@NotNull int id, String name, String display, String description, String cc, ArrayList<String> permissions, @NotNull int priority) {
        this.id = id;
        this.name = name;
        this.display = display;
        this.description = description;
        this.cc = cc;
        if(permissions == null) {
            permissions = new ArrayList<String>();
        } else {
            this.permissions = permissions;
        }
        this.priority = priority;
    }

    public int getID(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDisplayName(String displayName) {
        this.display = displayName;
    }

    public String getDisplayName() {
        return display;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCC() {
        return cc;
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

    public void update() {
        Core.permission.getGroupWrapper().updateGroup(this);
    }

    public void updatePerms() {
        Core.permission.getGroupWrapper().updatePerms(this);
    }

    public void refresh() {
        Core.permission.getGroupWrapper().refresh(this);
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void create() {
        if(!Core.permission.getGroupWrapper().existsGroup(this))
        Core.permission.getGroupWrapper().createGroup(this);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
