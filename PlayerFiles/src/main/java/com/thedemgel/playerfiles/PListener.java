
package com.thedemgel.playerfiles;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PListener implements Listener {

	private final PlayerFiles plugin;

	public PListener(PlayerFiles instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		plugin.addPlayerFile(event.getPlayer());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		/*List<Integer> testlist = new ArrayList<>();
		testlist.add(42);
		testlist.add(63);

		ConfigKey<List<Integer>> testconflist = ConfigKey.newConfigKeyFromString("test.value", testlist);
		ConfigValue<List<Integer>> testvaluelist = new ConfigValue(testlist);
		PlayerFiles.getDatabaseObject().insertValue(plugin, event.getPlayer().getName(), testconflist, testvaluelist);
		ConfigValue<List<Integer>> testing = PlayerFiles.getDatabaseObject().getValue(plugin, event.getPlayer().getName(), testconflist);
		for (Integer string : testing.getValue()) {
			System.out.println(string);
		}*/

		ConfigValue<Integer> test = PlayerFiles.getDatabaseObject().getValue(plugin, event.getPlayer().getName(), PlayerFiles.KEYS_LOGINS);

		if (test == null) {
			test = PlayerFiles.KEYS_LOGINS.getDefaultValue();
		} else {
			test.setValue(test.getValue() + 1);

		}

		PlayerFiles.getDatabaseObject().insertValue(plugin, event.getPlayer().getName(), PlayerFiles.KEYS_LOGINS, test);

		event.getPlayer().sendMessage("You have logged in " + test.getValue() + " times.");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.removePlayerFile(event.getPlayer());
	}
}
