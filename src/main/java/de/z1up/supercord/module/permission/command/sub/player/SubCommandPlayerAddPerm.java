package de.z1up.supercord.module.permission.command.sub.player;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.player.o.Player;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class SubCommandPlayerAddPerm implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandPlayerAddPerm(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // player <player> addperm <perm>
    @Override
    public void runSubCommand() {

        if (args.length < 3) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/player <player> addperm <perm>"
            ).create());
            return;
        }

        String playerName = args[0];

        if(!UUIDFetcher.existsPlayer(playerName)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This player doesn't exist!"
            ).create());
            return;
        }

        UUID uuid = UUIDFetcher.getUUID(playerName);

        if(!Core.player.getPlayerWrapper().existsPlayer(uuid)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This player isn't registered!"
            ).create());
            return;
        }

        Player player = Core.player.getPlayerWrapper().getPlayer(uuid);
        String formattedName = UUIDFetcher.getName(uuid);
        String perm = args[2];


        if(sender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            if(!Core.player.getPlayerWrapper().existsPlayer(proxiedPlayer)) {
                return;
            }

            Player playerSender = Core.player.getPlayerWrapper().getPlayer(proxiedPlayer);
            int senderPriority = playerSender.getGroup().getPriority();

            if(!sender.hasPermission(Core.permission.getPermissionWrapper().getPERM_BYPASS())) {
                if(senderPriority < player.getGroup().getPriority()) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "You cannot edit a group with a higher priority than yours."
                    ).create());
                    return;
                }
            }
        }

        if(perm.equalsIgnoreCase(Core.permission.getPermissionWrapper().getPERM_BYPASS())) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This is a privileged permission."
            ).create());
            return;
        }

        if(player.hasPermission(perm)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This player already owns the selected permission!"
            ).create());
            return;
        }

        player.addPermission(perm);
        player.update();

        sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.GRAY + "The player "
                        + cc + formattedName + ChatColor.GRAY
                        + " now owns the permission "
                        + cc + perm + ChatColor.GRAY + "!"
            ).create());

        return;
    }
}