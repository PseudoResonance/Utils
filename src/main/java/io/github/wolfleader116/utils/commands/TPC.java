package io.github.wolfleader116.utils.commands;

import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("tp")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (sender.hasPermission("utils.tp")) {
					if (args.length == 0) {
						WolfAPI.message("You need to add a location or player to tp to!", p, "Utils");
					} else if (args.length == 1) {
						if (isNumber(args[1])) {
							WolfAPI.message("You need to add a location to tp to!", p, "Utils");
						} else {
							if (Bukkit.getServer().getPlayer(args[0]) != null) {
								p.teleport(Bukkit.getServer().getPlayer(args[0]));
								WolfAPI.message("Teleported to " + Bukkit.getServer().getPlayer(args[0]).getName(), p, "Utils");
							} else {
								Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
							}
						}
					} else if (args.length == 2) {
						if (isNumber(args[1])) {
							WolfAPI.message("You need to add a location to tp to!", p, "Utils");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName(), p, "Utils");
							} else {
								Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
							}
						}
					} else if (args.length == 3) {
						if (isNumber(args[0])) {
							Location loc = new Location(p.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]));
							p.teleport(loc);
							WolfAPI.message("Teleported to X: " + ChatColor.RED + Double.valueOf(args[0]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[2]), p, "Utils");
						} else {
							if (isNumber(args[1])) {
								if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
									Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName(), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							} else {
								if (Bukkit.getServer().getPlayer(args[0]) != null) {
									WolfAPI.message("You need to add a location to tp to!", p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							}
						}
					} else if (args.length == 4) {
						if (isNumber(args[0])) {
							Location loc = new Location(p.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Float.valueOf(args[3]), 0);
							p.teleport(loc);
							WolfAPI.message("Teleported to X: " + ChatColor.RED + Double.valueOf(args[0]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[3]), p, "Utils");
						} else {
							if (isNumber(args[1])) {
								if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
									Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName(), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							} else {
								if (Bukkit.getServer().getPlayer(args[0]) != null) {
									Location loc = new Location(p.getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
									Bukkit.getServer().getPlayer(args[0]).teleport(loc);
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[3]), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							}
						}
					} else if (args.length == 5) {
						if (isNumber(args[0])) {
							Location loc = new Location(p.getWorld(), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Float.valueOf(args[3]), Float.valueOf(args[4]));
							p.teleport(loc);
							WolfAPI.message("Teleported to X: " + ChatColor.RED + Double.valueOf(args[0]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[3]) + ChatColor.GREEN + " X-Rotation: " + ChatColor.RED + Double.valueOf(args[4]), p, "Utils");
						} else {
							if (isNumber(args[1])) {
								if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
									Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName(), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							} else {
								if (Bukkit.getServer().getPlayer(args[0]) != null) {
									Location loc = new Location(p.getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), 0);
									Bukkit.getServer().getPlayer(args[0]).teleport(loc);
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[3]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[4]), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							}
						}
					} else if (args.length == 6) {
						if (isNumber(args[0])) {
							Location loc = new Location(Bukkit.getServer().getWorld(args[5]), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Float.valueOf(args[3]), Float.valueOf(args[4]));
							p.teleport(loc);
							WolfAPI.message("Teleported to X: " + ChatColor.RED + Double.valueOf(args[0]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[3]) + ChatColor.GREEN + " X-Rotation: " + ChatColor.RED + Double.valueOf(args[4]) + ChatColor.GREEN + " In World: " + ChatColor.RED + args[5], p, "Utils");
						} else {
							if (isNumber(args[1])) {
								if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
									Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName(), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							} else {
								if (Bukkit.getServer().getPlayer(args[0]) != null) {
									Location loc = new Location(p.getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5]));
									Bukkit.getServer().getPlayer(args[0]).teleport(loc);
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[3]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[4]) + ChatColor.GREEN + " X-Rotation: " + ChatColor.RED + Double.valueOf(args[5]), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							}
						}
					} else if (args.length >= 7) {
						if (isNumber(args[0])) {
							Location loc = new Location(Bukkit.getServer().getWorld(args[5]), Double.valueOf(args[0]), Double.valueOf(args[1]), Double.valueOf(args[2]), Float.valueOf(args[3]), Float.valueOf(args[4]));
							p.teleport(loc);
							WolfAPI.message("Teleported to X: " + ChatColor.RED + Double.valueOf(args[0]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[3]) + ChatColor.GREEN + " X-Rotation: " + ChatColor.RED + Double.valueOf(args[4]) + ChatColor.GREEN + " In World: " + ChatColor.RED + args[5], p, "Utils");
						} else {
							if (isNumber(args[1])) {
								if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
									Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName(), p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							} else {
								if (Bukkit.getServer().getPlayer(args[0]) != null) {
									Location loc = new Location(Bukkit.getServer().getWorld(args[6]), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5]));
									Bukkit.getServer().getPlayer(args[0]).teleport(loc);
									WolfAPI.message("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + ChatColor.RED + Double.valueOf(args[1]) + ChatColor.GREEN + " Y: " + ChatColor.RED + Double.valueOf(args[2]) + ChatColor.GREEN + " Z: " + ChatColor.RED + Double.valueOf(args[3]) + ChatColor.GREEN + " Y-Rotation: " + ChatColor.RED + Double.valueOf(args[4]) + ChatColor.GREEN + " X-Rotation: " + ChatColor.RED + Double.valueOf(args[5]) + ChatColor.GREEN + " In World: " + ChatColor.RED + args[6], p, "Utils");
								} else {
									Errors.sendError(Errors.NOT_ONLINE, p, "Utils");
								}
							}
						}
					}
				} else {
					Errors.sendError(Errors.NO_PERMISSION, p, "Utils");
				}
			} else {
				if (args.length == 0) {
					log.info("You need to add a player to teleport and a location or player to tp to!");
				} else if (args.length == 1) {
					log.info("You need to add a player to teleport and a location or player to tp to!");
				} else if (args.length == 2) {
					if (isNumber(args[1])) {
						log.info("You need to add a player to teleport and a location or player to tp to!");
					} else {
						if (isNumber(args[2])) {
							log.info("You need to add a player to tp to!");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName());
							} else {
								log.info("That player is not online!");
							}
						}
					}
				} else if (args.length == 3) {
					if (isNumber(args[1])) {
						log.info("You need to add a player to teleport and a location or player to tp to!");
					} else {
						if (isNumber(args[2])) {
							log.info("You need to add a player to tp to!");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName());
							} else {
								log.info("That player is not online!");
							}
						}
					}
				} else if (args.length == 4) {
					if (isNumber(args[1])) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Location loc = new Location(Bukkit.getServer().getPlayer(args[0]).getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]));
							Bukkit.getServer().getPlayer(args[0]).teleport(loc);
							log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + Double.valueOf(args[1]) + " Y: " + Double.valueOf(args[2]) + " Z: " + Double.valueOf(args[3]));
						} else {
							log.info("That player is not online!");
						}
					} else {
						if (isNumber(args[2])) {
							log.info("You need to add a player to tp to!");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName());
							} else {
								log.info("That player is not online!");
							}
						}
					}
				} else if (args.length == 5) {
					if (isNumber(args[1])) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Location loc = new Location(Bukkit.getServer().getPlayer(args[0]).getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), 0);
							Bukkit.getServer().getPlayer(args[0]).teleport(loc);
							log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + Double.valueOf(args[1]) + " Y: " + Double.valueOf(args[2]) + " Z: " + Double.valueOf(args[3]) + " Y-Rotation: " + Double.valueOf(args[4]));
						} else {
							log.info("That player is not online!");
						}
					} else {
						if (isNumber(args[2])) {
							log.info("You need to add a player to tp to!");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName());
							} else {
								log.info("That player is not online!");
							}
						}
					}
				} else if (args.length == 6) {
					if (isNumber(args[1])) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Location loc = new Location(Bukkit.getServer().getPlayer(args[0]).getWorld(), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5]));
							Bukkit.getServer().getPlayer(args[0]).teleport(loc);
							log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + Double.valueOf(args[1]) + " Y: " + Double.valueOf(args[2]) + " Z: " + Double.valueOf(args[3]) + " Y-Rotation: " + Double.valueOf(args[4]) + " X-Rotation: " + Double.valueOf(args[5]));
						} else {
							log.info("That player is not online!");
						}
					} else {
						if (isNumber(args[2])) {
							log.info("You need to add a player to tp to!");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName());
							} else {
								log.info("That player is not online!");
							}
						}
					}
				} else if (args.length >= 7) {
					if (isNumber(args[1])) {
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Location loc = new Location(Bukkit.getServer().getWorld(args[6]), Double.valueOf(args[1]), Double.valueOf(args[2]), Double.valueOf(args[3]), Float.valueOf(args[4]), Float.valueOf(args[5]));
							Bukkit.getServer().getPlayer(args[0]).teleport(loc);
							log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to X: " + Double.valueOf(args[1]) + " Y: " + Double.valueOf(args[2]) + " Z: " + Double.valueOf(args[3]) + " Y-Rotation: " + Double.valueOf(args[4]) + " X-Rotation: " + Double.valueOf(args[5]) + " In World: " + args[6]);
						} else {
							log.info("That player is not online!");
						}
					} else {
						if (isNumber(args[2])) {
							log.info("You need to add a player to tp to!");
						} else {
							if ((Bukkit.getServer().getPlayer(args[0]) != null) && (Bukkit.getServer().getPlayer(args[1]) != null)) {
								Bukkit.getServer().getPlayer(args[0]).teleport(Bukkit.getServer().getPlayer(args[1]));
								log.info("Teleported " + Bukkit.getServer().getPlayer(args[0]).getName() + " to " + Bukkit.getServer().getPlayer(args[1]).getName());
							} else {
								log.info("That player is not online!");
							}
						}
					}
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
