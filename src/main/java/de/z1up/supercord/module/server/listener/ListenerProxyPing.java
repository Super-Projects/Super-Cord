package de.z1up.supercord.module.server.listener;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ListenerProxyPing implements Listener {

    public ListenerProxyPing() {
        ProxyServer.getInstance().getPluginManager().registerListener(SuperCord.instance, this);
    }

    @EventHandler
    public void onCall(ProxyPingEvent event) {

        if(!Core.server.motd.isEnabled()) return;

        ServerPing serverPing = event.getResponse();

        String lineOne = Core.server.motd.getLine(1);
        lineOne = lineOne.replaceAll("&", "§");

        String lineTwo = Core.server.motd.getLine(2);
        lineTwo = lineTwo.replaceAll("&", "§");

        serverPing.setDescription(lineOne + "\n" + lineTwo);

        event.setResponse(serverPing);

    }

}
