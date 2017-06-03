package com.github.Kory33.minecartchestfilter.util;

import java.util.HashSet;
import java.util.Set;

import jp.llv.reflection.Refl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.StorageMinecart;

/**
 * Util class for manipulating NBT tags
 * Highly version-dependent as this class involves direct NBT manipulation.
 * @author Kory33
 */
public final class NBTUtil {
    /**
     * Attempt to add filtered-minecart specifier to the StorageMinecart
     * @param sMinecart target entity
     * @return whether the NBT tag is successfully added
     */
    public static boolean addFilterNBTToSMinecart(StorageMinecart sMinecart, String tagKey){
        Refl.RObject sMinecartHandler = NMSReflUtil.getNMSEntity(sMinecart);
        
        if(isEntityFilteredStorageMinecart(sMinecart)){
            return false;
        }
        
        try {
            return sMinecartHandler.invoke("a", tagKey).unwrapAsBoolean();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add a filter's name to the minecart name
     * @param sMinecart
     * @param filterName
     */
    public static void addFilterNameToSMinecart(StorageMinecart sMinecart, String filterName){
        sMinecart.setCustomName(sMinecart.getName() + " [" + filterName + "]");
    }
    
    /**
     * Check if the given entity is a filtered-storage minecart
     * @param checkTarget target entity
     * @return whether the given entity is a filtered-storage minecart
     */
    public static boolean isEntityFilteredStorageMinecart(Entity checkTarget){
        // check for entity type
        if(!(checkTarget instanceof StorageMinecart)){
            return false;
        }
        
        Refl.RObject sMinecartHandler = NMSReflUtil.getNMSEntity(checkTarget);

        // check for NBT presence
        Set<String> entityFilteringTags;
        try {
            entityFilteringTags = (Set<String>)sMinecartHandler.invoke("P").unwrap();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
        entityFilteringTags.retainAll(TagsUtil.FILTERED_MINECART_TAG_SET);
        return entityFilteringTags.size() != 0;
    }
    
    /**
     * Get the filtering tag from the entity
     * @param checkTarget Entity that is expected to have a single filtering tag
     * @return filtering tag in string
     * @throws IllegalStateException when illegal number of tags are contained in the entity tag set
     */
    public static String getFilteringTags(Entity checkTarget){
        Refl.RObject sEntityHandler = NMSReflUtil.getNMSEntity(checkTarget);

        Set<String> tagsSet = null;
        try {
            tagsSet = (Set<String>)sEntityHandler.invoke("P").unwrap();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        Set<String> intersection = new HashSet<>(tagsSet);
        intersection.retainAll(TagsUtil.FILTERED_MINECART_TAG_SET);
        
        if(intersection.size() == 0){
            return null;
        }else if (intersection.size() != 1){
            throw new IllegalStateException("Checking filtering tag: check target entity contains more than two tags!");
        }
        
        return intersection.iterator().next();
    }
}
