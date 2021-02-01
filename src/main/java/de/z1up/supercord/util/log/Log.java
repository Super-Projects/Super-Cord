package de.z1up.supercord.util.log;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.interfaces.Module;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;

import java.util.HashMap;

public class Log {

    private static HashMap<LogType, ChatColor> colorHash = new HashMap<LogType, ChatColor>();

    public Log(String message) {
        a(message, LogType.INFO, null);
    }

    public Log(String message, LogType logType) {
        a(message, logType, null);
    }

    public Log(String message, Module module) {
        a(message, LogType.INFO, module);
    }

    public Log(String message, LogType logType, Module module) {
        a(message, logType, module, false);
    }

    public Log(String message, LogType logType, boolean customPrefix) {
        a(message, logType, null, customPrefix);
    }

    public static void a(String message) {
        a(message, LogType.INFO, null);
    }

    public static void a(String message, LogType logType) {
        a(message, logType, null);
    }

    public static void a(String message, Module module) {
        a(message, LogType.INFO, module);
    }

    public static void a(String message, LogType logType, Module module) {
        a(message, logType, module, false);
    }

    public static void a(String message, LogType logType, boolean customPrefix) {
        a(message, logType, null, customPrefix);
    }

    public static void a(String message, LogType logType, Module module, boolean customPrefix) {
        initCCs();
        ChatColor cc = getCC(logType);
        String finalMsg =
                (module == null ? Core.prefix
                        : module.getPrefix()) + cc + message;

        if(customPrefix) {
            finalMsg = cc + message;
        }

        ProxyServer.getInstance().getConsole().sendMessage(new ComponentBuilder(finalMsg).create());
        LogFile.write(finalMsg);
    }

    private static void initCCs() {
        if(getCC(LogType.INFO) == null) colorHash.put(LogType.INFO, ChatColor.WHITE);
        if(getCC(LogType.WARNING) == null) colorHash.put(LogType.WARNING, ChatColor.RED);
        if(getCC(LogType.ERROR) == null) colorHash.put(LogType.ERROR, ChatColor.DARK_RED);
        if(getCC(LogType.SUCCESS) == null) colorHash.put(LogType.SUCCESS, ChatColor.GREEN);
    }

    private static ChatColor getCC(LogType logType) {
        return colorHash.get(logType);
    }

    public enum LogType {
        INFO, WARNING, ERROR, SUCCESS
    }

}
