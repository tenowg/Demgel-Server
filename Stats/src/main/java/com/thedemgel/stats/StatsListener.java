package com.thedemgel.stats;

import com.thedemgel.playerfiles.ConfigValue;
import com.thedemgel.playerfiles.PlayerFiles;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StatsListener implements Listener {

	private final Stats plugin;

	@SuppressWarnings("ResultOfObjectAllocationIgnored")
	public StatsListener(Stats instance) {
		plugin = instance;

		// Create initial object to increase initial performance
		new initStats();
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {

		//long start = System.nanoTime();
		String player = event.getPlayer().getName();


		initStats init = new initStats(player);
		PlayerFiles.getExecutorService().submit(init);//execute(new initStats(player));

		//System.out.println(System.nanoTime() - start);
	}

	private class initStats implements Runnable {

		private final String player;

		public initStats() {
			player = "";
		}

		public initStats(String player) {
			this.player = player;
		}

		@Override
		public void run() {
			ConfigValue<Double> str = PlayerFiles.getValue(plugin, player, Stats.STAT_STR);
			ConfigValue<Double> dex = PlayerFiles.getValue(plugin, player, Stats.STAT_DEX);
			ConfigValue<Double> bod = PlayerFiles.getValue(plugin, player, Stats.STAT_BOD);
			ConfigValue<Double> min = PlayerFiles.getValue(plugin, player, Stats.STAT_MIN);
			ConfigValue<Double> wis = PlayerFiles.getValue(plugin, player, Stats.STAT_WIS);
			ConfigValue<Double> chr = PlayerFiles.getValue(plugin, player, Stats.STAT_CHR);

			ConfigValue<Long> exp = PlayerFiles.getValue(plugin, player, Stats.EXPERIENCE);
			ConfigValue<Double> exp_bonus = PlayerFiles.getValue(plugin, player, Stats.EXPERIENCE_BONUS);

			// Check all stats
			if (str == null) {
				PlayerFiles.setValue(plugin, player, Stats.STAT_STR);
			}
			if (dex == null) {
				PlayerFiles.setValue(plugin, player, Stats.STAT_DEX);
			}
			if (bod == null) {
				PlayerFiles.setValue(plugin, player, Stats.STAT_BOD);
			}
			if (min == null) {
				PlayerFiles.setValue(plugin, player, Stats.STAT_MIN);
			}
			if (wis == null) {
				PlayerFiles.setValue(plugin, player, Stats.STAT_WIS);
			}
			if (chr == null) {
				PlayerFiles.setValue(plugin, player, Stats.STAT_CHR);
			}
			if (exp == null) {
				PlayerFiles.setValue(plugin, player, Stats.EXPERIENCE);
			}
			if (exp_bonus == null) {
				PlayerFiles.setValue(plugin, player, Stats.EXPERIENCE_BONUS);
			}
		}
	}
}
