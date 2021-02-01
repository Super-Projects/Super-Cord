package de.z1up.supercord.module.permission.command;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.permission.command.sub.player.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class CommandPlayer extends Command {

    public CommandPlayer() {
        super("player", "sb.command.player");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length >= 2) {

            if(args[1].equalsIgnoreCase("info")) {
                new SubCommandPlayerInfo(sender, args);
                return;
            }

            if(args[1].equalsIgnoreCase("join")) {
                new SubCommandPlayerJoin(sender, args);
                return;
            }

            if(args[1].equalsIgnoreCase("leave")) {
                new SubCommandPlayerLeave(sender, args);
                return;
            }

            if(args[1].equalsIgnoreCase("addperm")) {
                new SubCommandPlayerAddPerm(sender, args);
                return;
            }

            if(args[1].equalsIgnoreCase("removeperm")) {
                new SubCommandPlayerRemovePerm(sender, args);
                return;
            }

            if(args[1].equalsIgnoreCase("checkperm")) {
                new SubCommandPlayerCheckPerm(sender, args);
                return;
            }

        }

        ChatColor cc = Core.permission.getThemeColor();

        sender.sendMessage(new ComponentBuilder(
                Core.permission.getPrefix() + ChatColor.RED + "Wrong usage. Please use:"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/player <player> info"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/player <player> join <group>"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/player <player> leave"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                cc + "/player <player> addperm <perm>"
        ).create());


        sender.sendMessage(new ComponentBuilder(
                cc + "/player <player> removeperm <perm>"
        ).create());


        sender.sendMessage(new ComponentBuilder(
                cc + "/player <player> checkperm <perm>"
        ).create());

        return;
    }
}
