package de.z1up.supercord.module.report.listener;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.report.o.Report;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.UUID;

public class ListenerPlayerDisconnect implements Listener {

    @EventHandler
    public void onCall(PlayerDisconnectEvent event) {

        ProxiedPlayer disconnect = event.getPlayer();
        UUID uuid = disconnect.getUniqueId();

        if(Core.report.getReportWrapper().isModeratorCurrentlyHandlingReport(uuid)) {

            ArrayList<Report> reports = Core.report.getReportWrapper().getModeratorReports(uuid);

            reports.forEach(report -> {
                if(report.getReportStatus().equals(Report.ReportStatus.IN_PROGRESS)) {
                    report.setReportStatus(Report.ReportStatus.NEEDS_REVIEW);
                    report.update();
                }
            });

            return;
        }

        if(Core.report.getReportWrapper().hasReportInProgress(uuid)) {

            ArrayList<Report> reports = Core.report.getReportWrapper().getPlayerReports(uuid);

            reports.forEach(report -> {

                if(report.getReportStatus().equals(Report.ReportStatus.IN_PROGRESS)) {

                    ProxiedPlayer mod = ProxyServer.getInstance().getPlayer(report.getModeratorUUID());

                    report.setReportStatus(Report.ReportStatus.OPEN);
                    report.update();

                    if(mod != null) {
                        mod.sendMessage(new ComponentBuilder(
                                Core.report.getPrefix() + ChatColor.GRAY + "Your target left the server. The report status will be set to open."
                        ).create());
                    }
                }
            });
        }
    }
}
