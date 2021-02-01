package de.z1up.supercord.module.permission.command.sub.group;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.permission.o.Group;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class SubCommandGroupInfo implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandGroupInfo(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    @Override
    public void runSubCommand() {

        if(args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.RED + "Please use " + cc + "/group info <group>"
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

        String name = group.getName();
        String cc = "§" + group.getCC();
        String display = cc + group.getDisplayName();
        int id = group.getID();
        int priority = group.getPriority();
        String description = group.getDescription();

        sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.GRAY + "Information about group:"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "Name: " + cc + name
                        + ChatColor.DARK_GRAY + " [" + display + ChatColor.DARK_GRAY + "]"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "ID: " + cc + id
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "Priority: " + cc + priority
        ).create());

        sender.sendMessage(new ComponentBuilder(
                ChatColor.GRAY + "Description: " + cc + description
        ).create());

    }
}
