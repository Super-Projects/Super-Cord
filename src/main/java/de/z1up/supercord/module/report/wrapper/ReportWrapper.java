package de.z1up.supercord.module.report.wrapper;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.module.report.o.Report;
import de.z1up.supercord.module.server.reason.Reason;
import de.z1up.supercord.sql.SQL;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ReportWrapper {

    private final String TABLE_NAME = "reports";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_TARGET = "TARGET_UUID";
    private final String ATTRIBUTE_REPORTER = "REPORTER_UUID";
    private final String ATTRIBUTE_MODERATOR = "MODERATOR_UUID";
    private final String ATTRIBUTE_TIMESTAMP = "TIMESTAMP";
    private final String ATTRIBUTE_LAST_EDITED = "LAST_EDITED";
    private final String ATTRIBUTE_REPORT_REASON = "REPORT_REASON";
    private final String ATTRIBUTE_SERVER = "SERVER";
    private final String ATTRIBUTE_STATUS = "STATUS";

    private SQL sql;

    public ReportWrapper() {
        sql = Core.sql;
        createTable();
    }

    void createTable() {
        sql.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " ("
                + ATTRIBUTE_ID + " int, "
                + ATTRIBUTE_TARGET + " varchar(255), "
                + ATTRIBUTE_REPORTER + " varchar(255), "
                + ATTRIBUTE_MODERATOR + " varchar(255), "
                + ATTRIBUTE_TIMESTAMP + " bigint, "
                + ATTRIBUTE_LAST_EDITED + " bigint, "
                + ATTRIBUTE_REPORT_REASON + " int, "
                + ATTRIBUTE_SERVER + " varchar(255), "
                + ATTRIBUTE_STATUS + " varchar(255)"
                + ")", null);
    }

    public void createReport(Report report) {

        int id = report.getId();
        UUID targetUUID = report.getTargetUUID();
        UUID reporterUUID = report.getReporterUUID();
        UUID moderatorUUID = report.getModeratorUUID();
        long timestamp = report.getTimestamp();
        long lastEdited = report.getLastEdited();
        Reason reason = report.getReason();
        ServerInfo serverInfo = report.getServerInfo();
        Report.ReportStatus status = report.getReportStatus();

        sql.executeUpdate("INSERT INTO " + TABLE_NAME + " ("
                        + ATTRIBUTE_ID + ", "
                        + ATTRIBUTE_TARGET + ", "
                        + ATTRIBUTE_REPORTER + ", "
                        + ATTRIBUTE_MODERATOR + ", "
                        + ATTRIBUTE_TIMESTAMP + ", "
                        + ATTRIBUTE_LAST_EDITED + ", "
                        + ATTRIBUTE_REPORT_REASON + ", "
                        + ATTRIBUTE_SERVER + ", "
                        + ATTRIBUTE_STATUS + ") VALUES "
                        + "(?, ?, ?, ?, ?, ?, ?, ?, ?) "
                , Arrays.asList(
                        id,
                        targetUUID,
                        reporterUUID,
                        moderatorUUID,
                        timestamp,
                        lastEdited,
                        reason.getId(),
                        serverInfo.getName(),
                        status.toString()));

    }

    public Report getReport(int id) {

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_ID + "=?",
                Arrays.asList(id));

        try {
            rs.next();

            UUID targetUUID = UUID.fromString(rs.getString(ATTRIBUTE_TARGET));
            UUID reporterUUID = UUID.fromString(rs.getString(ATTRIBUTE_REPORTER));
            UUID moderatorUUID = UUID.fromString(rs.getString(ATTRIBUTE_MODERATOR));
            long timestamp = rs.getLong(ATTRIBUTE_TIMESTAMP);
            long lastedited = rs.getLong(ATTRIBUTE_LAST_EDITED);
            Reason reason = Core.server.getReasonWrapper().getReason(rs.getInt(ATTRIBUTE_REPORT_REASON));
            ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(rs.getString(ATTRIBUTE_SERVER));
            Report.ReportStatus status = Report.ReportStatus.valueOf(rs.getString(ATTRIBUTE_STATUS));

            Report report =
                    new Report(id, targetUUID, reporterUUID, moderatorUUID,
                            timestamp, lastedited, reason, serverInfo, status);

            return report;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public void updateReport(Report report) {

        int id = report.getId();
        UUID targetUUID = report.getTargetUUID();
        UUID reporterUUID = report.getReporterUUID();
        UUID moderatorUUID = report.getModeratorUUID();
        long timestamp = report.getTimestamp();
        long lastEdited = report.getLastEdited();
        Reason reason = report.getReason();
        ServerInfo serverInfo = report.getServerInfo();
        Report.ReportStatus status = report.getReportStatus();

        sql.executeUpdate("UPDATE " + TABLE_NAME + " SET "
                + ATTRIBUTE_TARGET + "=?, "
                        + ATTRIBUTE_REPORTER + "=?, "
                        + ATTRIBUTE_MODERATOR + "=?, "
                        + ATTRIBUTE_TIMESTAMP + "=?, "
                        + ATTRIBUTE_LAST_EDITED + "=?, "
                        + ATTRIBUTE_REPORT_REASON + "=?, "
                        + ATTRIBUTE_SERVER + "=?, "
                        + ATTRIBUTE_STATUS + "=? "
                        + "WHERE " + ATTRIBUTE_ID + "=?",
                Arrays.asList(
                        targetUUID,
                        reporterUUID,
                        moderatorUUID,
                        timestamp,
                        lastEdited,
                        reason.getId(),
                        serverInfo.getName(),
                        status.toString(),
                        id));
    }

    public boolean hasPlayerReportedTargetBefore(UUID targetUUID, UUID reporterUUID) {

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_TARGET + "=? AND "
                        + ATTRIBUTE_REPORTER + "=? AND " + ATTRIBUTE_STATUS + "=?",
                Arrays.asList(targetUUID, reporterUUID, Report.ReportStatus.OPEN));

        try {
            if(rs.next()) {
                return true;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return false;
    }

    public boolean existsID(int id) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, id);
    }

    public int createNewID() {
        int id = new Random().nextInt(99999);
        if(existsID(id)) {
            return createNewID();
        }
        return id;
    }

    public ArrayList<Report> getOpenReports() {
        ArrayList<Report> reports = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_STATUS + "=?",
                Arrays.asList(Report.ReportStatus.OPEN.toString()));

        try {
            while(rs.next()) {

                int id = rs.getInt(ATTRIBUTE_ID);
                UUID targetUUID = UUID.fromString(rs.getString(ATTRIBUTE_TARGET));
                UUID reporterUUID = UUID.fromString(rs.getString(ATTRIBUTE_REPORTER));
                UUID moderatorUUID = UUID.fromString(rs.getString(ATTRIBUTE_MODERATOR));
                long timestamp = rs.getLong(ATTRIBUTE_TIMESTAMP);
                long lastedited = rs.getLong(ATTRIBUTE_LAST_EDITED);
                Reason reason = Core.server.getReasonWrapper().getReason(rs.getInt(ATTRIBUTE_REPORT_REASON));
                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(rs.getString(ATTRIBUTE_SERVER));
                Report.ReportStatus status = Report.ReportStatus.valueOf(rs.getString(ATTRIBUTE_STATUS));

                Report report =
                        new Report(id, targetUUID, reporterUUID, moderatorUUID,
                                timestamp, lastedited, reason, serverInfo, status);
                reports.add(report);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return reports;
    }

    public boolean isModeratorCurrentlyHandlingReport(UUID uuid) {

        Collection<Report> reports = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT " + ATTRIBUTE_ID + " FROM "
                + TABLE_NAME + " WHERE " + ATTRIBUTE_MODERATOR + "=?",
                Arrays.asList(uuid));

        try {
            while(rs.next()) {
                int id = rs.getInt(ATTRIBUTE_ID);
                Report report = getReport(id);
                reports.add(report);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        for(Report report : reports) {
            if(report.getReportStatus().equals(Report.ReportStatus.IN_PROGRESS)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasReportInProgress(UUID uuid) {
        Collection<Report> reports = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT " + ATTRIBUTE_ID + " FROM "
                        + TABLE_NAME + " WHERE " + ATTRIBUTE_TARGET + "=?",
                Arrays.asList(uuid));

        try {
            while(rs.next()) {
                int id = rs.getInt(ATTRIBUTE_ID);
                Report report = getReport(id);
                reports.add(report);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        for(Report report : reports) {
            if(report.getReportStatus().equals(Report.ReportStatus.IN_PROGRESS)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Report> getPlayerReports(UUID uuid) {
        ArrayList<Report> reports = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT " + ATTRIBUTE_ID + " FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_TARGET + "=?",
                Arrays.asList(uuid));

        try {
            while(rs.next()) {
                Report report = getReport(rs.getInt(ATTRIBUTE_ID));
                reports.add(report);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return reports;
    }

    public ArrayList<Report> getModeratorReports(UUID uuid) {
        ArrayList<Report> reports = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT " + ATTRIBUTE_ID + " FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_MODERATOR + "=?",
                Arrays.asList(uuid));

        try {
            while(rs.next()) {
                Report report = getReport(rs.getInt(ATTRIBUTE_ID));
                reports.add(report);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return reports;
    }

}
