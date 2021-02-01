package de.z1up.supercord.module.report.command;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.report.o.Report;
import de.z1up.supercord.module.server.reason.Reason;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.UUID;

public class CommandReport extends Command {

    public CommandReport() {
        super("report");
        ProxyServer.getInstance().getPluginManager().registerCommand(SuperCord.instance, this);
    }

    // report player reason
    @Override
    public void execute(CommandSender sender, String[] args) {

        if(!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "Command can only be used by players"
            ).create());
            return;
        }

        if(args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "Please use /report <player> <reason>"
            ).create());
            return;
        }

        String targetName = args[0];
        String reasonAsString = args[1].toUpperCase();

        if(!UUIDFetcher.existsPlayer(targetName)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "This player doesn't exist!"
            ).create());
            return;
        }

        UUID targetUUID = UUIDFetcher.getUUID(targetName);

        if(Core.player.getPlayerWrapper().getPlayer(targetUUID).getGroup().getPriority()
                < Core.player.getPlayerWrapper().getPlayer(((ProxiedPlayer) sender).getUniqueId()).getGroup().getPriority()) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "Cannot report player with higher priority!"
            ).create());
            return;
        }

        UUID reporterUUID = ((ProxiedPlayer) sender).getUniqueId();

        if(Core.report.getReportWrapper().hasPlayerReportedTargetBefore(targetUUID, reporterUUID)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "You already reported this player."
            ).create());
            return;
        }

        Reason reason;

        if(isNumeric(reasonAsString)) {

            int reasonID = Integer.parseInt(reasonAsString);

            if(!Core.server.getReasonWrapper().existsReason(reasonID)) {
                sender.sendMessage(new ComponentBuilder(
                        Core.report.getPrefix() + ChatColor.RED + "This reason doesn't exist."
                ).create());

                ArrayList<Reason> reasons = Core.server.getReasonWrapper().getAllReasonsOf(Reason.ReasonType.REPORT);
                String msg = "";
                for(Reason available : reasons) {
                    msg = msg + Core.report.getThemeColor()
                            + available.getName()
                            + ChatColor.GRAY + "["
                            + Core.report.getThemeColor()
                            + "#" + available.getId() + ChatColor.GRAY
                            + "]" + ChatColor.DARK_GRAY + ", ";
                }
                sender.sendMessage(new ComponentBuilder(
                        Core.report.getPrefix() + ChatColor.RED + "Possible reasons: " + msg
                ).create());
                return;
            }

            reason = Core.server.getReasonWrapper().getReason(reasonID);

        } else if(Core.server.getReasonWrapper().existsReason(reasonAsString)) {

            reason = Core.server.getReasonWrapper().getReason(reasonAsString);

        } else {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "This reason doesn't exist."
            ).create());

            ArrayList<Reason> reasons = Core.server.getReasonWrapper().getAllReasonsOf(Reason.ReasonType.REPORT);
            String msg = "";
            for(Reason available : reasons) {
                msg = msg + Core.report.getThemeColor()
                        + available.getName()
                        + ChatColor.GRAY + "["
                        + Core.report.getThemeColor()
                        + "#" + available.getId() + ChatColor.GRAY
                        + "]" + ChatColor.DARK_GRAY + ", ";
            }
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "Possible reasons: " + msg
            ).create());
            return;
        }

        if(reason == null || reason.getReasonType() != Reason.ReasonType.REPORT) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "This reason doesn't fit right here."
            ).create());
            return;
        }

        int id = Core.report.getReportWrapper().createNewID();

        ServerInfo serverInfo = ((ProxiedPlayer) sender).getServer().getInfo();

        if(ProxyServer.getInstance().getPlayer(targetUUID) != null) {
            serverInfo = ProxyServer.getInstance().getPlayer(targetUUID).getServer().getInfo();
        }

        Report report = new Report(id, targetUUID,
                reporterUUID,
                UUID.randomUUID(),
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                reason,
                serverInfo,
                Report.ReportStatus.OPEN);
        report.create();

        sender.sendMessage(new ComponentBuilder(
                Core.report.getPrefix() + ChatColor.RED + "Your report was sent to the team!"
        ).create());

        for(ProxiedPlayer mods : ProxyServer.getInstance().getPlayers()) {

            if(mods.hasPermission("sb.report.handle")) {
                mods.sendMessage(new ComponentBuilder(
                        Core.report.getPrefix() + Core.report.getThemeColor()
                                + sender.getName() + ChatColor.GRAY + " reported " + Core.report.getThemeColor()
                                + targetName + ChatColor.GRAY + " for " + Core.report.getThemeColor()
                                + report.getReason().getName() + ChatColor.GRAY + "! [" + report.getId() + "]"
                ).create());
            }

        }

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
