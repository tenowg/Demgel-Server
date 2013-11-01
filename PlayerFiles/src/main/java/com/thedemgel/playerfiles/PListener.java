
package com.thedemgel.playerfiles;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.removePlayerFile(event.getPlayer());
	}
}
