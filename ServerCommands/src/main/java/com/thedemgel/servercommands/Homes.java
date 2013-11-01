package com.thedemgel.servercommands;

import com.thedemgel.playerfiles.ConfigKey;
import com.thedemgel.playerfiles.ConfigValue;
import com.thedemgel.playerfiles.PlayerFiles;
import com.thedemgel.playerfiles.PlayerObject;
import com.thedemgel.servercommands.util.Permissions;
import com.thedemgel.servercommands.util.ResponseObject;
import com.thedemgel.servercommands.util.ResponseObjectType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Homes {
	public static final String DEFAULT_HOME_NAME = "default";
	public static final ConfigKey<ConfigurationSection> HOME_SECTION = ConfigKey.newConfigKeyFromString("homes");

	private ServerCommands plugin;

	public Homes(ServerCommands instance) {
		this.plugin = instance;
	}

	public ResponseObject processHome(CommandSender player, String[] args) {
		if (!(player instanceof Player)) {
			return new ResponseObject("Cannot send from Console.", ResponseObjectType.FAILURE);
		}

		if (!player.hasPermission(Permissions.SET_HOME)) {
			return new ResponseObject("You don't have permission to set a home.", ResponseObjectType.FAILURE);
		}

		// Check if able to set multiple homes if args is greater than 0
		if (args.length > 0 && player.hasPermission(Permissions.SET_MULTI_HOME)) {
			return setHome(player, args[0]);
		} else if (args.length > 0) {
			return new ResponseObject("You don't have permissions to name or set multiple homes.", ResponseObjectType.FAILURE);
		}

		return setHome(player, "default");
	}

	private ResponseObject setHome(CommandSender sender, String homename) {
		Player player = (Player) sender;
		PlayerObject pobj = PlayerFiles.getPlayerFile(player);

		ConfigKey key = ConfigKey.newConfigKeyFromConfigKey(HOME_SECTION, homename);

		ConfigKey<String> world = ConfigKey.newConfigKeyFromConfigKey(key,"location", "world");
		ConfigKey<Double> x = ConfigKey.newConfigKeyFromConfigKey(key,"location", "x");
		ConfigKey<Double> y = ConfigKey.newConfigKeyFromConfigKey(key,"location", "y");
		ConfigKey<Double> z = ConfigKey.newConfigKeyFromConfigKey(key,"location", "z");

		pobj.setValue(world, new ConfigValue<>(player.getWorld().getName()));
		pobj.setValue(x, new ConfigValue<>(player.getLocation().getX()));
		pobj.setValue(y, new ConfigValue<>(player.getLocation().getY()));
		pobj.setValue(z, new ConfigValue<>(player.getLocation().getZ()));

		return new ResponseObject("Home set Successfully", ResponseObjectType.SUCCESS);
	}

	public ResponseObject doHome(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			return new ResponseObject("Cannot send from Console.", ResponseObjectType.FAILURE);
		}

		if (!sender.hasPermission(Permissions.SET_HOME)) {
			return new ResponseObject("You don't have permission to teleport to a home.", ResponseObjectType.FAILURE);
		}

		// Check if able to set multiple homes if args is greater than 0
		if (args.length > 0 && sender.hasPermission(Permissions.SET_MULTI_HOME)) {
			return teleportHome(sender, args[0]);
		} else if (args.length > 0) {
			return new ResponseObject("You don't have permissions to teleport to a named home.", ResponseObjectType.FAILURE);
		}

		return teleportHome(sender, "default");
	}

	public ResponseObject teleportHome(CommandSender sender, String homename) {
		Player player = (Player) sender;
		PlayerObject pobj = PlayerFiles.getPlayerFile(player);

		ConfigKey key = ConfigKey.newConfigKeyFromConfigKey(HOME_SECTION, homename);

		if (!pobj.getConfig().keyExists(key)) {
			return new ResponseObject("Home doesn't exist.", ResponseObjectType.FAILURE);
		}

		ConfigKey<String> worldKey = ConfigKey.newConfigKeyFromConfigKey(key,"location", "world");
		ConfigKey<Double> xKey = ConfigKey.newConfigKeyFromConfigKey(key,"location", "x");
		ConfigKey<Double> yKey = ConfigKey.newConfigKeyFromConfigKey(key,"location", "y");
		ConfigKey<Double> zKey = ConfigKey.newConfigKeyFromConfigKey(key,"location", "z");

		ConfigValue<Double> x = pobj.getValue(xKey);
		ConfigValue<Double> y = pobj.getValue(yKey);
		ConfigValue<Double> z = pobj.getValue(zKey);
		ConfigValue<String> world = pobj.getValue(worldKey);

		World bworld = Bukkit.getWorld(world.getValue());

		Location loc = new Location(bworld, x.getValue(), y.getValue(), z.getValue());

		player.teleport(loc);

		return new ResponseObject("Teleporting to " + homename.toLowerCase() + "...", ResponseObjectType.SUCCESS);
	}
}
