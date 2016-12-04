package com.github.Kory33.minecartchestfilter.util;

import java.util.Set;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.StorageMinecart;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;

/**
 * Util class for manipulating NBT tags
 * Highly version-dependent as this class involves direct NBT manipulation.
 */
public class NBTUtil {
    /**
     * Attempt to add filtered-minecart-specifier to the StorageMinecart
     * @param sMinecart
     * @return whether the NBT tag is successfully added
     */
    public static boolean addFilterNBTToSMinecart(StorageMinecart sMinecart){
        net.minecraft.server.v1_10_R1.Entity sMinecartHandler = ((CraftEntity) sMinecart).getHandle();
        
        if(isEntityFilteredStorgeMinecart(sMinecart)){
            return false;
        }
        
        // grant Tags key to the Storage Minecart
        return sMinecartHandler.a(MinecartChestFilter.FILTERED_MINECART_TAG_BASE);
    }
    
    public static boolean isEntityFilteredStorgeMinecart(Entity checkTarget){
        // check for entity type
        if(!(checkTarget instanceof StorageMinecart)){
            return false;
        }
        
        net.minecraft.server.v1_10_R1.Entity sMinecartHandler = ((CraftEntity) checkTarget).getHandle();
        
        // check for NBT presence
        Set<String> entityTags = sMinecartHandler.P();
        return entityTags.contains(MinecartChestFilter.FILTERED_MINECART_TAG_BASE);
    }
}
