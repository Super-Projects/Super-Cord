package de.z1up.supercord.module.server.listener;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.event.PlayServerDisconnectEvent;
import de.z1up.supercord.util.log.Log;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ListenerServerDisconnect implements Listener {

    public ListenerServerDisconnect() {
        ProxyServer.getInstance().getPluginManager().registerListener(SuperCord.instance, this);
    }

    @EventHandler
    public void onCall(PlayServerDisconnectEvent event) {
        Core.server.serverManager.removeOnlineServer(event.getServerInfo());
        new Log("Server " + event.getServerInfo().getName() + " running on port "
                + event.getServerInfo().getAddress().getPort() + " suddenly disconnected!",
                Log.LogType.WARNING, Core.server);

    }

}
