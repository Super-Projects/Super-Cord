package de.z1up.supercord;

import de.z1up.supercord.core.Core;
import de.z1up.supercord.util.log.Log;
import de.z1up.supercord.util.message.Messages;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * The SuperBungee class is the main class of the
 * plugin. The core, which is essential for the
 * plugin, is started here. The class can be accessed
 * via the static class attribute {@code instance}.
 *
 *
 * @author chris23lngr
 * @since 1.0
 * @see net.md_5.bungee.api.plugin.Plugin
 * @see de.z1up.supercord.core.Core
 */
public class SuperCord extends Plugin {

    /**
     * The {@code instance} is used to refer to the
     * main class of the pugin. This must often be
     * passed in constructors to indicate to which
     * plugin the module, command or listener belongs.
     */
    public static SuperCord instance;

    /**
     * {@code onLoad()} is called when the plug-in
     * is loaded. Already here the {@code this.init()}
     * method is called. To ensure that the instance
     * is already accessible when the plug-in is started.
     *
     * @see net.md_5.bungee.api.plugin.Plugin
     */
    @Override
    public void onLoad() {
        super.onLoad();

        init(); // call init method

        Log.a(Messages.ON_LOAD); // Create new Log message
    }

    /**
     * {@code onEnable()} is called when the plugin is
     * activated. This method loads the core and outputs
     * a new {@link Log}.
     *
     * @see net.md_5.bungee.api.plugin.Plugin
     */
    @Override
    public void onEnable() {
        super.onEnable();

        Log.a(Messages.ON_ENABLE); // Create new Log message
        Log.a(Messages.THANKS_FOR_USING);

        Core.start(); // Load the Core
    }

    /**
     * The {@code onDisable()} method is called when the
     * plugin is disabled. Only a log is output here and
     * the {@code close()} method is called. method of the
     * core, which closes all open connections.
     *
     * @see net.md_5.bungee.api.plugin.Plugin
     */
    @Override
    public void onDisable() {
        super.onDisable();

        Log.a(Messages.ON_DISABLE); // Create new Log message

        Core.close(); // Close the core
    }

    /**
     * With the {@code init()} method initialises the
     * class attribute {@code instance}. This is
     * important so that all functions of the Plugin
     * can be used.
     */
    void init() {
        instance = this; // set the instance to this class
    }

}
