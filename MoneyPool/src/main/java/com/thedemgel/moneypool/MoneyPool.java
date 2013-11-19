
package com.thedemgel.moneypool;

import org.bukkit.plugin.java.JavaPlugin;


public class MoneyPool extends JavaPlugin {
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new MoneyEvents(), this);
	}

	@Override
	public void onDisable() {

	}
}
