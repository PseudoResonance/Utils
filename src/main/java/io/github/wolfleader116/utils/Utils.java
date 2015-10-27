package io.github.wolfleader116.utils;

import java.io.File;
import java.util.logging.Logger;

import io.github.wolfleader116.utils.commands.BanC;
import io.github.wolfleader116.utils.commands.KickC;
import io.github.wolfleader116.utils.commands.MuteC;
import io.github.wolfleader116.utils.commands.SpawnMobC;
import io.github.wolfleader116.utils.commands.UtilsC;
import io.github.wolfleader116.wolfapi.WolfAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.Dispenser;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils extends JavaPlugin implements Listener {

	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Utils plugin;
	
	public void onEnable() {
		plugin = this;
		this.saveDefaultConfig();
		if (this.getConfig().getInt("Version") != 1) {
			File config = new File(this.getDataFolder(), "config.yml");
			config.delete();
			this.saveDefaultConfig();
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("WolfAPI") == null) {
			log.severe("WolfAPI was not found on the server! Disabling Shops!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		getCommand("utils").setExecutor(new UtilsC());
		getCommand("spawnmob").setExecutor(new SpawnMobC());
		getCommand("kick").setExecutor(new KickC());
		getCommand("mute").setExecutor(new MuteC());
		getCommand("unmute").setExecutor(new MuteC());
		getCommand("ban").setExecutor(new BanC());
		getCommand("unban").setExecutor(new BanC());
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {
		plugin = null;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
		Config c = new Config("bannedplayers", Utils.plugin);
		Config data = new Config("playerdata", Utils.plugin);
		data.getConfig().set(e.getName(), e.getUniqueId().toString());
		data.save();
		if (c.getConfig().contains(e.getUniqueId().toString())) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, c.getConfig().getString(e.getUniqueId().toString()));
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
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (this.getConfig().getBoolean("InfiniteFireworks")) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.hasItem() && e.getItem().getType() == Material.FIREWORK) {
					Location loc = e.getPlayer().getEyeLocation().toVector().add(e.getPlayer().getLocation().getDirection().multiply(2)).toLocation(e.getPlayer().getWorld(), e.getPlayer().getLocation().getYaw(), e.getPlayer().getLocation().getPitch());
					ItemStack item = e.getItem();
					Firework firework = (Firework) e.getPlayer().getWorld().spawnEntity(loc, EntityType.FIREWORK);
					FireworkMeta fwm = (FireworkMeta)item.getItemMeta();
					firework.setFireworkMeta(fwm);
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDispenser(BlockDispenseEvent e) {
		if (this.getConfig().getBoolean("InfiniteFireworks")) {
			Block b = e.getBlock();
			if (b.getType() == Material.DISPENSER && e.getItem().getType() == Material.FIREWORK) {
				ItemStack item = e.getItem();
				FireworkMeta fwm = (FireworkMeta)item.getItemMeta();
				Dispenser disp = (Dispenser) b.getState().getData();
				Location loc = b.getRelative(disp.getFacing(), 1).getLocation();
				if (loc.getBlock().isEmpty()) {
					Location fire = loc;
					fire.setX(fire.getX() + 0.5);
					fire.setZ(fire.getZ() + 0.5);
					fire.setY(fire.getY() + 0.5);
					Firework firework = (Firework) b.getWorld().spawnEntity(fire, EntityType.FIREWORK);
					firework.setFireworkMeta(fwm);
					e.setCancelled(true);
				} else if (!(loc.getBlock().isEmpty())) {
					Location loca = b.getRelative(disp.getFacing(), 2).getLocation();
					Location fire = loca;
					fire.setX(fire.getX() + 0.5);
					fire.setZ(fire.getZ() + 0.5);
					fire.setY(fire.getY() + 0.5);
					Firework firework = (Firework) b.getWorld().spawnEntity(fire, EntityType.FIREWORK);
					firework.setFireworkMeta(fwm);
					e.setCancelled(true);
				} else {
					Location fire = loc;
					fire.setX(fire.getX() + 0.5);
					fire.setZ(fire.getZ() + 0.5);
					fire.setY(fire.getY() + 0.5);
					Firework firework = (Firework) b.getWorld().spawnEntity(fire, EntityType.FIREWORK);
					firework.setFireworkMeta(fwm);
					e.setCancelled(true);
				}
			}
		}
	}

}
