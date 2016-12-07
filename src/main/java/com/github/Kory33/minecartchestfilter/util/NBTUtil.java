package com.github.Kory33.minecartchestfilter.util;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.StorageMinecart;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;

/**
 * Util class for manipulating NBT tags
 * Highly version-dependent as this class involves direct NBT manipulation.
 * @author Kory33
 */
public class NBTUtil {
    /**
     * Attempt to add filtered-minecart specifier to the StorageMinecart
     * @param sMinecart target entity
     * @return whether the NBT tag is successfully added
     */
    public static boolean addFilterNBTToSMinecart(StorageMinecart sMinecart, String tagKey){
        net.minecraft.server.v1_10_R1.Entity sMinecartHandler = ((CraftEntity) sMinecart).getHandle();
        
        if(isEntityFilteredStorgeMinecart(sMinecart)){
            return false;
        }
        
        // grant Tags key to the Storage Minecart
        return sMinecartHandler.a(tagKey);
    }
    
    /**
     * Check if the given entity is a filtered-storage minecart
     * @param checkTarget target entity
     * @return whether the given entity is a filtered-storage minecart
     */
    public static boolean isEntityFilteredStorgeMinecart(Entity checkTarget){
        // check for entity type
        if(!(checkTarget instanceof StorageMinecart)){
            return false;
        }
        
        net.minecraft.server.v1_10_R1.Entity sMinecartHandler = ((CraftEntity) checkTarget).getHandle();
        
        // check for NBT presence
        Set<String> entityFilteringTags = new HashSet<>(sMinecartHandler.P());
        entityFilteringTags.retainAll(MinecartChestFilter.getFilteredMinecartTagSet());
        return entityFilteringTags.size() != 0;
    }
    
    /**
     * Get the filtering tag from the entity
     * @param checkTarget Entity that is expected to have a single filtering tag
     * @return filtering tag in string
     * @throws IllegalStateException when illegal number of tags are contained in the entity tag set
     */
    @Nullable
    public static String getFilteringTags(Entity checkTarget){
        net.minecraft.server.v1_10_R1.Entity sEntityHandler = ((CraftEntity) checkTarget).getHandle();
        
        Set<String> tagsSet = sEntityHandler.P();
        
        Set<String> intersection = new HashSet<String>(tagsSet);
        intersection.retainAll(MinecartChestFilter.getFilteredMinecartTagSet());
        
        if(intersection.size() == 0){
            return null;
        }else if (intersection.size() != 1){
            throw new IllegalStateException("Checking filtering tag: check target entity contains more than two tags!");
        }
        
        return intersection.iterator().next();
    }
}
