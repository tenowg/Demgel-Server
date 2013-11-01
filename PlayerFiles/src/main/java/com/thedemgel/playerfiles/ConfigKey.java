
package com.thedemgel.playerfiles;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;


public class ConfigKey<T> {
	private T value;
	private String configKey;

	public ConfigKey(String key) {
		configKey = key;
		value = null;
	}

	public ConfigKey(String key, T defaultValue) {
		configKey = key;
		value = defaultValue;
	}

	public T getDefaultValue() {
		return value;
	}

	public String getKey() {
		return configKey;
	}

	public static <T> ConfigKey newConfigKeyFromSection(ConfigurationSection section, String key) {
		return new ConfigKey<>(section.getCurrentPath() + "." + key);
	}

	public static <T> ConfigKey newConfigKeyFromSection(ConfigurationSection section, String key, T defaultValue) {
		return new ConfigKey<>(section.getCurrentPath() + "." + key);
	}

	public static <T> ConfigKey<T> newConfigKeyFromConfigKey(ConfigKey configkey, String... key) {
		String concat = StringUtils.join(key, ".");
		return new ConfigKey<>(configkey.getKey() + "." + concat);
	}

	public static <T> ConfigKey<T> newConfigKeyFromString(String key) {
		return new ConfigKey<>(key);
	}

	public static <T> ConfigKey<T> newConfigKeyFromString(String key, T defaultValue) {
		return new ConfigKey<>(key);
	}
}
