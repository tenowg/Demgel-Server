package com.thedemgel.servercommands.commands;

import com.thedemgel.servercommands.ServerCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BaseCommandTemplate {

	private ServerCommands plugin;

	public BaseCommandTemplate(ServerCommands instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		return true;
	}
}
