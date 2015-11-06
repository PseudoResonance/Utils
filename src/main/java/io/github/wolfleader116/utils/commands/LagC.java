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
				if (sender.hasPermission("utils.mute")) {
					sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Utils Lag Report" + ChatColor.DARK_AQUA + "---===");
					SimpleDateFormat sdfshorthh = new SimpleDateFormat("HH");
					SimpleDateFormat sdfshortmm = new SimpleDateFormat("mm");
					SimpleDateFormat sdfshortss = new SimpleDateFormat("ss");
					Long now = System.currentTimeMillis() - 79200000;
					Long difference = now - Utils.plugin.startup;
					Date datedifference = new Date(difference);
					String uptime = String.valueOf(sdfshorthh.format(datedifference)) + "Hours" + String.valueOf(sdfshortmm.format(datedifference)) + "Minutes" + String.valueOf(sdfshortss.format(datedifference)) + "Seconds";
					sender.sendMessage(ChatColor.AQUA + "Server Uptime: " + ChatColor.RED +  uptime);
					sender.sendMessage(ChatColor.AQUA + "TPS: " + ChatColor.RED + TPS.getTPS() + ChatColor.AQUA + " TPS Percentage: " +  ChatColor.RED + String.valueOf(Math.round((1.0D - TPS.getTPS() / 20.0D) * 100.0D)));
					sender.sendMessage(ChatColor.AQUA + "Maximum Memory: " + ChatColor.RED +  Runtime.getRuntime().maxMemory());
					sender.sendMessage(ChatColor.AQUA + "Free Memory: " + ChatColor.RED +  Runtime.getRuntime().freeMemory());
					sender.sendMessage(ChatColor.AQUA + "Total Memory: " + ChatColor.RED +  Runtime.getRuntime().totalMemory());
					sender.sendMessage(ChatColor.AQUA + "Available Processors: " + ChatColor.RED +  Runtime.getRuntime().availableProcessors());
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
						}
						catch (ClassCastException ex) {
							log.severe("World " + w + " seems to be corrupted!");
						}
						sender.sendMessage(ChatColor.AQUA + "Worlds:");
						sender.sendMessage(ChatColor.AQUA + w.getName() + ": Type: " + ChatColor.RED  + worldType + ChatColor.AQUA + " Loaded Chunks: " + ChatColor.RED  + w.getLoadedChunks().length + ChatColor.AQUA  + " Entities: " + ChatColor.RED  + w.getEntities().size() + ChatColor.AQUA  + " Tile Entities: " + ChatColor.RED + tileEntities);
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			} else {
				
			}
		}
		return false;
	}

}