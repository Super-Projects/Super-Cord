package de.z1up.supercord.module.server.listener;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.event.PlayServerConnectEvent;
import de.z1up.supercord.util.log.Log;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ListenerServerConnect implements Listener {

    public ListenerServerConnect() {
        ProxyServer.getInstance().getPluginManager().registerListener(SuperCord.instance, this);
    }

    @EventHandler
    public void onCall(PlayServerConnectEvent event) {
        Core.server.serverManager.addOnlineServer(event.getServerInfo());
        new Log("Successfully connected \"" + event.getServerInfo().getName() + "\" on port " +
                event.getServerInfo().getAddress().getPort() + "!", Log.LogType.SUCCESS, Core.server);
    }
}
