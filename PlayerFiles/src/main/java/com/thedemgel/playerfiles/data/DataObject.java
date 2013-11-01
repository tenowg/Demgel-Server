package com.thedemgel.playerfiles.data;

import com.thedemgel.playerfiles.PlayerObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class DataObject {

	private final ExecutorService pool;

	public DataObject() {
		pool = Executors.newSingleThreadExecutor();
		System.out.println("Pool created");
	}

	public abstract void save(PlayerObject playerObject);

	public abstract void load(int shopid);

	public abstract void initShops();

	public ExecutorService getPool() {
		return pool;
	}
}
