package com.thedemgel.servercommands.commands;

import com.thedemgel.servercommands.util.Permissions;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommands extends Commands implements CommandExecutor {

	@BukkitCommand(name = "setspawn")
	public boolean setSpawn(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (player.hasPermission(Permissions.SET_SPAWN)) {
			Location loc = player.getLocation();
			player.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			player.sendMessage("Spawn set.");
		} else {
			player.sendMessage("You don't have permissions.");
		}

		return true;
	}

	@BukkitCommand(name = "spawn")
	public boolean spawn(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		player.teleport(player.getWorld().getSpawnLocation());

		return true;
	}
}
