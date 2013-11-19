
package com.thedemgel.moneypool;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;


public class MoneyEvents implements Listener {

	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent event) {
		System.out.println("Does this happen on login?");
	}
}
