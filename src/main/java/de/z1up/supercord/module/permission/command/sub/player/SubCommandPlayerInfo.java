package de.z1up.supercord.module.permission.command.sub.player;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.player.o.Player;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.UUID;

public class SubCommandPlayerInfo implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandPlayerInfo(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    @Override
    public void runSubCommand() {

        if (args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/player <player> info"
            ).create());
            return;
        }

        String playerName = args[0];

        if (!UUIDFetcher.existsPlayer(playerName)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This player doesn't exist!"
            ).create());
            return;
        }

        UUID uuid = UUIDFetcher.getUUID(playerName);

        if (!Core.player.getPlayerWrapper().existsPlayer(uuid)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This player isn't registered!"
            ).create());
            return;
        }

        Player player = Core.player.getPlayerWrapper().getPlayer(uuid);
        String formattedName = UUIDFetcher.getName(uuid);
        int id = player.getId();
        String groupName = player.getGroup().getName();
        String lastSeen = player.getLastSeen().getName();

        sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.GRAY + "Playerinformation:"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "Name" + ChatColor.DARK_GRAY + ": " + cc + formattedName
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "ID" + ChatColor.DARK_GRAY + ": " + cc + id
        ).create());

        sender.sendMessage(new ComponentBuilder(ChatColor.GRAY
                + formattedName + " is currently "
                + (ProxyServer.getInstance().getPlayer(uuid) != null
                ? ChatColor.GREEN + "online" : ChatColor.RED + "offline")
                + ChatColor.GRAY + "!"
        ).create());

        sender.sendMessage(new ComponentBuilder(ChatColor.GRAY
                + "Last seen on" + ChatColor.DARK_GRAY + ": " + Core.permission.getThemeColor() + lastSeen
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "Usergroup" + ChatColor.DARK_GRAY + ": " + cc + groupName
        ).create());

    }
}
