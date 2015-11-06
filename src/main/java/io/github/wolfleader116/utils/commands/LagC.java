package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.utils.TPS;
import io.github.wolfleader116.utils.Utils;
import io.github.wolfleader116.wolfapi.Errors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LagC implements CommandExecutor {
	
	private static final Logger log = Logger.getLogger("Minecraft");
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("lag")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.lag")) {
					sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Utils Lag Report" + ChatColor.DARK_AQUA + "---===");
					SimpleDateFormat sdfshorthh = new SimpleDateFormat("HH");
					SimpleDateFormat sdfshortmm = new SimpleDateFormat("mm");
					SimpleDateFormat sdfshortss = new SimpleDateFormat("ss");
					Long now = System.currentTimeMillis() - 79200000;
					Long difference = now - Utils.plugin.startup;
					Date datedifference = new Date(difference);
					String uptime = String.valueOf(sdfshorthh.format(datedifference)) + " Hours " + String.valueOf(sdfshortmm.format(datedifference)) + " Minutes " + String.valueOf(sdfshortss.format(datedifference)) + " Seconds";
					sender.sendMessage(ChatColor.AQUA + "Server Uptime: " + ChatColor.RED + uptime);
					sender.sendMessage(ChatColor.AQUA + "TPS: " + ChatColor.RED + TPS.getTPS());
					sender.sendMessage(ChatColor.AQUA + "Maximum Memory: " + ChatColor.RED + bytes(Runtime.getRuntime().maxMemory(), true));
					sender.sendMessage(ChatColor.AQUA + "Free Memory: " + ChatColor.RED + bytes(Runtime.getRuntime().freeMemory(), true));
					sender.sendMessage(ChatColor.AQUA + "Total Memory: " + ChatColor.RED + bytes(Runtime.getRuntime().totalMemory(), true));
					sender.sendMessage(ChatColor.AQUA + "Available Cores: " + ChatColor.RED + Runtime.getRuntime().availableProcessors());
					sender.sendMessage(ChatColor.AQUA + "Worlds:");
					List<World> worlds = Bukkit.getServer().getWorlds();
					for (World w : worlds) {
						String worldType = "World";
						switch (w.getEnvironment()) {
						case NETHER:
							worldType = "Nether";
							break;
						case THE_END:
							worldType = "The End";
							break;
						case NORMAL:
							worldType = "Overworld";
							break;
						default:
							worldType = "Unknown";
							break;
						}
						int tileEntities = 0;
						try {
							for (Chunk chunk : w.getLoadedChunks()) {
								tileEntities += chunk.getTileEntities().length;
							}
						} catch (ClassCastException ex) {
							log.severe("World " + w + " seems to be corrupted!");
						}
						sender.sendMessage(ChatColor.AQUA + w.getName() + ": Type: " + ChatColor.RED + worldType + ChatColor.AQUA + " Loaded Chunks: " + ChatColor.RED + w.getLoadedChunks().length + ChatColor.AQUA + " Entities: " + ChatColor.RED + w.getEntities().size() + ChatColor.AQUA + " Tile Entities: " + ChatColor.RED + tileEntities);
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			} else {
				SimpleDateFormat sdfshorthh = new SimpleDateFormat("HH");
				SimpleDateFormat sdfshortmm = new SimpleDateFormat("mm");
				SimpleDateFormat sdfshortss = new SimpleDateFormat("ss");
				Long now = System.currentTimeMillis() - 79200000;
				Long difference = now - Utils.plugin.startup;
				Date datedifference = new Date(difference);
				String uptime = String.valueOf(sdfshorthh.format(datedifference)) + " Hours " + String.valueOf(sdfshortmm.format(datedifference)) + " Minutes " + String.valueOf(sdfshortss.format(datedifference)) + " Seconds";
				List<World> worlds = Bukkit.getServer().getWorlds();
				for (World w : worlds) {
					String worldType = "World";
					switch (w.getEnvironment()) {
					case NETHER:
						worldType = "Nether";
						break;
					case THE_END:
						worldType = "The End";
						break;
					case NORMAL:
						worldType = "Overworld";
						break;
					default:
						worldType = "Unknown";
						break;
					}
					int tileEntities = 0;
					try {
						for (Chunk chunk : w.getLoadedChunks()) {
							tileEntities += chunk.getTileEntities().length;
						}
					} catch (ClassCastException ex) {
						log.severe("World " + w + " seems to be corrupted!");
					}
					log.info(w.getName() + ": Type: " + worldType + " Loaded Chunks: " + w.getLoadedChunks().length + " Entities: " + w.getEntities().size() + " Tile Entities: " + tileEntities);
				}
				log.info("Worlds:");
				log.info("Available Cores: " + Runtime.getRuntime().availableProcessors());
				log.info("Total Memory: " + bytes(Runtime.getRuntime().totalMemory(), true));
				log.info("Free Memory: " + bytes(Runtime.getRuntime().freeMemory(), true));
				log.info("Maximum Memory: " + bytes(Runtime.getRuntime().maxMemory(), true));
				log.info("TPS: " + TPS.getTPS());
				log.info("Server Uptime: " + uptime);
				log.info("===---Utils Lag Report---===");
			}
		}
		return false;
	}
	
	public static String bytes(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
}