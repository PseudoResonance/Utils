package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName() == "kick") {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.kick")) {
					if (args.length == 0) {
						WolfAPI.message("You need to add a player to kick!", p, "Utils");
					} else if (args.length == 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.kick.exempt")) {
								if (p.hasPermission("utils.kick.exempt.override")) {
									WolfAPI.message("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s kick exempt permissions!", p, "Utils");
									WolfAPI.message("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + "!", p, "Utils");
									Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas kicked you!");
								} else {
									WolfAPI.message("Can't kick " + Bukkit.getServer().getPlayer(args[0]).getName() + " because they have kick exempt permissions!", p, "Utils");
								}
							} else {
								WolfAPI.message("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + "!", p, "Utils");
								Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas kicked you!");
							}
						} else {
							Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
						}
					} else if (args.length >= 2) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.kick.exempt")) {
								if (p.hasPermission("utils.kick.exempt.override")) {
									String message = "";
									for (int i = 1; i < args.length; i++) {
										String arg = args[i] + " ";
										message = message + arg;
									}
									WolfAPI.message("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s kick exempt permissions!", p, "Utils");
									WolfAPI.message("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + ChatColor.WHITE + message + ChatColor.GREEN + "!", p, "Utils");
									Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas kicked you for §f" + message);
								} else {
									WolfAPI.message("Can't kick " + Bukkit.getServer().getPlayer(args[0]).getName() + " because they have kick exempt permissions!", p, "Utils");
								}
							} else {
								String message = "";
								for (int i = 1; i < args.length; i++) {
									String arg = args[i] + " ";
									message = message + arg;
								}
								WolfAPI.message("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + ChatColor.WHITE + message + ChatColor.GREEN + "!", p, "Utils");
								Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas kicked you for §f" + message);
							}
						} else {
							Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
						}
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			} else {
				if (args.length == 0) {
					log.info("You need to add a player to kick!");
				} else if (args.length == 1) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.kick.exempt")) {
							log.info("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s kick exempt permissions!");
							log.info("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + "!");
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas kicked you!");
						} else {
							log.info("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + "!");
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas kicked you!");
						}
					} else {
						log.info("That player is not online!");
					}
				} else if (args.length >= 2) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.kick.exempt")) {
							String message = "";
							for (int i = 1; i < args.length; i++) {
								String arg = args[i] + " ";
								message = message + arg;
							}
							log.info("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s kick exempt permissions!");
							log.info("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + message + "!");
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas kicked you for §f" + message);
						} else {
							String message = "";
							for (int i = 1; i < args.length; i++) {
								String arg = args[i] + " ";
								message = message + arg;
							}
							log.info("Kicked " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + message + "!");
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas kicked you for §f" + message);
						}
					} else {
						log.info("That player is not online!");
					}
				}
			}
		}
		return false;
	}

}
