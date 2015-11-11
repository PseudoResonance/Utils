package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;
import net.md_5.bungee.api.ChatColor;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WeatherC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("weather")) {
			if (!(sender instanceof Player)) {
				if (args.length == 0) {
					log.info("Please enter a world!");
				} else if (args.length == 1) {
					if (Bukkit.getServer().getWorld(args[0]) != null) {
						if (Bukkit.getServer().getWorld(args[0]).isThundering()) {
							log.info("The current weather in world " + Bukkit.getServer().getWorld(args[0]).getName() + " is thunder");
						} else if (Bukkit.getServer().getWorld(args[0]).hasStorm()) {
							log.info("The current weather in world " + Bukkit.getServer().getWorld(args[0]).getName() + " is rain");
						} else {
							log.info("The current weather in world " + Bukkit.getServer().getWorld(args[0]).getName() + " is clear");
						}
					} else {
						log.info("That is an invalid world!");
					}
				} else if (args.length == 2) {
					if (Bukkit.getServer().getWorld(args[0]) != null) {
						if ((args[1].equalsIgnoreCase("clear")) || (args[1].equalsIgnoreCase("sun"))) {
							Bukkit.getServer().getWorld(args[0]).setStorm(false);
							Bukkit.getServer().getWorld(args[0]).setThundering(false);
							log.info("You have set the weather to clear in world " + args[0]);
						} else if (args[1].equalsIgnoreCase("rain")) {
							Bukkit.getServer().getWorld(args[0]).setStorm(true);
							Bukkit.getServer().getWorld(args[0]).setThundering(false);
							log.info("You have set the weather to rain in world " + args[0]);
						} else if ((args[1].equalsIgnoreCase("thunder")) || (args[1].equalsIgnoreCase("storm"))) {
							Bukkit.getServer().getWorld(args[0]).setStorm(true);
							Bukkit.getServer().getWorld(args[0]).setThundering(true);
							log.info("You have set the weather to thunder in world " + args[0]);
						} else {
							log.info(args[0] + " is an invalid weather! Possible values are clear, rain or thunder.");
						}
					} else {
						log.info("That is an invalid world!");
					}
				} else if (args.length >= 3) {
					if (Bukkit.getServer().getWorld(args[0]) != null) {
						if ((args[1].equalsIgnoreCase("clear")) || (args[1].equalsIgnoreCase("sun"))) {
							if (isNumber(args[2])) {
								Bukkit.getServer().getWorld(args[0]).setStorm(false);
								Bukkit.getServer().getWorld(args[0]).setThundering(false);
								Bukkit.getServer().getWorld(args[0]).setWeatherDuration(Integer.valueOf(args[2]));
								Bukkit.getServer().getWorld(args[0]).setThunderDuration(0);
								log.info("You have set the weather to clear in world " + args[0] + " for " + args[2]);
							} else {
								log.info("Please enter a number!");
							}
						} else if (args[1].equalsIgnoreCase("rain")) {
							if (isNumber(args[2])) {
								Bukkit.getServer().getWorld(args[0]).setStorm(true);
								Bukkit.getServer().getWorld(args[0]).setThundering(false);
								Bukkit.getServer().getWorld(args[0]).setWeatherDuration(Integer.valueOf(args[2]));
								Bukkit.getServer().getWorld(args[0]).setThunderDuration(0);
								log.info("You have set the weather to rain in world " + args[0] + " for " + args[2]);
							} else {
								log.info("Please enter a number!");
							}
						} else if ((args[1].equalsIgnoreCase("thunder")) || (args[1].equalsIgnoreCase("storm"))) {
							if (isNumber(args[2])) {
								Bukkit.getServer().getWorld(args[0]).setStorm(true);
								Bukkit.getServer().getWorld(args[0]).setThundering(true);
								Bukkit.getServer().getWorld(args[0]).setWeatherDuration(Integer.valueOf(args[2]));
								Bukkit.getServer().getWorld(args[0]).setThunderDuration(Integer.valueOf(args[2]));
								log.info("You have set the weather to thunder in world " + args[0] + " for " + args[2]);
							} else {
								log.info("Please enter a number!");
							}
						} else {
							log.info(args[0] + " is an invalid weather! Possible values are clear, rain or thunder.");
						}
					} else {
						log.info("That is an invalid world!");
					}
				}
			} else {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.weather")) {
					if (args.length == 0) {
						if (p.getWorld().isThundering()) {
							WolfAPI.message("The current weather in your world is " + ChatColor.RED + "thunder", p, "Utils");
						} else if (p.getWorld().hasStorm()) {
							WolfAPI.message("The current weather in your world is " + ChatColor.RED + "rain", p, "Utils");
						} else {
							WolfAPI.message("The current weather in your world is " + ChatColor.RED + "clear", p, "Utils");
						}
					} else if (args.length == 1) {
						if ((args[0].equalsIgnoreCase("clear")) || (args[0].equalsIgnoreCase("sun"))) {
							p.getWorld().setStorm(false);
							p.getWorld().setThundering(false);
							WolfAPI.message("Set weather in your world to clear", p, "Utils");
						} else if (args[0].equalsIgnoreCase("rain")) {
							p.getWorld().setStorm(true);
							p.getWorld().setThundering(false);
							WolfAPI.message("Set weather in your world to rain", p, "Utils");
						} else if ((args[0].equalsIgnoreCase("thunder")) || (args[0].equalsIgnoreCase("storm"))) {
							p.getWorld().setStorm(true);
							p.getWorld().setThundering(true);
							WolfAPI.message("Set weather in your world to thunder", p, "Utils");
						} else {
							if (Bukkit.getServer().getWorld(args[0]) != null) {
								if (Bukkit.getServer().getWorld(args[0]).isThundering()) {
									WolfAPI.message("The current weather in world " + Bukkit.getServer().getWorld(args[0]).getName() + " is " + ChatColor.RED + "thunder", p, "Utils");
								} else if (Bukkit.getServer().getWorld(args[0]).hasStorm()) {
									WolfAPI.message("The current weather in world " + Bukkit.getServer().getWorld(args[0]).getName() + " is " + ChatColor.RED + "rain", p, "Utils");
								} else {
									WolfAPI.message("The current weather in world " + Bukkit.getServer().getWorld(args[0]).getName() + " is " + ChatColor.RED + "clear", p, "Utils");
								}
							} else {
								Errors.sendError(Errors.CUSTOM, p, "Utils", args[0] + " is an invalid world!");
							}
						}
					} else if (args.length == 2) {
						if ((args[0].equalsIgnoreCase("clear")) || (args[0].equalsIgnoreCase("sun"))) {
							if (isNumber(args[1])) {
								p.getWorld().setStorm(false);
								p.getWorld().setThundering(false);
								p.getWorld().setWeatherDuration(Integer.valueOf(args[1]));
								p.getWorld().setThunderDuration(0);
								WolfAPI.message("Set weather in your world to clear for " + args[1], p, "Utils");
							} else {
								if (Bukkit.getServer().getWorld(args[1]) != null) {
									Bukkit.getServer().getWorld(args[1]).setStorm(false);
									Bukkit.getServer().getWorld(args[1]).setThundering(false);
									WolfAPI.message("Set weather in world " + args[1] + " to clear", p, "Utils");
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Utils", args[1] + " is an invalid world!");
								}
							}
						} else if (args[0].equalsIgnoreCase("rain")) {
							if (isNumber(args[1])) {
								p.getWorld().setStorm(true);
								p.getWorld().setThundering(false);
								p.getWorld().setWeatherDuration(Integer.valueOf(args[1]));
								p.getWorld().setThunderDuration(0);
								WolfAPI.message("Set weather in your world to rain for " + args[1], p, "Utils");
							} else {
								if (Bukkit.getServer().getWorld(args[1]) != null) {
									Bukkit.getServer().getWorld(args[1]).setStorm(true);
									Bukkit.getServer().getWorld(args[1]).setThundering(false);
									WolfAPI.message("Set weather in world " + args[1] + " to rain", p, "Utils");
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Utils", args[1] + " is an invalid world!");
								}
							}
						} else if ((args[0].equalsIgnoreCase("thunder")) || (args[0].equalsIgnoreCase("storm"))) {
							if (isNumber(args[1])) {
								p.getWorld().setStorm(true);
								p.getWorld().setThundering(true);
								p.getWorld().setWeatherDuration(Integer.valueOf(args[1]));
								p.getWorld().setThunderDuration(Integer.valueOf(args[1]));
								WolfAPI.message("Set weather in your world to thunder for " + args[1], p, "Utils");
							} else {
								if (Bukkit.getServer().getWorld(args[1]) != null) {
									Bukkit.getServer().getWorld(args[1]).setStorm(true);
									Bukkit.getServer().getWorld(args[1]).setThundering(true);
									WolfAPI.message("Set weather in world " + args[1] + " to thunder", p, "Utils");
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Utils", args[1] + " is an invalid world!");
								}
							}
						} else {
							Errors.sendError(Errors.CUSTOM, p, "Utils", args[0] + " is an invalid weather! Possible values are clear, rain or thunder.");
						}
					} else if (args.length >= 3) {
						if ((args[0].equalsIgnoreCase("clear")) || (args[0].equalsIgnoreCase("sun"))) {
							if (isNumber(args[1])) {
								if (Bukkit.getServer().getWorld(args[2]) != null) {
									Bukkit.getServer().getWorld(args[2]).setStorm(false);
									Bukkit.getServer().getWorld(args[2]).setThundering(false);
									Bukkit.getServer().getWorld(args[2]).setWeatherDuration(Integer.valueOf(args[1]));
									Bukkit.getServer().getWorld(args[2]).setThunderDuration(0);
									WolfAPI.message("Set weather in world " + args[2] + " to clear for " + args[1], p, "Utils");
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Utils", args[1] + " is an invalid world!");
								}
							} else {
								Errors.sendError(Errors.NOT_NUMBER, p, "Utils");
							}
						} else if (args[0].equalsIgnoreCase("rain")) {
							if (isNumber(args[1])) {
								if (Bukkit.getServer().getWorld(args[2]) != null) {
									Bukkit.getServer().getWorld(args[2]).setStorm(true);
									Bukkit.getServer().getWorld(args[2]).setThundering(false);
									Bukkit.getServer().getWorld(args[2]).setWeatherDuration(Integer.valueOf(args[1]));
									Bukkit.getServer().getWorld(args[2]).setThunderDuration(0);
									WolfAPI.message("Set weather in world " + args[2] + " to rain for " + args[1], p, "Utils");
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Utils", args[1] + " is an invalid world!");
								}
							} else {
								Errors.sendError(Errors.NOT_NUMBER, p, "Utils");
							}
						} else if ((args[0].equalsIgnoreCase("thunder")) || (args[0].equalsIgnoreCase("storm"))) {
							if (isNumber(args[1])) {
								if (Bukkit.getServer().getWorld(args[2]) != null) {
									Bukkit.getServer().getWorld(args[2]).setStorm(true);
									Bukkit.getServer().getWorld(args[2]).setThundering(true);
									Bukkit.getServer().getWorld(args[2]).setWeatherDuration(Integer.valueOf(args[1]));
									Bukkit.getServer().getWorld(args[2]).setThunderDuration(Integer.valueOf(args[1]));
									WolfAPI.message("Set weather in world " + args[2] + " to thunder for " + args[1], p, "Utils");
								} else {
									Errors.sendError(Errors.CUSTOM, p, "Utils", args[1] + " is an invalid world!");
								}
							} else {
								Errors.sendError(Errors.NOT_NUMBER, p, "Utils");
							}
						} else {
							Errors.sendError(Errors.CUSTOM, p, "Utils", args[0] + " is an invalid weather! Possible values are clear, rain or thunder.");
						}
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			}
		}
		return false;
	}
	
	public static boolean isNumber(String string) {
		try {
			@SuppressWarnings("unused")
			double test = Double.parseDouble(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}
