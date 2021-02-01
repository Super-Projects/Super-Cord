package de.z1up.supercord.module.player.listener;

import de.z1up.supercord.SuperCord;
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

        ProxiedPlayer player = event.getPlayer();

        /*
        if(!Core.player.getPlayerWrapper().existsPlayer(player.getUniqueId())) {

            Player customPlayer = new Player(Core.player.getPlayerWrapper().createNewID(),
                    player.getUniqueId(),
                    player.getServer().getInfo(),
                    Core.permission.getGroupWrapper().getDefaultGroup(), null);
            Core.player.getPlayerWrapper().createPlayer(customPlayer);

            player.sendMessage(new ComponentBuilder(
                    Core.prefix + "§7Wilkommen " + Core.getThemeColor() + player.getName() + ChatColor.GRAY + "! "
                            + "Du bist der " + Core.getThemeColor() + customPlayer.getId() + "."
                            + ChatColor.GRAY + " Spieler auf diesem Netzwerk!"
            ).create());

        }

         */
    }

}
