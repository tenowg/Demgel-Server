
package com.thedemgel.servercommands.commands;

import com.thedemgel.servercommands.ServerCommands;
import com.thedemgel.servercommands.util.Permissions;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PluginList implements CommandExecutor {

	private ServerCommands plugin;

	public PluginList(ServerCommands instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getLogger().log(Level.WARNING, "No Console Please...");
			return true;
		}

		if (!sender.hasPermission(Permissions.VIEW_PLUGINS)) {
			sender.sendMessage(ChatColor.RED + "You can't do that...");
			return true;
		}

		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			sender.sendMessage(plugin.getDescription().getName() + "(" + plugin.getDescription().getVersion() + "): " + plugin.getDescription().getDescription());
		}

		return true;
	}
}
