package de.z1up.supercord.module.server.reason;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.sql.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReasonWrapper {

    private final String TABLE_NAME = "reasons";
    private final String ATTRIBUTE_ID = "ID";
    private final String ATTRIBUTE_NAME = "NAME";
    private final String ATTRIBUTE_TYPE = "REASON_TYPE";
    private final String ATTRIBUTE_DURATION = "DURATION";

    private SQL sql;

    public ReasonWrapper() {
        sql = Core.sql;
        createTable();
    }

    void createTable() {

        sql.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (" + ATTRIBUTE_ID + " int, "
                + ATTRIBUTE_NAME + " varchar(255), "
                + ATTRIBUTE_TYPE + " varchar(255), "
                + ATTRIBUTE_DURATION + " bigint"
                + ")", null);
    }

    public void createReason(Reason reason) {

        int id = reason.getId();
        String name = reason.getName();
        Reason.ReasonType reasonType = reason.getReasonType();
        long duration = reason.getDuration();

        sql.executeUpdate("INSERT INTO " + TABLE_NAME + " ("
                        + ATTRIBUTE_ID + ", "
                        + ATTRIBUTE_NAME + ", "
                        + ATTRIBUTE_TYPE + ", "
                        + ATTRIBUTE_DURATION + ")"
                        + " VALUES (?, ?, ?, ?)",
                Arrays.asList(id, name, reasonType.toString(), duration));

    }

    public Reason getReason(String name) {
        ResultSet rs = sql.getResult("SELECT * FROM "
                + TABLE_NAME + " WHERE " + ATTRIBUTE_NAME
                + "=?", Arrays.asList(name));

        try {
            rs.next();

            int id = rs.getInt(ATTRIBUTE_ID);
            String reasonTypeAsString = rs.getString(ATTRIBUTE_TYPE);
            Reason.ReasonType reasonType = Reason.ReasonType.valueOf(reasonTypeAsString);
            long duration = rs.getLong(ATTRIBUTE_DURATION);

            Reason reason = new Reason(id, name, reasonType, duration);
            return reason;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    public Reason getReason(int id) {
        ResultSet rs = sql.getResult("SELECT * FROM "
                + TABLE_NAME + " WHERE " + ATTRIBUTE_ID
                + "=?", Arrays.asList(id));

        try {
            rs.next();

            String name = rs.getString(ATTRIBUTE_NAME);
            String reasonTypeAsString = rs.getString(ATTRIBUTE_TYPE);
            Reason.ReasonType reasonType = Reason.ReasonType.valueOf(reasonTypeAsString);
            long duration = rs.getLong(ATTRIBUTE_DURATION);

            Reason reason = new Reason(id, name, reasonType, duration);
            return reason;

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    public Reason getReasonWithType(String name, Reason.ReasonType reasonType) {

        ResultSet rs = sql.getResult("SELECT * FROM "
                + TABLE_NAME + " WHERE " + ATTRIBUTE_NAME
                + "=? AND " + ATTRIBUTE_TYPE + "=?",
                Arrays.asList(name, reasonType));

        try {
            if(rs.next()) {
                int id = rs.getInt(ATTRIBUTE_ID);
                long duration = rs.getLong(ATTRIBUTE_DURATION);

                Reason reason = new Reason(id, name, reasonType, duration);
                return reason;
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

    public boolean existsReason(String name) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_NAME, name);
    }

    public boolean existsReason(int id) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, id);
    }

    public boolean existsID(int id) {
        return sql.existResult(TABLE_NAME, ATTRIBUTE_ID, id);
    }

    public void updateReason(Reason reason) {

        String name = reason.getName();
        int id = reason.getId();
        Reason.ReasonType reasonType = reason.getReasonType();
        long duration = reason.getDuration();


        sql.executeUpdate("UPDATE " + TABLE_NAME + " SET "
                        + ATTRIBUTE_ID + "=?, "
                        + ATTRIBUTE_TYPE + "=?, "
                        + ATTRIBUTE_DURATION + "=?"
                        + " WHERE " + ATTRIBUTE_NAME+"=?",
                Arrays.asList(id, reasonType.toString(), duration, name));

    }

    public void deleteReason(Reason reason) {
        sql.executeUpdate("DELTE * FROM " + TABLE_NAME + " WHERE "
                + ATTRIBUTE_NAME + "=? AND " + ATTRIBUTE_ID + "=?",
                Arrays.asList(reason.getName(), reason.getId()));
    }

    public ArrayList<Reason> getAllReasons() {

        ArrayList<Reason> reasons = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME, null);

        try {
            while(rs.next()) {

                int id = rs.getInt(ATTRIBUTE_ID);
                String name = rs.getString(ATTRIBUTE_NAME);
                String reasonTypeAsString = rs.getString(ATTRIBUTE_TYPE);
                Reason.ReasonType reasonType = Reason.ReasonType.valueOf(reasonTypeAsString);
                long duration = rs.getLong(ATTRIBUTE_DURATION);

                Reason reason = new Reason(id, name, reasonType, duration);
                reasons.add(reason);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return reasons;
    }

    public ArrayList<Reason> getAllReasonsOf(Reason.ReasonType reasonType) {

        ArrayList<Reason> reasons = new ArrayList<>();

        ResultSet rs = sql.getResult("SELECT * FROM " + TABLE_NAME + " WHERE " + ATTRIBUTE_TYPE + "=?",
                Arrays.asList(reasonType.toString()));

        try {
            while(rs.next()) {

                int id = rs.getInt(ATTRIBUTE_ID);
                String name = rs.getString(ATTRIBUTE_NAME);
                long duration = rs.getLong(ATTRIBUTE_DURATION);

                Reason reason = new Reason(id, name, reasonType, duration);
                reasons.add(reason);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return reasons;
    }

    /*
    public void formTime () {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date = format.format(Long.parseLong(String.valueOf(System.currentTimeMillis())));

        System.out.println("DATE " + date);

        date = format.format(Long.parseLong(String.valueOf(System.currentTimeMillis())) + (1000*60));

        System.out.println("DATE " + date);

        date = format.format(System.currentTimeMillis() + (1000*60));

        System.out.println("DATE " + date);

        /*
        String yearsAsString = date.substring(0, 4);
        System.out.println("YEARS AS STING " + yearsAsString);



        int years = Integer.parseInt(yearsAsString);
        System.out.println("YEARS " + yearsAsString);


        int actualYears = years - 1970;

        System.out.println("ACTUAL YEARS " + actualYears);
    }
    */

    public String formDuration(long duration) {

        // years
        int years = 0;
        long yearsInMms = 31536000;
        while(duration >= yearsInMms) {
            years = years + 1;
            duration = duration - yearsInMms;
        }

        // months
        int months = 0;
        long monthsInMMs = 2592000;
        while(duration >= monthsInMMs) {
            months = months + 1;
            duration = duration - monthsInMMs;
        }

        // weeks
        int weeks = 0;
        long weeksInMms = 604800;
        while(duration >= weeksInMms) {
            weeks = weeks + 1;
            duration = duration - weeksInMms;
        }

        // days
        int days = 0;
        long daysInMms = 86400;
        while(duration >= daysInMms) {
            days = days + 1;
            duration = duration - daysInMms;
        }

        // hours
        int hours = 0;
        long hoursInMms = 3600;
        while(duration >= hoursInMms) {
            hours = hours + 1;
            duration = duration - hoursInMms;
        }

        // minutes
        int minutes = 0;
        long minutesInMMs = 60;
        while(duration >= minutesInMMs) {
            minutes = minutes + 1;
            duration = duration - minutesInMMs;
        }

        // seconds
        int seconds = 0;
        long secondsInMms = 1;
        while(duration >= secondsInMms) {
            seconds = seconds + 1;
            duration = duration - secondsInMms;
        }

        String msg = "";
        if(years != 0) {
            msg = msg + years + " Jahre ";
        }
        if(months != 0) {
            msg = msg + months + " Monate ";
        }
        if(weeks != 0) {
            msg = msg + weeks + " Wochen ";
        }
        if(days != 0) {
            msg = msg + days + " Tage ";
        }
        if(hours != 0) {
            msg = msg + hours + " Stunden ";
        }
        if(minutes != 0) {
            msg = msg + minutes + " Minuten ";
        }
        if(seconds != 0) {
            msg = msg + seconds + " Sekunden ";
        }
        return msg;
    }

}
