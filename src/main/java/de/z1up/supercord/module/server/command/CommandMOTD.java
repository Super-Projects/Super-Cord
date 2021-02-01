package de.z1up.supercord.module.server.command;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;

public class CommandMOTD extends Command {

    public CommandMOTD() {
        super("motd", "sb.command.motd");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(new ComponentBuilder(Core.server.getPrefix() + ChatColor.RED + "Wrong usage! Please use: ").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/motd <1/2> <message>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Sets a new MOTD").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/motd reload/rl" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Reloads the MOTD").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/motd enable" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Disables the MOTD").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/motd disable" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Enables the MOTD").create());
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getThemeColor() + "/motd info" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows information about the MOTD").create());
            return;
        }

        if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("2")) {

            String motd = "";

            for(int i = 1; i < args.length; i++) {
                motd = motd + args[i] + " ";
            }

            motd = ChatColor.translateAlternateColorCodes('&', motd);

            int line = Integer.parseInt(args[0]);
            Core.server.motd.setLine(line, motd);

            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "New MOTD in line " + args[0] + " was set to: " + motd).create());

            if(!Core.server.motd.isEnabled())
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED + "Remember: MOTD is currently disabled!"
            ).create());

            return;
        }

        if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {

            Core.server.motd.reloadConfiguration();
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "MOTD was reloaded!").create());

            if(!Core.server.motd.isEnabled())
            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.RED + "Remember: MOTD is currently disabled!"
            ).create());
            return;
        }

        if(args[0].equalsIgnoreCase("enable")) {

            if(Core.server.motd.isEnabled()) {
                sender.sendMessage(new ComponentBuilder(
                        Core.server.getPrefix() + ChatColor.RED + "MOTD is already enabled!"
                ).create());
                return;
            }

            Core.server.motd.enable();

            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "MOTD was " + Core.server.getThemeColor() + "enabled"
                    + ChatColor.GRAY + "!"
            ).create());

            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "You might have to use " + Core.server.getThemeColor() + "/motd reload"
                            + ChatColor.GRAY + " to see results."
            ).create());

            return;
        }

        if(args[0].equalsIgnoreCase("disable")) {

            if(!Core.server.motd.isEnabled()) {
                sender.sendMessage(new ComponentBuilder(
                        Core.server.getPrefix() + ChatColor.RED + "MOTD is already disbaled!"
                ).create());
                return;
            }

            Core.server.motd.disable();

            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "MOTD was " + Core.server.getThemeColor() + "disabled"
                            + ChatColor.GRAY + "!"
            ).create());

            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "You might have to use " + Core.server.getThemeColor() + "/motd reload"
                            + ChatColor.GRAY + " to see results."
            ).create());

            return;
        }

        if(args[0].equalsIgnoreCase("info")) {
            String lineOne = Core.server.motd.getLine(1);
            lineOne = lineOne.replaceAll("&", "§");
            String lineTwo = Core.server.motd.getLine(2);
            lineTwo = lineTwo.replaceAll("&", "§");
            boolean enabled = Core.server.motd.isEnabled();

            sender.sendMessage(new ComponentBuilder(
                    Core.server.getPrefix() + ChatColor.GRAY + "The MOTD is currently "
                    + (enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled")
                    + ChatColor.GRAY + "! This is what it currently looks like:"
                    ).create());
            sender.sendMessage(new ComponentBuilder(
                    lineOne).create());
            sender.sendMessage(new ComponentBuilder(
                    lineTwo).create());
            return;
        }

        sender.sendMessage(new ComponentBuilder(Core.server.getPrefix() + ChatColor.RED + "Wrong usage! Please use: ").create());
        sender.sendMessage(new ComponentBuilder(
                Core.server.getThemeColor() + "/motd <1/2> <message>" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Sets a new MOTD").create());
        sender.sendMessage(new ComponentBuilder(
                Core.server.getThemeColor() + "/motd reload/rl" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Reloads the MOTD").create());
        sender.sendMessage(new ComponentBuilder(
                Core.server.getThemeColor() + "/motd enable" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Disables the MOTD").create());
        sender.sendMessage(new ComponentBuilder(
                Core.server.getThemeColor() + "/motd disable" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Enables the MOTD").create());
        sender.sendMessage(new ComponentBuilder(
                Core.server.getThemeColor() + "/motd info" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "Shows information about the MOTD").create());
        return;
    }
}
