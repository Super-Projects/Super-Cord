package de.z1up.supercord.module.report.listener;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.report.o.Report;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.UUID;

public class ListenerServerSwitch implements Listener {

    public ListenerServerSwitch() {
        ProxyServer.getInstance().getPluginManager().registerListener(SuperCord.instance, this);
    }

    @EventHandler
    public void onCall(ServerSwitchEvent event) {

        ProxiedPlayer switched = event.getPlayer();
        UUID uuid = switched.getUniqueId();

        if(!Core.report.getReportWrapper().hasReportInProgress(uuid)) {
            return;
        }

        ArrayList<Report> reports = Core.report.getReportWrapper().getPlayerReports(uuid);

        for(Report report : reports) {

            if(report.getReportStatus().equals(Report.ReportStatus.IN_PROGRESS)) {

                UUID moderatorUUID = report.getModeratorUUID();

                ProxiedPlayer moderator = ProxyServer.getInstance().getPlayer(moderatorUUID);

                if(moderator != null) {

                    moderator.connect(switched.getServer().getInfo());
                    moderator.sendMessage(new ComponentBuilder(
                            Core.report.getPrefix() + ChatColor.GRAY + "Your target switched the server. You were connected to his new server."
                    ).create());

                }

            }

        }

    }

}
