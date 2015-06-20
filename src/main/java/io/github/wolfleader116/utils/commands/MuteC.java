package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.utils.Config;
import io.github.wolfleader116.utils.Utils;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Config c = new Config("mutedplayers", Utils.plugin);
		Config data = new Config("playerdata", Utils.plugin);
		if (cmd.getName().equalsIgnoreCase("mute")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.mute")) {
					if (args.length == 0) {
						WolfAPI.message("You need to add a player to mute!", p, "Utils");
					} else if (args.length >= 1) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.mute.exempt")) {
								if (p.hasPermission("utils.mute.exempt.override")) {
									WolfAPI.message("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s mute exempt permissions!", p, "Utils");
									WolfAPI.message("Muted " + Bukkit.getServer().getPlayer(args[0]).getName() + "!", p, "Utils");
									c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), p.getName() + " has muted you!");
									c.save();
									WolfAPI.message(p.getName() + " has muted you!", Bukkit.getServer().getPlayer(args[0]), "Utils");
								} else {
									WolfAPI.message("Can't mute " + Bukkit.getServer().getPlayer(args[0]).getName() + " because they have mute exempt permissions!", p, "Utils");
								}
							} else {
								WolfAPI.message("Muted " + Bukkit.getServer().getPlayer(args[0]).getName() + "!", p, "Utils");
								c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), p.getName() + " has muted you!");
								c.save();
								WolfAPI.message(p.getName() + " has muted you!", Bukkit.getServer().getPlayer(args[0]), "Utils");
							}
						} else {
							if (data.getConfig().contains(args[0])) {
								WolfAPI.message("Muted " + args[0] + "!", p, "Utils");
								c.getConfig().set(data.getConfig().getString(args[0]), true);
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
					log.info("You need to add a player to mute!");
				} else if (args.length == 1) {
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						if (Bukkit.getServer().getPlayer(args[0]).hasPermission("utils.mute.exempt")) {
							log.info("Overriding " + Bukkit.getServer().getPlayer(args[0]).getName() + "'s mute exempt permissions!");
							log.info("Muted " + Bukkit.getServer().getPlayer(args[0]).getName() + "!");
							c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), "Console has muted you!");
							c.save();
							WolfAPI.message("Console has muted you!", Bukkit.getServer().getPlayer(args[0]), "Utils");
						} else {
							log.info("Muted " + Bukkit.getServer().getPlayer(args[0]).getName() + "!");
							c.getConfig().set(Bukkit.getServer().getPlayer(args[0]).getUniqueId().toString(), "Console has muted you!");
							c.save();
							WolfAPI.message("Console has muted you!", Bukkit.getServer().getPlayer(args[0]), "Utils");
						}
					} else {
						if (data.getConfig().contains(args[0])) {
							log.info("Muted " + args[0] + "!");
							c.getConfig().set(data.getConfig().getString(args[0]), "Console has muted you!");
							c.save();
						} else {
							log.info("That player has never joined the server!");
						}
					}
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("unmute")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.unmute")) {
					if (args.length == 0) {
						WolfAPI.message("You need to add a player to unmute!", p, "Utils");
					} else if (args.length == 1) {
						if (data.getConfig().contains(args[0])) {
							c.getConfig().set(data.getConfig().getString(args[0]), null);
							WolfAPI.message("Unmuted " + args[0] + "!", p, "Utils");
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
					log.info("You need to add a player to unmute!");
				} else if (args.length == 1) {
					if (data.getConfig().contains(args[0])) {
						c.getConfig().set(data.getConfig().getString(args[0]), null);
						log.info("Unmuted " + args[0] + "!");
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