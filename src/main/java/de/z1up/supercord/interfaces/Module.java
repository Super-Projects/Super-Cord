package de.z1up.supercord.interfaces;

import net.md_5.bungee.api.ChatColor;

public interface Module {

    /**
     * Registers new {@link net.md_5.bungee.api.plugin.Command} and {@link net.md_5.bungee.api.plugin.Listener}.
     */
    void register();

    /**
     * Initialises the class attributes and all other.
     */
    void init();

    /**
     * Checks if the module is enabled.
     * @return Is module enabled.
     */
    boolean isEnabled();

    /**
     * Enables or disables the module,
     * @param enabled Module enabled or disabled,
     */
    void setEnabled(boolean enabled);

    /**
     * Returns the prefix of the selected module.
     * @return The module prefix.
     */
    String getPrefix();

    /**
     * Returns the name of the selected module.
     * @return The module name.
     */
    String getName();

    /**
     * Returns the it of the selected module.
     * @return The module id.
     */
    int getInitID();

    /**
     * Returns the ThemeColor of the selected module.
     * @return The module theme color as {@link ChatColor}
     */
    ChatColor getThemeColor();
}
