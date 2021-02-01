package de.z1up.supercord.module.report;

import de.z1up.supercord.interfaces.Module;
import de.z1up.supercord.module.report.command.CommandReport;
import de.z1up.supercord.module.report.wrapper.ReportWrapper;
import de.z1up.supercord.util.manager.ModuleManager;
import net.md_5.bungee.api.ChatColor;

public class ModuleReport implements Module {

    private boolean enabled;
    private String name;
    private String prefix;
    private int initID;
    private ChatColor themeColor;

    private ReportWrapper reportWrapper;

    public ModuleReport() {
        init();

        if(!enabled) return;

        register();
    }

    @Override
    public void register() {
        new CommandReport();
    }

    @Override
    public void init() {
        enabled = true;
        name = "REPORT";
        themeColor = ChatColor.RED;
        prefix = ChatColor.DARK_GRAY + "[" + themeColor + "SuperReport" + ChatColor.DARK_GRAY + "]" + " ";
        initID = ModuleManager.createInitID();

        reportWrapper = new ReportWrapper();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getInitID() {
        return initID;
    }

    @Override
    public ChatColor getThemeColor() {
        return themeColor;
    }

    public ReportWrapper getReportWrapper() {
        return reportWrapper;
    }
}
