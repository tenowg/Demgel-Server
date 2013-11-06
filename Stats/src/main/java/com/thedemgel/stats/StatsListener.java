
package com.thedemgel.stats;

import com.thedemgel.playerfiles.ConfigValue;
import com.thedemgel.playerfiles.PlayerFiles;
import com.thedemgel.playerfiles.PlayerObject;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class StatsListener implements Listener {

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		System.out.println("Join");

		PlayerObject pobj = PlayerFiles.getPlayerFile(event.getPlayer());

		// Check all stats
		ConfigValue<Double> defaultstats = new ConfigValue(6.0);
		if (!pobj.getConfig().keyExists(Stats.STAT_STR)) {
			pobj.setValue(Stats.STAT_STR, defaultstats);
		}
		if (!pobj.getConfig().keyExists(Stats.STAT_DEX)) {
			pobj.setValue(Stats.STAT_DEX, defaultstats);
		}
		if (!pobj.getConfig().keyExists(Stats.STAT_BOD)) {
			pobj.setValue(Stats.STAT_BOD, defaultstats);
		}
		if (!pobj.getConfig().keyExists(Stats.STAT_MIN)) {
			pobj.setValue(Stats.STAT_MIN, defaultstats);
		}
		if (!pobj.getConfig().keyExists(Stats.STAT_WIS)) {
			pobj.setValue(Stats.STAT_WIS, defaultstats);
		}
		if (!pobj.getConfig().keyExists(Stats.STAT_CHR)) {
			pobj.setValue(Stats.STAT_CHR, defaultstats);
		}
	}
}
