package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.utils.SpawnMob;
import io.github.wolfleader116.utils.Utils;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnMobC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("spawnmob")) {
			if (!(sender instanceof Player)) {
				log.info("Only a player can run this command!");
			} else {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.spawnmob")) {
					if (args.length == 0) {
						WolfAPI.message("Please add a mob and an amount!", p, "Utils");
					} else if (args.length == 1) {
						WolfAPI.message("Please add an amount!", p, "Utils");
					} else if (args.length >= 2) {
						try {
							int amount = Integer.parseInt(args[1]);
							if (amount >= Utils.plugin.getConfig().getInt("MaxSpawns")) {
								WolfAPI.message("The amount of mobs is too high! Changing to config limit!", p, "Utils");
								amount = Utils.plugin.getConfig().getInt("MaxSpawns");
								Block b = p.getTargetBlock((Set<Material>) null, 100);
								Location loc = b.getLocation();
								loc.setY(loc.getY() + 1);
								SpawnMob.spawn(EntityType.valueOf(args[0].toUpperCase()), amount, loc);
								WolfAPI.message("Spawned " + args[1] + " " + WordUtils.capitalizeFully(args[0].toLowerCase()) + "s where you were looking!", p, "Utils");
							} else {
								Block b = p.getTargetBlock((Set<Material>) null, 100);
								Location loc = b.getLocation();
								loc.setY(loc.getY() + 1);
								SpawnMob.spawn(EntityType.valueOf(args[0].toUpperCase()), amount, loc);
								WolfAPI.message("Spawned " + args[1] + " " + WordUtils.capitalizeFully(args[0].toLowerCase()) + "s where you were looking!", p, "Utils");
							}
						} catch (NumberFormatException e) {
							Errors.sendError(Errors.NOT_NUMBER, p, "Utils");
						} catch (IllegalArgumentException e) {
							WolfAPI.message("The entity you entered is invalid!", p, "Utils");
						} catch (NullPointerException e) {
							WolfAPI.message("The entity you entered is invalid!", p, "Utils");
						}
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			}
		}
		return false;
	}

}
