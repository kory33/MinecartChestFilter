package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.minecart.StorageMinecart;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;

import net.minecraft.server.v1_10_R1.NBTTagCompound;

/**
 * Util class for manipulating NBT tags
 * Highly version-dependent.
 */
public class NBTUtil {
    public static void addFilterNBTToSMinecart(StorageMinecart sMinecart){
        net.minecraft.server.v1_10_R1.Entity sMinecartHandler = ((CraftEntity) sMinecart).getHandle();
        NBTTagCompound nbtTag = new NBTTagCompound();
        
        // fetch NBT tags
        sMinecartHandler.e(nbtTag);
        
        //TODO add filtering NBT
        nbtTag.setInt(MinecartChestFilter.FILTERED_MINECART_METAKEY, 1);
        
        // apply nbtTag
        sMinecartHandler.f(nbtTag);
    }
}
