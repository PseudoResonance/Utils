package io.github.wolfleader116.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import io.github.wolfleader116.utils.commands.BanC;
import io.github.wolfleader116.utils.commands.KickC;
import io.github.wolfleader116.utils.commands.LagC;
import io.github.wolfleader116.utils.commands.MuteC;
import io.github.wolfleader116.utils.commands.SpawnMobC;
import io.github.wolfleader116.utils.commands.UtilsC;
import io.github.wolfleader116.wolfapi.WolfAPI;
import io.github.wolfleader116.settings.Settings;
import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.material.Dispenser;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
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

import com.xxmicloxx.noteblockapi.NBSDecoder;
import com.xxmicloxx.noteblockapi.RadioSongPlayer;
import com.xxmicloxx.noteblockapi.Song;
import com.xxmicloxx.noteblockapi.SongPlayer;

public class Utils extends JavaPlugin implements Listener {

	private static final Logger log = Logger.getLogger("Minecraft");
	
	public static Utils plugin;

	public static int songnumber = -1;
	
	public final long startup = System.currentTimeMillis() - 10800000;

	public boolean barenabled = true;
	public boolean titleenabled = true;
	public boolean settingsenabled = true;
	
	private static SongPlayer songplayer;
	
	public void onEnable() {
		plugin = this;
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 100L, 1L);
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
		getCommand("lag").setExecutor(new LagC());
		getServer().getPluginManager().registerEvents(this, this);
		
		if (Bukkit.getPluginManager().getPlugin("NoteBlockAPI") == null) {
			log.severe("NoteBlockAPI plugin not found. Disabling Music!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		if (Bukkit.getPluginManager().getPlugin("BarAPI") == null) {
			log.warning("BarAPI plugin not found. Some features will not be available in the Music plugin!");
			barenabled = false;
		}
		if (Bukkit.getPluginManager().getPlugin("TitleManager") == null) {
			log.warning("TitleManager plugin not found. Some features will not be available in the Music plugin!");
			titleenabled = false;
		}
		if (Bukkit.getPluginManager().getPlugin("Settings") == null) {
			log.warning("Settings plugin not found. Some features will not be available in the Music plugin!");
			settingsenabled = false;
		}
		if (this.getConfig().getBoolean("EnableMusic")) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					firstLoop();
				}
			}, 200);
		}
	}
	
	public void onDisable() {
		this.getConfig().set("LastSong", songnumber);
		this.saveConfig();
		plugin = null;
	}
	
	public static boolean isMusic() {
		if (Utils.plugin.getConfig().getBoolean("EnableMusic")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void endLoop(final SongPlayer sp) {
		if (sp.isPlaying()) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					endLoop(sp);
				}
			}, 5);
		} else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					startLoop();
				}
			}, 100);
		}
	}

	public void firstLoop() {
		songnumber = this.getConfig().getInt("LastSong");
		try {
			songnumber++;
			String songname = name();
			Song s = NBSDecoder.parse(new File(getDataFolder() + "/songs", songname + ".nbs"));
			SongPlayer sp = new RadioSongPlayer(s);
			sp.setAutoDestroy(true);
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				Config settings = new Config("../Settings/playerdata", Utils.plugin);
				if (settings.getConfig().getBoolean("music." + online.getUniqueId().toString()) || settings.getConfig().getString("music." + online.getUniqueId().toString()) == null) {
					sp.addPlayer(online);
					if (barenabled) {
						BarAPI.setMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"", 10);
					}
					if (titleenabled) {
						sendActionbarMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"");
					}
				}
			}
			Settings.setCurrentSong(songname);
			sp.setPlaying(true);
			endLoop(sp);
			songplayer = sp;
		} catch (NullPointerException e) {
			songnumber = 0;
			String songname = name();
			Song s = NBSDecoder.parse(new File(getDataFolder() + "/songs", songname + ".nbs"));
			SongPlayer sp = new RadioSongPlayer(s);
			sp.setAutoDestroy(true);
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				Config settings = new Config("../Settings/playerdata", Utils.plugin);
				if (settings.getConfig().getBoolean("music." + online.getUniqueId().toString()) || settings.getConfig().getString("music." + online.getUniqueId().toString()) == null) {
					sp.addPlayer(online);
					if (barenabled) {
						BarAPI.setMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"", 10);
					}
					if (titleenabled) {
						sendActionbarMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"");
					}
				}
			}
			Settings.setCurrentSong(songname);
			sp.setPlaying(true);
			endLoop(sp);
			songplayer = sp;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startLoop() {
		try {
			songnumber++;
			String songname = name();
			Song s = NBSDecoder.parse(new File(getDataFolder() + "/songs", songname + ".nbs"));
			SongPlayer sp = new RadioSongPlayer(s);
			sp.setAutoDestroy(true);
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				Config settings = new Config("../Settings/playerdata", Utils.plugin);
				if (settings.getConfig().getBoolean("music." + online.getUniqueId().toString()) || settings.getConfig().getString("music." + online.getUniqueId().toString()) == null) {
					sp.addPlayer(online);
					if (barenabled) {
						BarAPI.setMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"", 10);
					}
					if (titleenabled) {
						sendActionbarMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"");
					}
				}
			}
			Settings.setCurrentSong(songname);
			sp.setPlaying(true);
			endLoop(sp);
			songplayer = sp;
		} catch (NullPointerException e) {
			songnumber = 0;
			String songname = name();
			Song s = NBSDecoder.parse(new File(getDataFolder() + "/songs", songname + ".nbs"));
			SongPlayer sp = new RadioSongPlayer(s);
			sp.setAutoDestroy(true);
			for (Player online : Bukkit.getServer().getOnlinePlayers()) {
				Config settings = new Config("../Settings/playerdata", Utils.plugin);
				if (settings.getConfig().getBoolean("music." + online.getUniqueId().toString()) || settings.getConfig().getString("music." + online.getUniqueId().toString()) == null) {
					sp.addPlayer(online);
					if (barenabled) {
						BarAPI.setMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"", 10);
					}
					if (titleenabled) {
						sendActionbarMessage(online, "§a§lNow Playing §6\"§c§l" + songname + "§6\"");
					}
				}
			}
			Settings.setCurrentSong(songname);
			sp.setPlaying(true);
			endLoop(sp);
			songplayer = sp;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendActionbarMessage(Player player, String message) {
		new ActionbarTitleObject(message).send(player);
	}

	public static String name() {
		if (songnumber == -1) {
			File f = new File(Utils.plugin.getDataFolder() + "/songs");
			List<String> songlist = new ArrayList<String>(Arrays.asList(f.list()));
			String[] songs = songlist.toArray(new String[0]);
			String song = songs[0].replace(".nbs", "");
			return song;
		} else {
			File f = new File(Utils.plugin.getDataFolder() + "/songs");
			List<String> songlist = new ArrayList<String>(Arrays.asList(f.list()));
			String[] songs = songlist.toArray(new String[0]);
			String song = songs[songnumber].replace(".nbs", "");
			return song;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(AsyncPlayerPreLoginEvent e) {
		final String pname = e.getName();
		Config c = new Config("bannedplayers", Utils.plugin);
		Config data = new Config("playerdata", Utils.plugin);
		data.getConfig().set(pname, e.getUniqueId().toString());
		data.save();
		if (c.getConfig().contains(e.getUniqueId().toString())) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, c.getConfig().getString(e.getUniqueId().toString()));
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				songplayer.addPlayer(Bukkit.getServer().getPlayer(pname));
			}
		}, 20);
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
