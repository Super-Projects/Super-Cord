package de.z1up.supercord.module.server.reason.sub;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.server.reason.Reason;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;


public class SubCommandReasonCreate implements SubCommand {

    private String prefix = Core.permission.getPrefix();
    private ChatColor cc = Core.permission.getThemeColor();
    private CommandSender sender;
    private String[] args;

    public SubCommandReasonCreate(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // reason create <name> ban
    @Override
    public void runSubCommand() {

        if(args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Please use " + cc + "/reason create report/warning <name> <id>"
            ).create());
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Or " + cc + "/reason create ban/mute <name> <id> <years> <months> <weeks> <days> <hours> <minutes>"
            ).create());
            return;
        }

        String reasonTypeName = args[1].toUpperCase();

        if(Reason.ReasonType.valueOf(reasonTypeName) == null) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "This Reason- Type doesn't exist!"
            ).create());

            String types = "";
            for(Reason.ReasonType reasonType : Reason.ReasonType.values()) {
                types = cc + reasonType.toString() + ChatColor.DARK_GRAY + ", ";
            }

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "Possible Reason- Types: " + types
            ).create());
            return;
        }

        Reason.ReasonType reasonType = Reason.ReasonType.valueOf(reasonTypeName);

        if(args[1].equalsIgnoreCase("mute") || args[1].equalsIgnoreCase("ban")) {

            if(args.length > 10) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "Please use " + cc + "/reason create ban/mute <name> <id> <years> <months> <weeks> <days> <hours> <minutes>"
                ).create());
                return;
            }

            for(int i = 3; i < args.length; i++) {
                if(!isNumeric(args[i])) {
                    sender.sendMessage(new ComponentBuilder(
                            prefix + ChatColor.RED + "Duration can only be displayed in numbers"
                    ).create());
                    return;
                }
            }

            int id = Integer.parseInt(args[3]);

            if(Core.server.getReasonWrapper().existsID(id)) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "This ID is already in use!"
                ).create());
                return;
            }

            int years = Integer.parseInt(args[4]);
            int months = Integer.parseInt(args[5]);
            int weeks = Integer.parseInt(args[6]);
            int days = Integer.parseInt(args[7]);
            int hours = Integer.parseInt(args[8]);
            int minutes = Integer.parseInt(args[9]);

            long duration = 0;

            if(years != 0) {
                duration = duration + (years * 60 * 60 * 24 * 365);
            }
            if(months != 0) {
                duration = duration + (months * 60 * 60 * 24 * 30);
            }
            if(weeks != 0) {
                duration = duration + (weeks * 60 * 60 * 24 * 7);
            }
            if(days != 0) {
                duration = duration + (days * 60 * 60 * 24);
            }
            if(hours != 0) {
                duration = duration + (hours * 60 * 60);
            }
            if(minutes != 0) {
                duration = duration + (minutes * 60);
            }

            if(duration == 0) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "Duration can not be 0."
                ).create());
                return;
            }

            String name = args[2].toUpperCase();
            Reason reason = new Reason(id, name, reasonType, duration);
            reason.create();

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.GRAY + "A new reason was created."
            ).create());

            return;
        }

        // reason create report name id
        if(args[1].equalsIgnoreCase("report") || args[1].equalsIgnoreCase("warning")) {

            if(args.length < 4) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "Please use " + cc + "/reason create report/warning <name> <id>"
                ).create());
                return;
            }

            if(!isNumeric(args[3])) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "ID has to be numeric!"
                ).create());
            }

            int id = Integer.parseInt(args[3]);

            if(Core.server.getReasonWrapper().existsID(id)) {
                sender.sendMessage(new ComponentBuilder(
                        prefix + ChatColor.RED + "This ID is already in use!"
                ).create());
                return;
            }

            String name = args[2];
            Reason reason = new Reason(id, name, reasonType, 0);
            reason.create();

            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.GRAY + "A new reason was created."
            ).create());

            return;

        }

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
