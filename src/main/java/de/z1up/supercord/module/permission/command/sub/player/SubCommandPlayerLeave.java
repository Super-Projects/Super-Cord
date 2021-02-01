package de.z1up.supercord.module.permission.command.sub.player;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.module.player.o.Player;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class SubCommandPlayerLeave implements SubCommand {
    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandPlayerLeave(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // player <player> leave
    @Override
    public void runSubCommand() {

        if (args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/player <player> leave"
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
        Group targetPlayerGroup = player.getGroup();

        if (targetPlayerGroup.getName().equalsIgnoreCase(
                Core.permission.getGroupWrapper().getDefaultGroup().getName())) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This player already belongs to the default group!"
            ).create());
            return;
        }

        if (!sender.hasPermission(Core.permission.getPermissionWrapper().getPERM_BYPASS())) {

            if (sender instanceof ProxiedPlayer) {

                ProxiedPlayer proxiedPlayerSender = (ProxiedPlayer) sender;

                if (proxiedPlayerSender.getName().equalsIgnoreCase(playerName)) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "You cann't change you own group!"
                    ).create());
                    return;
                }

                if (!Core.player.getPlayerWrapper().existsPlayer(player)) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "You're not registered! Please rejoin."
                    ).create());
                    return;
                }

                Player playerSender = Core.player.getPlayerWrapper().getPlayer(proxiedPlayerSender);
                Group playerSenderGroup = playerSender.getGroup();

                if (targetPlayerGroup.getPriority() < playerSenderGroup.getPriority()) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "You cann't change the group of someone with a higher priority."
                    ).create());
                    return;
                }

                if (targetPlayerGroup.getPriority() < targetPlayerGroup.getPriority()) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED
                                    + "You cann't change the group of someone to a group with a higher priority than yours."
                    ).create());
                    return;
                }

            }
        }

        player.leaveGroup();

        Group defaultGroup = Core.permission.getGroupWrapper().getDefaultGroup();

        sender.sendMessage(new ComponentBuilder(
                prefix + cc
                        + playerName + ChatColor.GRAY
                        + " was added to §" + defaultGroup.getCC()
                        + defaultGroup.getName() + ChatColor.GRAY + "!"
        ).create());

        if (ProxyServer.getInstance().getPlayer(uuid) != null) {
            ComponentBuilder builder = new ComponentBuilder(ChatColor.GRAY + "Your user group has changed. " +
                    "You are now in the group §" + defaultGroup.getCC() + defaultGroup.getName()
                    + "\n§e\n" + ChatColor.GRAY
                    + "To confirm the process, you have been kicked from the server."
            );

            ProxyServer.getInstance().getPlayer(uuid).disconnect(builder.create());
        }
    }
}
