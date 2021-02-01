package de.z1up.supercord.util.message;

import de.z1up.supercord.SuperCord;

/**
 * The Messages class contains messages that are
 * globally accessible. All non-context specific
 * messages are stored here. Context-specific
 * messages should be declared in the header of
 * the respective class.
 *
 * @author chris23lngr
 * @since 1.2
 */
public class Messages {

    private static final String VERSION
            = SuperCord.instance.getDescription().getVersion();

    private static final String AUTHOR
            = SuperCord.instance.getDescription().getAuthor();

    public static final String PREFIX
            = "§8[§bCore§8]§7" + " ";

    public static final String ON_LOAD
            = PREFIX + "§aLoading SuperBungee. Running on version " + VERSION + " by " + AUTHOR + "...";

    public static final String ON_ENABLE
            = PREFIX + "§aEnabling SuperBungee. Running on version " + VERSION + " by " + AUTHOR + "...";

    public static final String ON_DISABLE
            = PREFIX + "§aDisabling SuperBungee. Running on version " + VERSION + " by " + AUTHOR + "...";

    public static final String THANKS_FOR_USING
            = PREFIX + "§aThanks for choosing SuperBungee!";

}
