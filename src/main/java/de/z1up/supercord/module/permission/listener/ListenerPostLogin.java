package de.z1up.supercord.module.permission.listener;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ListenerPostLogin implements Listener {

    public ListenerPostLogin() {
        ProxyServer.getInstance().getPluginManager().registerListener(SuperCord.instance, this);
    }

    @EventHandler
    public void onCall(PostLoginEvent event) {

        ProxiedPlayer proxiedPlayer = event.getPlayer();
        Core.permission.getPermissionWrapper().updatePermissions(proxiedPlayer);
    }
}
