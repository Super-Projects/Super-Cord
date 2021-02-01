package de.z1up.supercord.module.permission.command.sub.group;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.permission.o.Group;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.Collection;

public class SubCommandGroupList implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandGroupList(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    @Override
    public void runSubCommand() {

        sender.sendMessage(new ComponentBuilder(prefix + ChatColor.GRAY + "These are all the registered groups:").create());

        Collection<Group> groups = Core.permission.getGroupWrapper().getAllGroups();

        groups.forEach(group -> {
            String msg = "§" + group.getCC()
                    + group.getName()
                    + ChatColor.DARK_GRAY + " [§" + group.getCC() + group.getDisplayName() + ChatColor.DARK_GRAY + "]"
                    + ChatColor.DARK_GRAY + " [" + ChatColor.GRAY + "ID: §" + group.getCC() + group.getID() + ChatColor.DARK_GRAY + "]";
            sender.sendMessage(new ComponentBuilder(msg).create());
        });

        return;
    }
}

