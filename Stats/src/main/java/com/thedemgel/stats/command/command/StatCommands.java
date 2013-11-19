package com.thedemgel.stats.command.command;

import com.thedemgel.playerfiles.ConfigValue;
import com.thedemgel.playerfiles.PlayerFiles;
import com.thedemgel.stats.Stats;
import com.thedemgel.stats.command.BukkitCommand;
import com.thedemgel.stats.command.Commands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StatCommands extends Commands implements CommandExecutor {

	private Stats plugin;

	public StatCommands(Stats instance) {
		this.plugin = instance;
	}

	@BukkitCommand(name = "list")
	public boolean stats(CommandSender sender, Command cmd, String label, String[] args) {

		String player = sender.getName();

		ConfigValue<Double> str = PlayerFiles.getValue(plugin, player, Stats.STAT_STR);
		ConfigValue<Double> dex = PlayerFiles.getValue(plugin, player, Stats.STAT_DEX);
		ConfigValue<Double> bod = PlayerFiles.getValue(plugin, player, Stats.STAT_BOD);
		ConfigValue<Double> min = PlayerFiles.getValue(plugin, player, Stats.STAT_MIN);
		ConfigValue<Double> wis = PlayerFiles.getValue(plugin, player, Stats.STAT_WIS);
		ConfigValue<Double> chr = PlayerFiles.getValue(plugin, player, Stats.STAT_CHR);

		ConfigValue<Long> exp = PlayerFiles.getValue(plugin, player, Stats.EXPERIENCE);
		ConfigValue<Double> exp_bonus = PlayerFiles.getValue(plugin, player, Stats.EXPERIENCE_BONUS);

		sender.sendMessage(Stats.prefix + "--------<" + ChatColor.DARK_GREEN + " Your Stats " + ChatColor.YELLOW + ">--------");
		sender.sendMessage(Stats.prefix + "Strength: " + ChatColor.WHITE + str.getValue());
		sender.sendMessage(Stats.prefix + "Dexterity: " + ChatColor.WHITE + dex.getValue());
		sender.sendMessage(Stats.prefix + "Body: " + ChatColor.WHITE + bod.getValue());
		sender.sendMessage(Stats.prefix + "Mind: " + ChatColor.WHITE +min.getValue());
		sender.sendMessage(Stats.prefix + "Wisdom: " + ChatColor.WHITE + wis.getValue());
		sender.sendMessage(Stats.prefix + "Charisma: " + ChatColor.WHITE + chr.getValue());
		sender.sendMessage(Stats.prefix + "Experience: " + ChatColor.WHITE + exp.getValue() + ChatColor.YELLOW + " Bonus: " + ChatColor.WHITE + exp_bonus.getValue());
		return true;
	}
}
