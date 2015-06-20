package io.github.wolfleader116.utils;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class SpawnMob {
	
	public static boolean spawn(EntityType entity, int amount, Location loc) {
		if (amount <= 0) {
			return false;
		} else {
			for (int i = amount; i > 0; i--) {
				loc.getWorld().spawnEntity(loc, entity);
			}
			return true;
		}
	}

}
