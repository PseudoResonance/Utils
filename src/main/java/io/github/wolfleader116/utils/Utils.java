package io.github.wolfleader116.utils;

import java.util.logging.Logger;

import io.github.wolfleader116.utils.commands.BanC;
import io.github.wolfleader116.utils.commands.KickC;
import io.github.wolfleader116.utils.commands.SpawnMobC;
import io.github.wolfleader116.utils.commands.UtilsC;
import io.github.wolfleader116.wolfapi.WolfAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class Utils extends JavaPlugin implements Listener {

	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Utils plugin;
	
	public void onEnable() {
		plugin = this;
		this.saveDefaultConfig();
		if (Bukkit.getServer().getPluginManager().getPlugin("WolfAPI") == null) {
			log.severe("WolfAPI was not found on the server! Disabling Shops!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		getCommand("utils").setExecutor(new UtilsC());
		getCommand("spawnmob").setExecutor(new SpawnMobC());
		getCommand("kick").setExecutor(new KickC());
		getCommand("ban").setExecutor(new BanC());
		getCommand("unban").setExecutor(new BanC());
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {
		plugin = null;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerPreLoginEvent e) {
		Config c = new Config("bannedplayers", Utils.plugin);
		Config data = new Config("playerdata", Utils.plugin);
		data.getConfig().set(e.getName(), e.getUniqueId().toString());
		data.save();
		if (c.getConfig().contains(e.getUniqueId().toString())) {
			e.disallow(PlayerPreLoginEvent.Result.KICK_BANNED, c.getConfig().getString(e.getUniqueId().toString()));
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Config c = new Config("mutedplayers", Utils.plugin);
		Config data = new Config("playerdata", Utils.plugin);
		data.getConfig().set(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString());
		data.save();
		if (c.getConfig().contains(e.getPlayer().getUniqueId().toString())) {
			WolfAPI.message(c.getConfig().getString(e.getPlayer().getUniqueId().toString()), e.getPlayer(), "Utils");
		}
	}
	
	public static boolean isBanned(String uuid) {
		Config c = new Config("bannedplayers", Utils.plugin);
		if (c.getConfig().contains(uuid)) {
			return true;
		} else {
			return false;
		}
	}

}
