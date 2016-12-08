package com.github.Kory33.minecartchestfilter.filter;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Filter that only allows fuel items.
 * Implementation utilizes version-dependent methods.
 * @author Kory33
 */
public final class FuelFilter extends Filter {
    @Override
    public boolean isItemAllowed(ItemStack itemStack) {
        net.minecraft.server.v1_10_R1.ItemStack nmItemStack = CraftItemStack.asNMSCopy(itemStack);
        return net.minecraft.server.v1_10_R1.TileEntityFurnace.isFuel(nmItemStack);
    }
}
