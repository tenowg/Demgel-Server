
package com.thedemgel.servercommands;

import com.thedemgel.playerfiles.PlayerFiles;
import com.thedemgel.servercommands.commands.PluginList;
import com.thedemgel.servercommands.commands.SetHome;
import com.thedemgel.servercommands.commands.TeleportCommands;
import com.thedemgel.servercommands.events.ServerCommandOverRideEvents;
import org.bukkit.plugin.java.JavaPlugin;


public class ServerCommands extends JavaPlugin {
	private Homes homes;

	@Override
	public final void onEnable() {
		homes = new Homes(this);
		getCommand("tele").setExecutor(new TeleportCommands());
		getCommand("sethome").setExecutor(new SetHome(this));
		getCommand("home").setExecutor(new SetHome(this));
		//getCommand("plugins").setExecutor(new PluginList(this));
		getServer().getPluginManager().registerEvents(new ServerCommandOverRideEvents(), this);
		PlayerFiles.initPlugin(this);
	}

	// Get All Modules
	public Homes getHomes() {
		return homes;
	}
}
