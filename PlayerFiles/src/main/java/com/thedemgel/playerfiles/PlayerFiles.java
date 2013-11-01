
package com.thedemgel.playerfiles;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class PlayerFiles extends JavaPlugin {
	private static ConcurrentMap<String, PlayerObject> playerFiles;

	@Override
	public void onEnable() {
		playerFiles = new ConcurrentHashMap<>();
		getServer().getPluginManager().registerEvents(new PListener(this), this);
	}

	@Override
	public void onDisable() {
		for (PlayerObject obj : playerFiles.values()) {
			obj.saveConfig();
		}
	}

	public PlayerObject addPlayerFile(Player player) {
		PlayerObject obj = new PlayerObject(this, player);
		ConfigValue<Double> test = obj.getValue(PlayerObject.defaulttest);
		System.out.println(obj.getValue(PlayerObject.defaulttest).getValue());
		return playerFiles.put(player.getName(), obj);
	}

	public void removePlayerFile(Player player) {
		removePlayerFile(player.getName());
	}

	public void removePlayerFile(String player) {
		PlayerObject obj = playerFiles.remove(player);

		if (obj != null) {
			obj.getFileConfig();
			obj.setValue(PlayerObject.defaulttest, new ConfigValue<>(0.0));
			obj.saveConfig();
		}
	}

	public static PlayerObject getPlayerFile(Player player) {
		return playerFiles.get(player.getName());
	}
}
