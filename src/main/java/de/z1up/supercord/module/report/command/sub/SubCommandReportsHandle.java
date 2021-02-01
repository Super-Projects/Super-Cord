package de.z1up.supercord.module.report.command.sub;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.report.o.Report;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class SubCommandReportsHandle implements SubCommand {

    private CommandSender sender;
    private String[] args;
    private String prefix = Core.report.getPrefix();
    private ChatColor cc = Core.report.getThemeColor();

    public SubCommandReportsHandle(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // reports handle <id>
    @Override
    public void runSubCommand() {

        if(args.length < 2) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "Please use /reports handle <id>"
            ).create());
            return;
        }

        ProxiedPlayer moderator = (ProxiedPlayer) sender;
        UUID moderatorUUID = moderator.getUniqueId();

        if(Core.report.getReportWrapper().isModeratorCurrentlyHandlingReport(moderatorUUID)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "You are already handling a report."
            ).create());
            return;
        }

        if(!isNumeric(args[1])) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "ID has to be a number."
            ).create());
            return;
        }

        int id = Integer.parseInt(args[1]);

        Report report = Core.report.getReportWrapper().getReport(id);

        if (!report.getReportStatus().equals(Report.ReportStatus.OPEN)) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "This report is not open."
            ).create());
        }

        UUID senderUUID = report.getReporterUUID();
        UUID targetUUID = report.getTargetUUID();

        if(ProxyServer.getInstance().getPlayer(targetUUID) == null) {
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "Target not online. Try again later."
            ).create());
            return;
        }

        report.setModeratorUUID(moderatorUUID);
        report.setReportStatus(Report.ReportStatus.IN_PROGRESS);
        report.update();

        if(ProxyServer.getInstance().getPlayer(senderUUID) != null) {
            ProxyServer.getInstance().getPlayer(senderUUID).sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.GRAY + "Your report about " + cc +
                            UUIDFetcher.getName(targetUUID) + ChatColor.GRAY + " will now be processed."
            ).create());
        }

        if(ProxyServer.getInstance().getPlayer(targetUUID) != null) {
            ProxiedPlayer target = ProxyServer.getInstance().getPlayer(senderUUID);

            ((ProxiedPlayer) sender).connect(target.getServer().getInfo());
            sender.sendMessage(new ComponentBuilder(
                    Core.report.getPrefix() + ChatColor.RED + "You will be connected to your targets server."
            ).create());

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
