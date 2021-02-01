package de.z1up.supercord.module.player.listener;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.player.o.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class ListenerServerConnected implements Listener {

    public ListenerServerConnected() {
        ProxyServer.getInstance().getPluginManager().registerListener(SuperCord.instance, this);
    }

    @EventHandler
    public void onCall(ServerConnectedEvent event) {

        ProxiedPlayer proxiedPlayer = event.getPlayer();

        ProxyServer.getInstance().getScheduler().schedule(SuperCord.instance, new Runnable() {
            @Override
            public void run() {
                if(!Core.player.getPlayerWrapper().existsPlayer(proxiedPlayer.getUniqueId())) {

                    Player customPlayer = new Player(Core.player.getPlayerWrapper().createNewID(),
                            proxiedPlayer.getUniqueId(),
                            proxiedPlayer.getServer().getInfo(),
                            Core.permission.getGroupWrapper().getDefaultGroup(), null);
                    Core.player.getPlayerWrapper().createPlayer(customPlayer);

                    proxiedPlayer.sendMessage(new ComponentBuilder(
                            Core.prefix + "§7Wilkommen " + Core.getThemeColor() + proxiedPlayer.getName() + ChatColor.GRAY + "! "
                                    + "Du bist der " + Core.getThemeColor() + customPlayer.getId() + "."
                                    + ChatColor.GRAY + " Spieler auf diesem Netzwerk!"
                    ).create());

                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

}
