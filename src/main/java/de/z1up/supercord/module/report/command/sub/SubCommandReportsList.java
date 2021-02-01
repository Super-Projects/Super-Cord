package de.z1up.supercord.module.report.command.sub;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.SubCommand;
import de.z1up.supercord.module.report.o.Report;
import de.z1up.supercord.util.uuid.UUIDFetcher;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.ArrayList;

public class SubCommandReportsList implements SubCommand {

    private CommandSender sender;
    private String[] args;
    private String prefix = Core.report.getPrefix();
    private ChatColor cc = Core.report.getThemeColor();

    public SubCommandReportsList(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
        runSubCommand();
    }

    // reports list
    @Override
    public void runSubCommand() {

        ArrayList<Report> reports = Core.report.getReportWrapper().getOpenReports();

        if(reports == null || reports.isEmpty()) {
            sender.sendMessage(new ComponentBuilder(
                    prefix + ChatColor.RED + "No open reports were found"
            ).create());
            return;
        }

        sender.sendMessage(new ComponentBuilder(
                prefix + ChatColor.GRAY + "These are the reports we found:"
        ).create());

        for(Report report : reports) {

            String reporterName = UUIDFetcher.getName(report.getReporterUUID());
            String targetName = UUIDFetcher.getName(report.getTargetUUID());
            String reason = report.getReason().getName();
            int id = report.getId();

            sender.sendMessage(new ComponentBuilder(
                    ChatColor.DARK_GRAY + "- " + cc
                            + targetName + ChatColor.GRAY + " by "
                            + cc + reporterName
                            + ChatColor.GRAY + " for "
                            + cc + reason
                            + ChatColor.DARK_GRAY + "["
                            + cc + id + ChatColor.DARK_GRAY + "]"
            ).create());

        }

        return;
    }
}
