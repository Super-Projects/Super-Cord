package de.z1up.supercord.module.player;

import de.z1up.supercord.interfaces.Module;
import de.z1up.supercord.module.player.listener.ListenerPostLogin;
import de.z1up.supercord.module.player.listener.ListenerServerConnected;
import de.z1up.supercord.module.player.wrapper.PlayerWrapper;
import de.z1up.supercord.util.manager.ModuleManager;
import net.md_5.bungee.api.ChatColor;

public class ModulePlayer implements Module {

    private boolean enabled;
    private String name;
    private String prefix;
    private int initID;
    private ChatColor themeColor;

    private PlayerWrapper playerWrapper;

    public ModulePlayer() {
        init();

        if(!enabled) return;

        register();
    }

    @Override
    public void register() {

        new ListenerPostLogin();
        new ListenerServerConnected();

    }

    @Override
    public void init() {

        enabled = true;
        name = "PLAYER";
        themeColor = ChatColor.LIGHT_PURPLE;
        prefix = ChatColor.DARK_GRAY + "[" + themeColor + "SuperPlayer" + ChatColor.DARK_GRAY + "]" + " ";
        initID = ModuleManager.createInitID();

        playerWrapper = new PlayerWrapper();

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

    public PlayerWrapper getPlayerWrapper() {
        return playerWrapper;
    }
}
