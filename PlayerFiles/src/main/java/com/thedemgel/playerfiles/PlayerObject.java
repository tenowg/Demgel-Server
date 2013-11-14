
package com.thedemgel.playerfiles;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class PlayerObject {
	private Player player;
	private ConcurrentMap<JavaPlugin, PlayerConfig> configs;
	private PlayerFiles plugin;

	public PlayerObject(PlayerFiles plugin, Player bukkitPlayer) {

		player = bukkitPlayer;
		this.plugin = plugin;
		if (!PlayerFiles.isMysql()) {
			configs = new ConcurrentHashMap<>();
			pluginConfig(plugin, bukkitPlayer.getName());
		}

	}

	public void pluginConfig(JavaPlugin plugin, String player) {
		PlayerConfig conf = new PlayerConfig(plugin, player);
		configs.put(plugin, conf);
	}

	public Player getPlayer() {
		return player;
	}

	public <T> void setValue(ConfigKey<T> key) {
		setValue(plugin, key, key.getDefaultValue());
	}

	public <T> void setValue(JavaPlugin javaplugin, ConfigKey<T> key) {
		setValue(javaplugin, key, key.getDefaultValue());
	}

	public <T> void setValue(ConfigKey<T> key, ConfigValue<T> value) {
		setValue(plugin, key, value);
	}
	
	public <T> void setValue(JavaPlugin plugin, ConfigKey<T> key, ConfigValue<T> value) {
		// If using database
		if (PlayerFiles.isMysql()) {
			PlayerFiles.getDatabaseObject().insertValue(plugin, player.getName(), key, value);
			return;
		}

		// If not using database
		if (!configs.containsKey(plugin)) {
			pluginConfig(plugin, player.getName());
		}

		PlayerConfig config = configs.get(plugin);

		config.setConfigValue(key, value);
	}

	public <T> ConfigValue<T> getValue(ConfigKey<T> key) {
		return getValue(plugin, key);
	}

	public <T> ConfigValue<T> getValue(JavaPlugin plugin, ConfigKey<T> key) {
		// If using database
		if (PlayerFiles.isMysql()) {
			return PlayerFiles.getDatabaseObject().getValue(plugin, player.getName(), key);
		}

		// If not using database
		if (!configs.containsKey(plugin)) {
			pluginConfig(plugin, player.getName());
		}

		PlayerConfig config = configs.get(plugin);

		return config.getConfigValue(key);
	}

	/*public <T> ConfigurationSection getConfigSection(ConfigKey<T> key) {
		return getConfigSection(plugin, key);
	}*/

	/*public <T> ConfigurationSection getConfigSection(JavaPlugin plugin, ConfigKey<T> key) {
		if (!configs.containsKey(plugin)) {
			pluginConfig(plugin, player.getName());
		}

		PlayerConfig config = configs.get(plugin);

		ConfigurationSection section = config.getConfig().getConfigurationSection(key.getKey());

		if (section == null) {
			section = config.getConfig().createSection(key.getKey());
		}

		return section;
	}*/

	public PlayerConfig getConfig() {
		return getConfig(plugin);
	}

	public PlayerConfig getConfig(JavaPlugin plugin) {
		if (!configs.containsKey(plugin)) {
			pluginConfig(plugin, player.getName());
		}

		PlayerConfig config = configs.get(plugin);

		return config;
	}

	/*public FileConfiguration getFileConfig() {
		return getFileConfig(plugin);
	}*/

	/*public FileConfiguration getFileConfig(JavaPlugin plugin) {
		if (!configs.containsKey(plugin)) {
			pluginConfig(plugin, player.getName());
		}

		PlayerConfig config = configs.get(plugin);

		return config.getConfig();
	}*/

	public void saveConfigs() {
		// Only run if not using Database
		if (PlayerFiles.isMysql()) {
			return;
		}

		for (Entry<JavaPlugin, PlayerConfig> con : configs.entrySet()) {
			saveConfig(con.getKey());
		}
	}
	public void saveConfig() {
		saveConfig(plugin);
	}

	public void saveConfig(JavaPlugin plugin) {
		if (!configs.containsKey(plugin)) {
			pluginConfig(plugin, player.getName());
		}

		PlayerConfig config = configs.get(plugin);

		config.saveConfig();
	}
}
