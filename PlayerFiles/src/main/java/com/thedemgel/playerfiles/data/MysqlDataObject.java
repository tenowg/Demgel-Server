package com.thedemgel.playerfiles.data;

import com.thedemgel.playerfiles.ConfigKey;
import com.thedemgel.playerfiles.ConfigValue;
import com.thedemgel.playerfiles.PlayerObject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import snaq.db.ConnectionPool;

public class MysqlDataObject extends DataObject {

	private final ConnectionPool conpool;
	private final JavaPlugin plugin;
	private FileConfiguration config;

	public MysqlDataObject(JavaPlugin plugin) {
		super();
		this.plugin = plugin;
		config = plugin.getConfig();
		try {
			Class c = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) c.newInstance();
			DriverManager.registerDriver(driver);
		} catch (ClassNotFoundException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Class not found: {0}", ex);
		} catch (InstantiationException | IllegalAccessException | SQLException ex) {
			Logger.getLogger(MysqlDataObject.class.getName()).log(Level.SEVERE, null, ex);
		}
		String url = "jdbc:mysql://"
			+ config.getString("database.url")
			+ ":"
			+ config.getInt("database.port")
			+ "/"
			+ config.getString("database.database")
			+ "?zeroDateTimeBehavior=convertToNull";

		conpool = new ConnectionPool("test",
			1,
			10,
			30,
			180000,
			url,
			config.getString("database.username"),
			config.getString("database.password"));
	}

	@Override
	public void save(PlayerObject playerObject) {
	}

	@Override
	public void load(Player player) {
	}

	public void createPluginTable(JavaPlugin javaplugin) {
		String createStatement = "CREATE TABLE IF NOT EXISTS `pf-" + javaplugin.getName() + "` ("
			+ "`player` varchar(45) NOT NULL,"
			+ "`key` varchar(200) NOT NULL,"
			+ "`value` blob DEFAULT NULL,"
			+ " PRIMARY KEY (`player`,`key`)"
			+ " ) ENGINE=InnoDB DEFAULT CHARSET=utf8";

		Connection con = null;
		long timeout = 3;  // 3 second timeout

		try {
			con = conpool.getConnection(timeout);
			if (con != null) {
				PreparedStatement stat = con.prepareStatement(createStatement);
				stat.execute();
			} else {
				// ...do something else (timeout occurred)...
			}
		} catch (SQLException sqlx) {
			Bukkit.getLogger().log(Level.SEVERE, sqlx.getMessage());
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqlx) {
				/* ... */
			}
		}
	}

	public <T> ConfigValue<T> getValue(JavaPlugin plugin, String player, ConfigKey<T> key) {
		long timeout = 3;
		ConfigValue<T> confvalue = null;

		String testsql2 = "SELECT value FROM `pf-" + plugin.getName() + "` WHERE "
			+ "`player` = ? AND `key` = ?";

		try (Connection con = conpool.getConnection(timeout)) {
			if (con != null) {
				PreparedStatement statement = con.prepareStatement(testsql2);
				statement.setString(1, player);
				statement.setString(2, key.getKey());
				statement.execute();

				ResultSet result = statement.getResultSet();

				if (result.isBeforeFirst()) {
					result.next();
					Blob value = result.getBlob("value");

					byte[] bdata = value.getBytes(1, (int) value.length());
					String text = new String(bdata);
					if (key.getType().equals(Double.class)) {
						confvalue = new ConfigValue(Double.valueOf(text));
					} else if (key.getType().equals(Integer.class)) {
						confvalue = new ConfigValue(Integer.valueOf(text));
					} else if (key.getType().equals(Long.class)) {
						confvalue = new ConfigValue(Long.valueOf(text));
					} else if (key.getType().equals(Float.class)) {
						confvalue = new ConfigValue(Float.valueOf(text));
					} else if (key.getType().equals(String.class)) {
						confvalue = new ConfigValue(text);
					} else if (key.getType().equals(Boolean.class)) {
						if (text.equals("0")) {
							confvalue = new ConfigValue(false);
						} else {
							confvalue = new ConfigValue(true);
						}
					} else {
						ByteArrayInputStream baip = new ByteArrayInputStream(bdata);

						try {
							ObjectInputStream ois = new ObjectInputStream(baip);
							Object emp = ois.readObject();
							confvalue = new ConfigValue(key.getType().cast(emp));
						} catch (IOException | ClassNotFoundException ex) {
							Logger.getLogger(MysqlDataObject.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(MysqlDataObject.class.getName()).log(Level.SEVERE, null, ex);
		}

		return confvalue;
	}

	public void insertDefaultValue(JavaPlugin plugin, String player, ConfigKey key) {
		insertValue(plugin, player, key.getKey(), key.getDefaultValue().getValue());
	}

	public void insertValue(JavaPlugin plugin, String player, ConfigKey key, ConfigValue value) {
		insertValue(plugin, player, key.getKey(), value.getValue());
	}

	public void insertValue(JavaPlugin plugin, String player, String key, Object value) {
		//Connection con = null;
		long timeout = 3000;

		String testsql2 = "INSERT INTO `pf-" + plugin.getName() + "` (`player`, `key`, `value`) VALUES("
			+ "?, ?, ?"
			+ ") ON DUPLICATE KEY UPDATE `value` = ?";

		try (Connection con = conpool.getConnection(timeout)) {
			if (con != null) {
				PreparedStatement statement = con.prepareStatement(testsql2);
				statement.setString(1, player);
				statement.setString(2, key);
				statement.setObject(3, (Object) value);
				statement.setObject(4, (Object) value);
				statement.execute();
			}
		} catch (SQLException ex) {
			Logger.getLogger(MysqlDataObject.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
