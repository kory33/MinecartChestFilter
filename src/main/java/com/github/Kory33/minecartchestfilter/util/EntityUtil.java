package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.StorageMinecart;

public class EntityUtil {
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
