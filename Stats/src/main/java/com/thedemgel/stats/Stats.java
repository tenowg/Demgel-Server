
package com.thedemgel.stats;

import com.thedemgel.playerfiles.ConfigKey;
import org.bukkit.plugin.java.JavaPlugin;


public class Stats extends JavaPlugin {
	public final static ConfigKey<Double> STAT_STR = ConfigKey.newConfigKeyFromString("stats.str", 0D);
	public final static ConfigKey<Double> STAT_DEX = ConfigKey.newConfigKeyFromString("stats.dex", 0D);
	public final static ConfigKey<Double> STAT_BOD = ConfigKey.newConfigKeyFromString("stats.bod", 0D);
	public final static ConfigKey<Double> STAT_MIN = ConfigKey.newConfigKeyFromString("stats.min", 0D);
	public final static ConfigKey<Double> STAT_WIS = ConfigKey.newConfigKeyFromString("stats.wis", 0D);
	public final static ConfigKey<Double> STAT_CHR = ConfigKey.newConfigKeyFromString("stats.chr", 0D);
	public final static ConfigKey<Double> EXPERIENCE = ConfigKey.newConfigKeyFromString("experience.total", 0D);
	public final static ConfigKey<Double> EXPERIENCE_BONUS = ConfigKey.newConfigKeyFromString("experience.bonus", 0D);

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new StatsListener(), this);
	}

	@Override
	public void onDisable() {

	}
}
