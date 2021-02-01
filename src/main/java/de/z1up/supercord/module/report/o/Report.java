package de.z1up.supercord.module.report.o;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.server.reason.Reason;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.UUID;

public class Report {

    private int id;
    private UUID targetUUID;
    private UUID reporterUUID;
    private UUID moderatorUUID;
    private long timestamp;
    private long lastEdited;
    private Reason reason;
    private ServerInfo serverInfo;
    private ReportStatus reportStatus;

    public Report(int id, UUID targetUUID, UUID reporterUUID, UUID moderatorUUID, long timestamp, long lastEdited, Reason reason, ServerInfo serverInfo, ReportStatus reportStatus) {
        this.id = id;
        this.targetUUID = targetUUID;
        this.reporterUUID = reporterUUID;
        this.moderatorUUID = moderatorUUID;
        this.timestamp = timestamp;
        this.lastEdited = lastEdited;
        this.reason = reason;
        this.serverInfo = serverInfo;
        this.reportStatus = reportStatus;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public UUID getReporterUUID() {
        return reporterUUID;
    }

    public UUID getModeratorUUID() {
        return moderatorUUID;
    }

    public void setModeratorUUID(UUID moderatorUUID) {
        this.moderatorUUID = moderatorUUID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Reason getReason() {
        return reason;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void create() {
        Core.report.getReportWrapper().createReport(this);
    }

    public long getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(long lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public void update() {
        Core.report.getReportWrapper().updateReport(this);
    }

    public int getId() {
        return id;
    }

    public enum ReportStatus {
        OPEN, IN_PROGRESS, NEEDS_REVIEW, ACCEPTED, REJECTED
    }

}
