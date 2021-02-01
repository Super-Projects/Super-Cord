package de.z1up.supercord.module.server.command;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CommandClear extends Command {

    public CommandClear() {
        super("clear", "sb.command.clear", "cc");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            for (int i = 0; i < 200; i++) {
                player.sendMessage(" ");
            }
            player.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "Chat was " + Core.server.getThemeColor() + "cleared!"
            ).create());
        }
    }
}
