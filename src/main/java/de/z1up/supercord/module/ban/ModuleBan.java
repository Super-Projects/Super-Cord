package de.z1up.supercord.module.ban;

import de.z1up.supercord.interfaces.Module;
import de.z1up.supercord.util.manager.ModuleManager;
import net.md_5.bungee.api.ChatColor;

/**
 * @see de.z1up.supercord.interfaces.Module
 */
public class ModuleBan implements Module {

    private boolean enabled;
    private String name;
    private String prefix;
    private int initID;
    private ChatColor themeColor;

    public ModuleBan() {
        init();

        if(!enabled) return;

        register();
    }


    @Override
    public void register() {

    }

    @Override
    public void init() {

        enabled = true;
        name = "BAN";
        themeColor = ChatColor.DARK_RED;
        prefix = ChatColor.DARK_GRAY + "[" + themeColor + "SuperBan" + ChatColor.DARK_GRAY + "]" + " ";
        initID = ModuleManager.createInitID();

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
}
