
package com.thedemgel.stats;

import com.thedemgel.playerfiles.ConfigValue;
import com.thedemgel.playerfiles.PlayerConfig;
import com.thedemgel.playerfiles.PlayerFiles;
import com.thedemgel.playerfiles.PlayerObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class StatsListener implements Listener {
	private Stats plugin;

	public StatsListener(Stats instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {

		PlayerObject pobj = PlayerFiles.getPlayerFile(event.getPlayer());
		//PlayerConfig pconf = pobj.getConfig(plugin);

		// Check all stats
		ConfigValue<Double> str = pobj.getValue(plugin, Stats.STAT_STR);
		ConfigValue<Double> dex = pobj.getValue(plugin, Stats.STAT_DEX);
		ConfigValue<Double> bod = pobj.getValue(plugin, Stats.STAT_BOD);
		ConfigValue<Double> min = pobj.getValue(plugin, Stats.STAT_MIN);
		ConfigValue<Double> wis = pobj.getValue(plugin, Stats.STAT_WIS);
		ConfigValue<Double> chr = pobj.getValue(plugin, Stats.STAT_CHR);

		ConfigValue<Long> exp = PlayerFiles.getDatabaseObject().getValue(plugin, pobj.getPlayer().getName(), Stats.EXPERIENCE);
		ConfigValue<Double> exp_bonus = PlayerFiles.getDatabaseObject().getValue(plugin, pobj.getPlayer().getName(), Stats.EXPERIENCE_BONUS);


		if (str == null) {
			pobj.setValue(plugin, Stats.STAT_STR);
		}
		if (dex == null) {
			pobj.setValue(plugin, Stats.STAT_DEX);
		}
		if (bod == null) {
			pobj.setValue(plugin, Stats.STAT_BOD);
		}
		if (min == null) {
			pobj.setValue(plugin, Stats.STAT_MIN);
		}
		if (wis == null) {
			pobj.setValue(plugin, Stats.STAT_WIS);
		}
		if (chr == null) {
			pobj.setValue(plugin, Stats.STAT_CHR);
		}
		if (exp == null) {
			pobj.setValue(plugin, Stats.EXPERIENCE);
		}
		if (exp_bonus == null) {
			pobj.setValue(plugin, Stats.EXPERIENCE_BONUS);
		}
	}
}
