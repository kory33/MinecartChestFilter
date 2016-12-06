package com.github.Kory33.minecartchestfilter.util.inventory;

import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.Kory33.minecartchestfilter.filter.Filter;

public class FilteredInventoryUtil {
    public static void processFilteredInventoryClick(InventoryClickEvent event){
        Inventory clickedInventory = event.getClickedInventory();
        Inventory destInventory = event.getView().getTopInventory();
        
        if(clickedInventory == null){
            return;
        }
        
        boolean isClickedTop = clickedInventory.equals(destInventory);
        ItemStack filterCheckTarget = null;
        
        // detect the item that is being moved
        if(event.isShiftClick() && !isClickedTop){
            filterCheckTarget = event.getCurrentItem();
        }else if(!event.isShiftClick() && isClickedTop){
            filterCheckTarget = event.getCursor();
        }
        
        // if the item is not being moved
        if(filterCheckTarget == null || filterCheckTarget.getAmount() == 0){
            return;
        }

        event.setCancelled(!FilteredInventoryUtil.isAllowed(filterCheckTarget, (Entity)destInventory.getHolder()));

        return;
    }

    private static boolean isAllowed(ItemStack filterCheckTarget, Entity holder) {
        return Filter.getFilterInstance(holder).isItemAllowed(filterCheckTarget);
    }
}
