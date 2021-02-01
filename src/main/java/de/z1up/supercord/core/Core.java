package de.z1up.supercord.core;

import de.z1up.supercord.module.ban.ModuleBan;
import de.z1up.supercord.module.permission.ModulePermission;
import de.z1up.supercord.module.player.ModulePlayer;
import de.z1up.supercord.module.report.ModuleReport;
import de.z1up.supercord.module.server.ModuleServer;
import de.z1up.supercord.sql.SQL;
import de.z1up.supercord.sql.SQLConfig;
import de.z1up.supercord.util.manager.ModuleManager;
import net.md_5.bungee.api.ChatColor;

import java.sql.SQLException;

/**
 * The core is the distributor of the individual modules.
 * All usable modules are initialised and declared.
 * In addition, the SQL connection can be accessed via the core.
 */
public class Core {

    /** The global prefix for the Core. */
    public static String prefix;

    /** The report module, handles player reports. */
    public static ModuleReport report;
    /** The ban module, handles player bans/mutes.  */
    public static ModuleBan ban;
    /** The player module, handles player settings and stats. */
    public static ModulePlayer player;
    /** The server module, handles servers and server comments. */
    public static ModuleServer server;
    /** The permission module, handles permission system for players and groups.*/
    public static ModulePermission permission;

    public static SQL sql;

    /** The theme color for the Core. */
    private static ChatColor themeColor = ChatColor.AQUA;

    // methods

    /**
     * Called when Core starts up.
     */
    public static void start() {
        init();
    }

    public static void close() {
        try {
            sql.getConnection().close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * Initialises the class attributes and registers
     * them in the {@link ModuleManager}.
     */
    static void init() {

        prefix = ChatColor.DARK_GRAY
                + "[" + ChatColor.AQUA
                + "Core"
                + ChatColor.DARK_GRAY
                + "]" + " ";

        initSQL();

        report = new ModuleReport();
        ban = new ModuleBan();
        player = new ModulePlayer();
        server = new ModuleServer();
        permission = new ModulePermission();

        ModuleManager.registerModule(report);
        ModuleManager.registerModule(ban);
        ModuleManager.registerModule(player);
        ModuleManager.registerModule(server);
        ModuleManager.registerModule(permission);

    }

    /**
     * Initialises and connects the sql connection.
     */
    private static void initSQL() {
        SQLConfig sqldata = new SQLConfig();
        sql = new SQL(sqldata.readData());
        sql.connect(); // create connection
    }

    /**
     * @return The theme color/ {@link ChatColor} for the {@link Core}.
     */
    public static ChatColor getThemeColor() {
        return themeColor;
    }
}
