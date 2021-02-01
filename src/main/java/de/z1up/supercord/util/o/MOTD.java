package de.z1up.supercord.util.o;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.interfaces.Config;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Manages the server MOTD. Can either be enabled or disbaled.
 * When disabled: Custom MOTD won't be displayed.
 */
public class MOTD implements Config {

    /**
     * Is MOTD enabled or not.
     */
    private boolean enabled;

    /**
     * The first line of the MOTD.
     */
    private String lineOne;
    /**
     * Rhe second line of the MOTD.
     */
    private String lineTwo;

    /**
     * The configuration, where the settigns will be saved.
     */
    private Configuration configuration;
    /**
     * The actual file, to get data from and dave into.
     */
    private File file;

    /**
     * Runs method {@code setUpConfiguration()}.
     */
    public MOTD() {
        setUpConfiguration();
    }

    /**
     * Loads the configuration and creates a new File.
     * If configuration doesn't contain teh attriubtes, they will be added.
     */
    public void loadConfiguration() {

        if (!SuperCord.instance.getDataFolder().exists()) SuperCord.instance.getDataFolder().mkdirs();

        file = new File(SuperCord.instance.getDataFolder(), "MOTD.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!configuration.contains("Enabled")) configuration.set("Enabled", true);

        if (!configuration.contains("Line One")) configuration.set("Line One", "This is line One");
        if (!configuration.contains("Line Two")) configuration.set("Line Two", "This is line Two");

        saveConfiguration();
    }

    /**
     * Reloads and saves the configuration.
     */
    public void setUpConfiguration() {
        reloadConfiguration();
        saveConfiguration();
    }

    /**
     * Sets the selected line and saves into the configuration.
     * Saves the configuration after that.
     *
     * @param line The line that will change.
     * @param motd The new MOTD, the text that will be seen.
     */
    public void setLine(int line, String motd) {
        if (line == 1) {
            lineOne = motd;
            configuration.set("Line One", lineOne);
        } else {
            lineTwo = motd;
            configuration.set("Line Two", lineTwo);
        }
        saveConfiguration();
        reloadConfiguration();
    }

    /**
     * Returns the selected line from the local variable.
     * @param i Which line of the MOTD schould be returned.
     * @return The text which was set for the selected line.
     */
    public String getLine(int i) {
        String motd = "NONE";
        if (i == 1) {
            if (lineOne != null) {
                motd = lineOne;
            }
        } else if (i == 2) {
            if (lineTwo != null) {
                motd = lineTwo;
            }
        }
        return motd;
    }

    /**
     * Reloads the configuration and reInitialises the class attributes.
     */
    public void reloadConfiguration() {

        loadConfiguration();

        if (configuration.contains("Enabled")) {
            enabled = configuration.getBoolean("Enabled");
        }
        if (configuration.contains("Line One")) {
            lineOne = configuration.getString("Line One");
        }
        if (configuration.contains("Line Two")) {
            lineTwo = configuration.getString("Line Two");
        }
    }

    /**
     * Saves the configuration to the file.
     */
    public void saveConfiguration() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if MOTD is enabled.
     * @return Is MOTD enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Disables the MOTD and saves decision into configuration.
     */
    public void disable() {
        enabled = false;
        configuration.set("Enabled", false);
        saveConfiguration();
    }

    /**
     * Enables the MOTD and saves decision into configuration.
     */
    public void enable() {
        enabled = true;
        configuration.set("Enabled", true);
        saveConfiguration();
    }
}
