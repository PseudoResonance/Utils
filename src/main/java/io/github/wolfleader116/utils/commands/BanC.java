package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.utils.Config;
import io.github.wolfleader116.utils.Utils;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Config c = new Config("bannedplayers", Utils.plugin);
		Config data = new Config("playerdata", Utils.plugin);
		if (cmd.getName().equalsIgnoreCase("ban")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.ban")) {
					if (args.length == 0) {
						WolfAPI.message("You need to add a player to ban!", p, "Utils");
					} else if (args.length == 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.ban.exempt")) {
								if (p.hasPermission("utils.ban.exempt.override")) {
									WolfAPI.message("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s ban exempt permissions!", p, "Utils");
									WolfAPI.message("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + "!", p, "Utils");
									c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), p.getName() + " §ahas banned you!");
									c.save();
									Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas banned you!");
								} else {
									WolfAPI.message("Can't ban " + Bukkit.getServer().getPlayer(args[0]).getName() + " because they have ban exempt permissions!", p, "Utils");
								}
							} else {
								WolfAPI.message("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + "!", p, "Utils");
								c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), p.getName() + " §ahas banned you!");
								c.save();
								Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas banned you!");
							}
						} else {
							if (data.getConfig().contains(args[0])) {
								WolfAPI.message("Banned " + args[0] + "!", p, "Utils");
								c.getConfig().set(data.getConfig().getString(args[0]), p.getName() + " §ahas banned you!");
								c.save();
							} else {
								Errors.sendError(Errors.NEVER_JOINED, p, "Utils");
							}
						}
					} else if (args.length >= 2) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.ban.exempt")) {
								if (p.hasPermission("utils.ban.exempt.override")) {
									String message = "";
									for (int i = 1; i < args.length; i++) {
										String arg = args[i] + " ";
										message = message + arg;
									}
									WolfAPI.message("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s ban exempt permissions!", p, "Utils");
									WolfAPI.message("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + ChatColor.WHITE + message + ChatColor.GREEN + "!", p, "Utils");
									c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), p.getName() + " §ahas banned you for §f" + message);
									c.save();
									Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas banned you for §f" + message);
								} else {
									WolfAPI.message("Can't ban " + Bukkit.getServer().getPlayer(args[0]).getName() + " because they have ban exempt permissions!", p, "Utils");
								}
							} else {
								String message = "";
								for (int i = 1; i < args.length; i++) {
									String arg = args[i] + " ";
									message = message + arg;
								}
								WolfAPI.message("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + ChatColor.WHITE + message + ChatColor.GREEN + "!", p, "Utils");
								c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), p.getName() + " §ahas banned you for §f" + message);
								c.save();
								Bukkit.getServer().getPlayer(args[0]).kickPlayer(p.getName() + " §ahas banned you for §f" + message);
							}
						} else {
							if (data.getConfig().contains(args[0])) {
								String message = "";
								for (int i = 1; i < args.length; i++) {
									String arg = args[i] + " ";
									message = message + arg;
								}
								WolfAPI.message("Banned " + args[0] + " for " + ChatColor.WHITE + message + ChatColor.GREEN + "!", p, "Utils");
								c.getConfig().set(data.getConfig().getString(args[0]), p.getName() + " §ahas banned you for §f" + message);
								c.save();
							} else {
								Errors.sendError(Errors.NEVER_JOINED, p, "Utils");
							}
						}
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			} else {
				if (args.length == 0) {
					log.info("You need to add a player to ban!");
				} else if (args.length == 1) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.ban.exempt")) {
							log.info("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s ban exempt permissions!");
							log.info("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + "!");
							c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), "Console §ahas banned you!");
							c.save();
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas banned you!");
						} else {
							log.info("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + "!");
							c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), "Console §ahas banned you!");
							c.save();
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas banned you!");
						}
					} else {
						if (data.getConfig().contains(args[0])) {
							log.info("Banned " + args[0] + "!");
							c.getConfig().set(data.getConfig().getString(args[0]), "Console §ahas banned you!");
							c.save();
						} else {
							log.info("That player has never joined the server!");
						}
					}
				} else if (args.length >= 2) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.ban.exempt")) {
							String message = "";
							for (int i = 1; i < args.length; i++) {
								String arg = args[i] + " ";
								message = message + arg;
							}
							log.info("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s ban exempt permissions!");
							log.info("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + message + "!");
							c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), "Console §ahas banned you for §f" + message);
							c.save();
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas banned you for §f" + message);
						} else {
							String message = "";
							for (int i = 1; i < args.length; i++) {
								String arg = args[i] + " ";
								message = message + arg;
							}
							log.info("Banned " + Bukkit.getServer().getPlayer(args[0]).getName() + " for " + message + "!");
							c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), "Console §ahas banned you for §f" + message);
							c.save();
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Console §ahas banned you for §f" + message);
						}
					} else {
						if (data.getConfig().contains(args[0])) {
							String message = "";
							for (int i = 1; i < args.length; i++) {
								String arg = args[i] + " ";
								message = message + arg;
							}
							log.info("Banned " + args[0] + " for " + message + "!");
							c.getConfig().set(data.getConfig().getString(args[0]), "Console §ahas banned you for §f" + message);
							c.save();
						} else {
							log.info("That player has never joined the server!");
						}
					}
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("unban")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.unban")) {
					if (args.length == 0) {
						WolfAPI.message("You need to add a player to unban!", p, "Utils");
					} else if (args.length == 1) {
						if (data.getConfig().contains(args[0])) {
							c.getConfig().set(data.getConfig().getString(args[0]), null);
							WolfAPI.message("Unbanned " + args[0] + "!", p, "Utils");
							c.save();
						} else {
							Errors.sendError(Errors.NEVER_JOINED, p, "Utils");
						}
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			} else {
				if (args.length == 0) {
					log.info("You need to add a player to unban!");
				} else if (args.length == 1) {
					if (data.getConfig().contains(args[0])) {
						c.getConfig().set(data.getConfig().getString(args[0]), null);
						log.info("Unbanned " + args[0] + "!");
						c.save();
					} else {
						log.info("That player has never joined the server!");
					}
				}
			}
		}
		return false;
	}

}