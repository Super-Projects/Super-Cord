package de.z1up.supercord.sql;

import de.z1up.supercord.SuperCord;
import de.z1up.supercord.interfaces.Config;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SQLConfig implements Config {

    private Configuration configuration;
    private File file;

    public SQLConfig() {
        setUpConfiguration();
    }

    public void loadConfiguration() {

        if(!SuperCord.instance.getDataFolder().exists()) SuperCord.instance.getDataFolder().mkdirs();

        file = new File(SuperCord.instance.getDataFolder(), "MySQL.yml");

        try {
            if(!file.exists()) {
                file.createNewFile();
            }

            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!configuration.contains("host")) configuration.set("host", "localhost");
        if(!configuration.contains("port")) configuration.set("port", "3306");
        if(!configuration.contains("database")) configuration.set("database", "database");
        if(!configuration.contains("username")) configuration.set("username", "root");
        if(!configuration.contains("password")) configuration.set("password", "password");

        saveConfiguration();
    }

    public void setUpConfiguration() {
        reloadConfiguration();
        saveConfiguration();
    }

    public void reloadConfiguration() {

        loadConfiguration();

        if(!configuration.contains("host")) configuration.set("host", "localhost");
        if(!configuration.contains("port")) configuration.set("port", 3306);
        if(!configuration.contains("database")) configuration.set("database", "database");
        if(!configuration.contains("username")) configuration.set("username", "root");
        if(!configuration.contains("password")) configuration.set("password", "password");

    }

    public void saveConfiguration() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> readData() {

        HashMap<String, String> config = new HashMap<>();
        config.put("host", configuration.getString("host"));
        config.put("port", configuration.getString("port"));
        config.put("database", configuration.getString("database"));
        config.put("username", configuration.getString("username"));
        config.put("password", configuration.getString("password"));

        return config;
    }

}
