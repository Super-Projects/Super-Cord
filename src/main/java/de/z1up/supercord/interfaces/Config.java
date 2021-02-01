package de.z1up.supercord.interfaces;

/**
 * A Interface for Configs to provide all the needed
 * methods.
 */
public interface Config {

    /**
     * Saves the configuration file.
     */
    void saveConfiguration();

    /**
     * Sets the configuration file up.
     */
    void setUpConfiguration();

    /**
     * Loads the configuration file into the system.
     */
    void loadConfiguration();

    /**
     * Reloads the configuration file into the system.
     * Variable are being reinitialised
     */
    void reloadConfiguration();

}
