package de.z1up.supercord.module.permission.command;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.permission.command.sub.group.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class CommandGroup extends Command {

    public CommandGroup() {
        super("group", "sb.command.group", "groups");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length >= 1) {

            if(args[0].equalsIgnoreCase("create")) {
                new SubCommandGroupCreate(sender, args);
                return;
            }

            if(args[0].equalsIgnoreCase("list")) {
                new SubCommandGroupList(sender, args);
                return;
            }

            if(args.length >= 2) {

                if(args[1].equalsIgnoreCase("info")) {
                    new SubCommandGroupInfo(sender, args);
                    return;
                }

                if(args[1].equalsIgnoreCase("addperm")) {
                    new SubCommandGroupAddPerm(sender, args);
                    return;
                }

                if(args[1].equalsIgnoreCase("removeperm")) {
                    new SubCommandGroupRemovePerm(sender, args);
                    return;
                }

                if(args[1].equalsIgnoreCase("checkperm")) {
                    new SubCommandGroupCheckPerm(sender, args);
                    return;
                }

            }
        }

        ChatColor cc = Core.permission.getThemeColor();

        sender.sendMessage(new ComponentBuilder(
                Core.permission.getPrefix() + ChatColor.RED + "Wrong usage. Please use:"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/group create"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/group list"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/group <group> info"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/group <group> addperm"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/group <group> removeperm"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/group <group> checkperm"
        ).create());

        return;

    }

}
