package com.thedemgel.playerfiles;

import com.thedemgel.playerfiles.data.MysqlDataObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerFiles extends JavaPlugin {

	private static ConcurrentMap<String, PlayerObject> playerFiles;
	private static boolean mysql = true;
	private static MysqlDataObject dbo;
	public static final ConfigKey<Integer> KEYS_LOGINS = ConfigKey.newConfigKeyFromString("logins.total", 1);

	public static boolean isMysql() {
		return mysql;
	}

	public static MysqlDataObject getDatabaseObject() {
		return dbo;
	}

	@Override
	public void onEnable() {
		playerFiles = new ConcurrentHashMap<>();
		getServer().getPluginManager().registerEvents(new PListener(this), this);

		getConfig().options().copyDefaults(true);
		saveConfig();

		if (getConfig().getString("database.type").equalsIgnoreCase("mysql")) {
			mysql = true;
			dbo = new MysqlDataObject(this);
		}

		initPlugin(this);
	}

	@Override
	public void onDisable() {
		// Only save yml if not using database
		if (!isMysql()) {
			for (PlayerObject obj : playerFiles.values()) {
				obj.saveConfig();
			}
		}

	}

	public PlayerObject addPlayerFile(Player player) {
		PlayerObject obj = new PlayerObject(this, player);
		return playerFiles.put(player.getName(), obj);
	}

	public void removePlayerFile(Player player) {
		removePlayerFile(player.getName());
	}

	public void removePlayerFile(String player) {
		PlayerObject obj = playerFiles.remove(player);

		// Only save the obj if not using Database
		if (!isMysql()) {
			if (obj != null) {
				obj.saveConfigs();
			}
		}
	}

	public static PlayerObject getPlayerFile(Player player) {
		return playerFiles.get(player.getName());
	}

	public static void initPlugin(JavaPlugin plugin) {
		if (mysql) {
			dbo.createPluginTable(plugin);
		}
	}
}
