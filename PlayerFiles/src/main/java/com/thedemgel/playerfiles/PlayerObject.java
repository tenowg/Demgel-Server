
package com.thedemgel.playerfiles;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class PlayerObject {
	private Player player;
	private PlayerConfig config;
	public static final ConfigKey<String> test = ConfigKey.newConfigKeyFromString("test", "default");
	public static final ConfigKey<Double> defaulttest = ConfigKey.newConfigKeyFromString("default", 4.0);

	public PlayerObject(PlayerFiles plugin, Player bukkitPlayer) {
		player = bukkitPlayer;
		config = new PlayerConfig(plugin, player);
	}

	public Player getPlayer() {
		return player;
	}

	public <T> void setValue(ConfigKey<T> key, ConfigValue<T> value) {
		config.setConfigValue(key, value);
	}

	public <T> ConfigValue<T> getValue(ConfigKey<T> key) {
		return config.getConfigValue(key);
	}

	public <T> ConfigurationSection getConfigSection(ConfigKey<T> key) {
		ConfigurationSection section = config.getConfig().getConfigurationSection(key.getKey());

		if (section == null) {
			section = config.getConfig().createSection(key.getKey());
		}

		return section;
	}

	public PlayerConfig getConfig() {
		return config;
	}

	public FileConfiguration getFileConfig() {
		return config.getConfig();
	}

	public void saveConfig() {
		config.saveConfig();
	}
}
