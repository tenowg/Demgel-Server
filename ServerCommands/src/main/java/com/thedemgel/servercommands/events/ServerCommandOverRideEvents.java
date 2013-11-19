package com.thedemgel.servercommands.events;

import com.thedemgel.servercommands.util.Permissions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

public class ServerCommandOverRideEvents implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		String message = event.getMessage();
		if (message.toLowerCase().startsWith("/pl ") || message.equalsIgnoreCase("/pl")
			|| message.toLowerCase().startsWith("/plugins ") || message.equalsIgnoreCase("/plugins")) {
			if (event.getPlayer().hasPermission(Permissions.VIEW_PLUGINS)) {
				String[] args = message.split(" ");
				int pluginscount = Bukkit.getPluginManager().getPlugins().length;
				int pages = (int) Math.ceil(pluginscount / 9.0);

				int page = 1;
				if (args.length > 1) {
					if (StringUtils.isNumeric(args[1])) {
						page = Integer.parseInt(args[1]);
					}
				}

				event.getPlayer().sendMessage(ChatColor.GREEN + "[" + page + "/" + pages + "] Plugins: ");

				for (int i = 0; i <= 8; i++) {
					int pluginnum = i + ((page - 1) * 9);
					if (pluginnum >= pluginscount) {
						break;
					} else {
						Plugin plugin = Bukkit.getPluginManager().getPlugins()[pluginnum];
						ChatColor enabled = ChatColor.RED;
						if (plugin.isEnabled()) {
							enabled = ChatColor.GREEN;
						}
						event.getPlayer().sendMessage(enabled + plugin.getDescription().getName() + ChatColor.GOLD + "(" + ChatColor.YELLOW + plugin.getDescription().getVersion() + ChatColor.GOLD + ")" + enabled + ": " + ChatColor.WHITE + plugin.getDescription().getDescription());
					}
				}
			} else {
				event.getPlayer().sendMessage(ChatColor.RED + "You can't do that...");
			}

			event.setCancelled(true);
		}
	}

}
