
package com.thedemgel.playerfiles;

import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;


public class ConfigKey<T> {
	private Class<? extends T> type;
	private T value;
	private String configKey;
	private ConfigValue<T> defaultvalue;

	public ConfigKey(String key, Class<? extends T> classtype) {
		configKey = key;
		value = null;
		type = classtype;
	}

	public ConfigKey(String key, T defaultValue) {
		configKey = key;
		value = defaultValue;
		defaultvalue = new ConfigValue(defaultValue);
		type = (Class<? extends T>) defaultValue.getClass();
	}

	public ConfigValue<T> getDefaultValue() {
		return defaultvalue;
	}

	public String getKey() {
		return configKey;
	}

	public static <T> ConfigKey newConfigKeyFromSection(ConfigurationSection section, Class<? extends T> type, String key) {
		return new ConfigKey<>(section.getCurrentPath() + "." + key, type);
	}

	public static <T> ConfigKey newConfigKeyFromSection(ConfigurationSection section, String key, T defaultValue) {
		return new ConfigKey<>(section.getCurrentPath() + "." + key, defaultValue);
	}

	public static <T> ConfigKey<T> newConfigKeyFromConfigKey(ConfigKey configkey, Class<? extends T> type, String... key) {
		String concat = StringUtils.join(key, ".");
		return new ConfigKey<>(configkey.getKey() + "." + concat, type);
	}

	public static <T> ConfigKey<T> newConfigKeyFromString(String key, Class<? extends T> type) {
		return new ConfigKey<>(key, type);
	}

	public static <T> ConfigKey<T> newConfigKeyFromString(String key, T defaultValue) {
		return new ConfigKey<>(key, defaultValue);
	}

	public Class<? extends T> getType() {
		return type;
	}
}
