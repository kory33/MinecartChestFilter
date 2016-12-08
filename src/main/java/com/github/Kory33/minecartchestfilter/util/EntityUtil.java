package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.StorageMinecart;

/**
 * Collection of methods that are related to entity controls.
 * @author Kory33
 *
 */
public final class EntityUtil {
    /**
     * Spawn the minecart to which the filtering tag is attached.
     * @param world World in which the entity will be spawned
     * @param spawnLocation Location at which the entity will be spawned
     * @param filteringTag filtering tag string
     * @return if the filtered minecart is successfully spawned
     */
    public static boolean spawnFilteredMinecart(World world, Location spawnLocation, String filteringTag){
        Entity spawnedMinecart = world.spawnEntity(spawnLocation, EntityType.MINECART_CHEST);
        if (spawnedMinecart == null){
            return false;
        }
        
        if (!NBTUtil.addFilterNBTToSMinecart((StorageMinecart)spawnedMinecart, filteringTag)){
            spawnedMinecart.remove();
            return false;
        }
        
        return true;
    }
}
