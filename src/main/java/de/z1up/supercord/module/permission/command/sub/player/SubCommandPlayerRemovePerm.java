package de.z1up.supercord.module.permission.command.sub.player;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.module.player.o.Player;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class SubCommandPlayerRemovePerm implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandPlayerRemovePerm(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // player <player> join <group>
    @Override
    public void runSubCommand() {

        if (args.length < 3) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/player <player> removeperm <perm>"
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

        String perm = args[2];
        Player player = Core.player.getPlayerWrapper().getPlayer(uuid);
        Group targetPlayerGroup = player.getGroup();

        if(!sender.hasPermission(Core.permission.getPermissionWrapper().getPERM_BYPASS())) {

            if (sender instanceof ProxiedPlayer) {

                ProxiedPlayer proxiedPlayerSender = (ProxiedPlayer) sender;

                if (proxiedPlayerSender.getName().equalsIgnoreCase(playerName)) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "You can not change you own group!"
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
                            prefix + ChatColor.RED + "You can not change the group of someone with a higher priority."
                    ).create());
                    return;
                }

                if (targetPlayerGroup.getPriority() < targetPlayerGroup.getPriority()) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED
                                    + "You can not change the permissions of someone with a higher priority than yours."
                    ).create());
                    return;
                }

            }
        }

        player.removePermission(perm);
        player.update();

        String formattedName = UUIDFetcher.getName(uuid);

        sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.GRAY + "The player "
                        + cc + formattedName + ChatColor.GRAY
                        + " now no longer owns the permission "
                        + cc + perm + ChatColor.GRAY + "!"
        ).create());
        return;
    }
}
