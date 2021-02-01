package de.z1up.supercord.module.permission.command.sub.player;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.player.o.Player;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.UUID;

public class SubCommandPlayerCheckPerm implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandPlayerCheckPerm(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // player <player> checkperm <perm>
    @Override
    public void runSubCommand() {

        if(args.length > 3) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/player <player> checkperm <perm>"
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
        String perm = args[2];
        String formattedName = UUIDFetcher.getName(uuid);

        if(player.hasPermission(perm)) {

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.GREEN +  formattedName + " owns the permission "
                            + cc + perm + ChatColor.GREEN + "!"
            ).create());

        } else {

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + formattedName + " does not own the permission "
                            + cc + perm + ChatColor.RED + "!"
            ).create());

        }
        return;
    }
}
