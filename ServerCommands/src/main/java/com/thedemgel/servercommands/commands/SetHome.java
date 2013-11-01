
package com.thedemgel.servercommands.commands;

import com.thedemgel.servercommands.ServerCommands;
import com.thedemgel.servercommands.util.Permissions;
import com.thedemgel.servercommands.util.ResponseObject;
import com.thedemgel.servercommands.util.ResponseObjectType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHome implements CommandExecutor {

	private ServerCommands plugin;

	public SetHome(ServerCommands instance) {
		this.plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ResponseObject response;

		switch (cmd.getName().toLowerCase()) {
			case "sethome":
				response = plugin.getHomes().processHome(sender, args);
				break;
			case "home":
				response = plugin.getHomes().doHome(sender, args);
				break;
			default:
				return false;
		}

		if (!response.getMessage().equals("")) {
			ChatColor color;
			if (response.getResponseObjectType().equals(ResponseObjectType.SUCCESS)) {
				color = ChatColor.GREEN;
			} else {
				color = ChatColor.RED;
			}

			sender.sendMessage(color + response.getMessage());
		}

		return true;
	}
}
