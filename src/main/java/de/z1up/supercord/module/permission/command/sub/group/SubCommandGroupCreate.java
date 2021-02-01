package de.z1up.supercord.module.permission.command.sub.group;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.permission.o.Group;
import de.z1up.supercord.module.player.o.Player;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SubCommandGroupCreate implements SubCommand {

    private String[] args;
    private CommandSender sender;
    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();

    public SubCommandGroupCreate(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // create Name display cc priority description
    @Override
    public void runSubCommand() {

        if(args.length < 5) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/group create <Name> <Display> <ColorCode> <Priority> [<Description>]"
            ).create());
            return;
        }

        String name = args[1];
        String display = args[2];
        String targetCC = args[3];
        String priorityAsString = args[4];

        String desc = "";
        if(args.length > 5) {
            for(int i = 5; i < args.length; i++) {
                desc = desc + args[i] + " ";
            }
        } else {
            desc = "NONE";
        }

        if(Core.permission.getGroupWrapper().existsGroup(name)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This group already exists."
            ).create());
            return;
        }

        if(!isNumeric(priorityAsString)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Priority needs to be a number."
            ).create());
            return;
        }

        int priority = Integer.parseInt(priorityAsString);

        if(!sender.hasPermission(Core.permission.getPermissionWrapper().getPERM_BYPASS())) {

            if (priority < 0 || priority > 100) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "Priority needs to be a number between 0 and 100."
                ).create());
                return;
            }
        }

        if (Core.permission.getGroupWrapper().existsPriority(priority)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This priority is already taken by another group."
            ).create());
            return;
        }

        if(sender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;

            if(!Core.player.getPlayerWrapper().existsPlayer(proxiedPlayer)) {
                return;
            }

            Player player = Core.player.getPlayerWrapper().getPlayer(proxiedPlayer);

            Group playerGroup = player.getGroup();

            int playerPriority = playerGroup.getPriority();

            if(priority > playerPriority) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "You cannot create a group with a higher priority than yours."
                ).create());
                return;
            }
        }

        if(targetCC.length() != 1) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "ColorCode can only be one character."
            ).create());
            return;
        }

        if(Core.permission.getGroupWrapper().ccInUse(targetCC)) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This ColorCode is already taken by another group."
            ).create());
            return;
        }

        int id = Core.permission.getGroupWrapper().createNewID();

        Group group = new Group(id, name, display, desc, targetCC, null, priority);
        group.create();

        sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.GREEN + "Succesfully created group §"
                        + group.getCC() + group.getName() + ChatColor.GREEN + "!"
        ).create());
        return;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}