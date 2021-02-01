package de.z1up.supercord.util.message;

public class SQLMessages {

    public static final String PREFIX
            = "§8[§6SQL§8]§7" + " ";

    public static final String CON_CREATE_FAILED
            = PREFIX + "§4Could not create connection to database server. Attempted reconnect 3 times. Giving up.";

    public static final String CON_CREATE_SUCCESS
            = PREFIX + "§aSQL Connection successfully established.";

    public static final String CON_CLOSE
            = PREFIX + "§4SQL Connection closed.";

    public static final String CON_LOST
            = PREFIX + "§4Suddenly lost SQL connection!";

}
