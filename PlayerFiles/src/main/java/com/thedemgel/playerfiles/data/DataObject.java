package com.thedemgel.playerfiles.data;

import com.thedemgel.playerfiles.PlayerObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.bukkit.entity.Player;

public abstract class DataObject {

	private final ExecutorService pool;

	public DataObject() {
		pool = Executors.newSingleThreadExecutor();
		System.out.println("Pool created");
	}

	public abstract void save(PlayerObject playerObject);

	public abstract void load(Player player);

	public ExecutorService getPool() {
		return pool;
	}
}
