package com.thedemgel.playerfiles;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerConfig {

	private final String fileName;
	private final JavaPlugin plugin;
	private File configFile;
	private FileConfiguration fileConfiguration;

	public PlayerConfig(JavaPlugin plugin, Player player) {
		this(plugin, player.getName());
	}

	public PlayerConfig(JavaPlugin plugin, String playername) {
		if (plugin == null) {
			throw new IllegalArgumentException("plugin cannot be null");
		}
		if (!plugin.isInitialized()) {
			throw new IllegalArgumentException("plugin must be initiaized");
		}
		this.plugin = plugin;
		this.fileName = playername + ".yml";
		File dataFolder = new File(plugin.getDataFolder() + "/players");
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
		this.configFile = new File(dataFolder, fileName);
	}

	public void reloadConfig() {
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);

		// Look for defaults in the jar
		InputStream defConfigStream = plugin.getResource(fileName);
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			//fileConfiguration.options().copyDefaults(true);
			fileConfiguration.setDefaults(defConfig);
		}
	}

	public FileConfiguration getConfig() {
		if (fileConfiguration == null) {
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	public void saveConfig() {
		if (fileConfiguration != null && configFile != null) {
			try {
				getConfig().save(configFile);
			} catch (IOException ex) {
				plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
			}
		} else {
			plugin.getLogger().log(Level.SEVERE, "No Config file to save..." + configFile + "  " + fileConfiguration);
		}
	}

	public void saveDefaultConfig() {
		if (!configFile.exists()) {
			this.plugin.saveResource(fileName, false);
		}
	}

	public <T> ConfigValue<T> getConfigValue(ConfigKey<T> key) {
		ConfigValue<T> config = new ConfigValue(getConfig().get(key.getKey(), key.getDefaultValue()));
		return config;
	}

	public <T> void setConfigValue(ConfigKey<T> key, ConfigValue<T> value) {
		getConfig().set(key.getKey(), value.getValue());
	}

	public boolean keyExists(ConfigKey key) {
		return getConfig().contains(key.getKey());
	}
}
