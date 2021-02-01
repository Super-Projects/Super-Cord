package de.z1up.supercord.module.permission;

import de.z1up.supercord.interfaces.Module;
import de.z1up.supercord.module.permission.command.CommandGroup;
import de.z1up.supercord.module.permission.command.CommandPlayer;
import de.z1up.supercord.module.permission.listener.ListenerPostLogin;
import de.z1up.supercord.module.permission.wrapper.GroupWrapper;
import de.z1up.supercord.module.permission.wrapper.PermissionWrapper;
import de.z1up.supercord.util.manager.ModuleManager;
import net.md_5.bungee.api.ChatColor;

public class ModulePermission implements Module {

    private boolean enabled;
    private String name;
    private String prefix;
    private int initID;
    private ChatColor themeColor;

    private GroupWrapper groupWrapper;
    private PermissionWrapper permissionWrapper;

    public ModulePermission() {
        init();

        if(!enabled) return;

        register();
    }

    @Override
    public void register() {

        new CommandGroup();
        new CommandPlayer();

        new ListenerPostLogin();
    }

    @Override
    public void init() {

        enabled = true;
        name = "PERMISSION";
        themeColor = ChatColor.DARK_GREEN;
        prefix = ChatColor.DARK_GRAY + "[" + themeColor + "SuperPermission" + ChatColor.DARK_GRAY + "]" + " ";
        initID = ModuleManager.createInitID();

        groupWrapper = new GroupWrapper();
        permissionWrapper = new PermissionWrapper();

    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getInitID() {
        return initID;
    }

    @Override
    public ChatColor getThemeColor() {
        return themeColor;
    }

    public GroupWrapper getGroupWrapper() {
        return groupWrapper;
    }

    public PermissionWrapper getPermissionWrapper() {
        return permissionWrapper;
    }
}
