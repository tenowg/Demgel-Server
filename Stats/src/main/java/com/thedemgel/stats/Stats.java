
package com.thedemgel.stats;

import com.thedemgel.playerfiles.ConfigKey;
import com.thedemgel.playerfiles.PlayerFiles;
import com.thedemgel.stats.command.command.StatCommands;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


public class Stats extends JavaPlugin {

	public final static String prefix = ChatColor.AQUA + "[Stats] " + ChatColor.YELLOW;

	public final static ConfigKey<Double> STAT_STR = ConfigKey.newConfigKeyFromString("stats.str", 6D);
	public final static ConfigKey<Double> STAT_DEX = ConfigKey.newConfigKeyFromString("stats.dex", 6D);
	public final static ConfigKey<Double> STAT_BOD = ConfigKey.newConfigKeyFromString("stats.bod", 6D);
	public final static ConfigKey<Double> STAT_MIN = ConfigKey.newConfigKeyFromString("stats.min", 6D);
	public final static ConfigKey<Double> STAT_WIS = ConfigKey.newConfigKeyFromString("stats.wis", 6D);
	public final static ConfigKey<Double> STAT_CHR = ConfigKey.newConfigKeyFromString("stats.chr", 6D);
	public final static ConfigKey<Long> EXPERIENCE = ConfigKey.newConfigKeyFromString("experience.total", 0L);
	public final static ConfigKey<Double> EXPERIENCE_BONUS = ConfigKey.newConfigKeyFromString("experience.bonus", 0D);

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new StatsListener(this), this);
		getServer().getPluginCommand("stats").setExecutor(new StatCommands(this));
		PlayerFiles.initPlugin(this);
	}

	@Override
	public void onDisable() {

	}
}
