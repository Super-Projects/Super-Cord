package de.z1up.supercord.module.permission.command.sub.group;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.module.player.o.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SubCommandGroupCheckPerm implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandGroupCheckPerm(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    @Override
    public void runSubCommand() {

        if(args.length < 3) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/group checkperm <group> <perm>"
            ).create());
            return;
        }

        String groupName = args[0];

        if(!Core.permission.getGroupWrapper().existsGroup(groupName)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This group doesn't exist!"
            ).create());
            return;
        }

        Group group = Core.permission.getGroupWrapper().getGroup(groupName);

        if(sender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            if(!Core.player.getPlayerWrapper().existsPlayer(proxiedPlayer)) {
                return;
            }

            Player player = Core.player.getPlayerWrapper().getPlayer(proxiedPlayer);

            Group playerGroup = player.getGroup();

            int playerPriority = playerGroup.getPriority();

            int targetGroupPriority = group.getPriority();

            if(!sender.hasPermission(Core.permission.getPermissionWrapper().getPERM_BYPASS())) {
                if (targetGroupPriority > playerPriority) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "You cannot check permissions of a group higher than yours."
                    ).create());
                    return;
                }
            }

        }

        String permName = args[2];

        if(Core.permission.getGroupWrapper().hasGroupPermission(group, permName).get()) {

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.GREEN + group.getName() + " owns the permission "
                            + cc + permName + ChatColor.GREEN + "!"
            ).create());

        } else {

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + group.getName() + " does not own the permission "
                            + cc + permName + ChatColor.RED + "!"
            ).create());

        }

        return;
    }

}
