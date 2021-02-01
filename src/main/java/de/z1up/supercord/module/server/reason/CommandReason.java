package de.z1up.supercord.module.server.reason;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.server.reason.sub.SubCommandReasonCreate;
import de.z1up.supercord.module.server.reason.sub.SubCommandReasonList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class CommandReason extends Command {

    public CommandReason() {
        super("reason", "sb.command.reason");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    //
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length >= 1) {

            if(args[0].equalsIgnoreCase("list")) {
                new SubCommandReasonList(sender, args);
                return;
            }

            if(args[0].equalsIgnoreCase("create")) {
                new SubCommandReasonCreate(sender, args);
                return;
            }

        }

        sender.sendMessage(new ComponentBuilder(
                Core.server.getPrefix() + ChatColor.RED + "Wrong usage! Please use:"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                Core.server.getPrefix() + Core.server.getThemeColor() + "/reason list <report/ban/mute/warning>"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                Core.server.getPrefix() + Core.server.getThemeColor()  + "/reason create report/warning <Name> <id>"
        ).create());

        sender.sendMessage(new ComponentBuilder(
                Core.server.getPrefix() + Core.server.getThemeColor()  + "/reason create ban/mute <Name> <id> <years> <months> <weeks> <days> <hours> <minutes> [<Description>]"
        ).create());

    }
}
