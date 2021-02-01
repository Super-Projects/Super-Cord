package de.z1up.supercord.module.server.reason;

import de.z1up.supercord.core.Core;

public class Reason {

    private int id;
    private String name;
    private ReasonType reasonType;
    private long duration;

    public Reason(int id, String name, ReasonType reasonType, long duration) {
        this.id = id;
        this.name = name;
        this.reasonType = reasonType;
        this.duration = duration;
    }

    public ReasonType getReasonType() {
        return reasonType;
    }

    public void setReasonType(ReasonType reasonType) {
        this.reasonType = reasonType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void create() {
        Core.server.getReasonWrapper().createReason(this);
    }

    public enum ReasonType {
        BAN, MUTE, WARNING, REPORT
    }

}
