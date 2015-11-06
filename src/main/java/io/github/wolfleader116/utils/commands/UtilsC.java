package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.utils.Utils;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilsC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		File configFile = new File(Utils.plugin.getDataFolder(), "config.yml");
		if (cmd.getName().equalsIgnoreCase("utils")) {
			if (!(sender instanceof Player)) {
				if (args.length == 0) {
					log.info("Use /utils help for a list of commands.");
					log.info("Utils plugin created by WolfLeader116");
					log.info("===---Utils Info---===");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("help")) {
						log.info("/utils reload Reloads the config.");
						log.info("/utils reset Resets the config.");
						log.info("/utils help Shows this message.");
						log.info("/utils Shows the info page.");
						log.info("===---Utils Help---===");
					} else if (args[0].equalsIgnoreCase("reset")) {
						configFile.delete();
						Utils.plugin.saveDefaultConfig();
						log.info("Reset the config!");
					} else if (args[0].equalsIgnoreCase("reload")) {
						Utils.plugin.reloadConfig();
						log.info("Reloaded the config!");
					} else {
						log.info(args[0] + " is not a possible subcommand. Use /utils help for more.");
					}
				}
			} else {
				Player p = (Player) sender;
				if (args.length == 0) {
					sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Utils Info" + ChatColor.DARK_AQUA + "---===");
					sender.sendMessage(ChatColor.AQUA + "Utils plugin created by WolfLeader116");
					sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.RED + "/utils help " + ChatColor.AQUA + "for a list of commands.");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Utils Help" + ChatColor.DARK_AQUA + "---===");
						sender.sendMessage(ChatColor.RED + "/utils " + ChatColor.AQUA + "Shows the info page.");
						sender.sendMessage(ChatColor.RED + "/utils help " + ChatColor.AQUA + "Shows this message.");
						if (sender.hasPermission("utils.reset")) {
							sender.sendMessage(ChatColor.RED + "/utils reset " + ChatColor.AQUA + "Resets the config.");
						}
						if (sender.hasPermission("utils.reload")) {
							sender.sendMessage(ChatColor.RED + "/utils reload " + ChatColor.AQUA + "Reloads the config.");
						}
					} else if (args[0].equalsIgnoreCase("reset")) {
						if (sender.hasPermission("utils.reset")) {
							configFile.delete();
							Utils.plugin.saveDefaultConfig();
							WolfAPI.message("Reset the config!", p, "Utils");
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
						}
					} else if (args[0].equalsIgnoreCase("reload")) {
						if (sender.hasPermission("utils.reload")) {
							Utils.plugin.reloadConfig();
							WolfAPI.message("Reloaded the config!", p, "Utils");
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
						}
					} else {
						Errors.sendError(Errors.CUSTOM, p, "Utils", args[0] + " is not a possible subcommand. Use /utils help for more.");
					}
				}
			}
		}
		return false;
	}

}
