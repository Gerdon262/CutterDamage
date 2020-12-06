package me.greaperc4.cutterdamage.config;

import me.greaperc4.cutterdamage.CutterDamage;
import me.greaperc4.cutterdamage.enums.ConfigKeys;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Set;

public class Config {
    CutterDamage plugin;
    private final File configFile;
    private final EnumMap<ConfigKeys, Object> configEnum;

    public Config(CutterDamage plugin) {
        this.plugin = plugin;

        configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        this.configEnum = new EnumMap<>(ConfigKeys.class);

        createConfigFile();
        setupConfig();
        loadValues();
    }

    private void createConfigFile() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                this.plugin.sendError(Collections.singletonList(String.format("Creating config.yml\n%s", e.getMessage())));
            }
        }
    }

    private void setupConfig() {
        YamlConfiguration config = getConfig();

        for (ConfigKeys key : ConfigKeys.values()) {
            if (!config.isSet(key.getConfigName())) {
                config.set(key.getConfigName(), key.getDefaultValue());
            }
        }

        try {
            config.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadValues() {
        YamlConfiguration config = getConfig();
        for (ConfigKeys key : ConfigKeys.values()) {
            if (key.getDefaultValue() instanceof HashMap) {
                Set<String> keys = config.getConfigurationSection(key.getConfigName()).getKeys(false);
                HashMap<Object, Object> map = new HashMap<>();
                for (String configKey : keys) {
                    map.put(configKey, config.get(String.format("%s.%s", key.getConfigName(), configKey)));
                }
                this.configEnum.put(key, map);
            } else {
                this.configEnum.put(key, config.get(key.getConfigName()));
            }
        }
    }

    private YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public String getString(ConfigKeys key) {
        Object o = configEnum.get(key);
        if (!(o instanceof String)) {
            o = (String) key.getDefaultValue();
        }
        return (String) o;
    }

    public boolean getBoolean(ConfigKeys key) {
        Object o = configEnum.get(key);
        if (!(o instanceof Boolean)) {
            o = (Boolean) key.getDefaultValue();
        }
        return (Boolean) o;
    }

    public int getInt(ConfigKeys key) {
        Object o = configEnum.get(key);
        if (!(o instanceof Integer)) {
            o = (Integer) key.getDefaultValue();
        }
        return (Integer) o;
    }

    public double getDouble(ConfigKeys key) {
        Object o = configEnum.get(key);
        if (!(o instanceof Double)) {
            o = (Integer) key.getDefaultValue();
        }
        return (Double) o;
    }
}
