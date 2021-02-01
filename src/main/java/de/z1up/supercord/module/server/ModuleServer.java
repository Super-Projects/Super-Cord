package de.z1up.supercord.module.server;

import de.z1up.supercord.interfaces.Module;
import de.z1up.supercord.module.server.account.AccountWrapper;
import de.z1up.supercord.module.server.account.CommandAccount;
import de.z1up.supercord.module.server.command.CommandClear;
import de.z1up.supercord.module.server.command.CommandMOTD;
import de.z1up.supercord.module.server.command.CommandServer;
import de.z1up.supercord.module.server.listener.ListenerProxyPing;
import de.z1up.supercord.module.server.listener.ListenerServerConnect;
import de.z1up.supercord.module.server.listener.ListenerServerDisconnect;
import de.z1up.supercord.module.server.reason.CommandReason;
import de.z1up.supercord.module.server.reason.ReasonWrapper;
import de.z1up.supercord.util.manager.ModuleManager;
import de.z1up.supercord.util.manager.ServerManager;
import de.z1up.supercord.util.o.MOTD;
import net.md_5.bungee.api.ChatColor;

public class ModuleServer implements Module {

    private boolean enabled;
    private String name;
    private String prefix;
    private int initID;
    private ChatColor themeColor;

    public ServerManager serverManager;
    public MOTD motd;

    private ReasonWrapper reasonWrapper;
    private AccountWrapper accountWrapper;

    public ModuleServer() {
        init();

        if(!enabled) return;

        register();
    }

    @Override
    public void register() {

        new ListenerServerConnect();
        new ListenerServerDisconnect();
        new ListenerProxyPing();

        new CommandServer();
        new CommandMOTD();
        new CommandClear();
        new CommandReason();

        new CommandAccount();

    }

    @Override
    public void init() {

        enabled = true;
        name = "SERVER";
        themeColor = ChatColor.YELLOW;
        prefix = ChatColor.DARK_GRAY + "[" + themeColor + "SuperServer" + ChatColor.DARK_GRAY + "]" + " ";
        initID = ModuleManager.createInitID();

        serverManager = new ServerManager();
        motd = new MOTD();

        reasonWrapper = new ReasonWrapper();
        accountWrapper = new AccountWrapper();

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

    public ReasonWrapper getReasonWrapper() {
        return reasonWrapper;
    }

    public AccountWrapper getAccountWrapper() {
        return accountWrapper;
    }
}
