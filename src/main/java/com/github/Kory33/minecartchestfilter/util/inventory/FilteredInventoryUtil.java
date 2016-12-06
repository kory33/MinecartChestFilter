package com.github.Kory33.minecartchestfilter.util.inventory;

import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.Kory33.minecartchestfilter.filter.Filter;

public class FilteredInventoryUtil {
    public static void processFilteredInventoryClick(InventoryClickEvent event){
        Inventory clickedInventory = event.getClickedInventory();
        
        boolean isClickedBottom = clickedInventory.equals(event.getView().getBottomInventory());
        boolean isClickedTop = clickedInventory.equals(event.getView().getTopInventory());
        
        ItemStack filterCheckTarget = null;
        
        // if the item is moved by shift click
        if(event.isShiftClick() && isClickedBottom){
            filterCheckTarget = event.getCursor();
        }
        
        // if the item is moved by left click
        if(event.isLeftClick() && isClickedTop){
            filterCheckTarget = event.getCurrentItem();
        }
        
        // if the item is not being moved
        if(filterCheckTarget == null){
            return;
        }
        
        event.setCancelled(!FilteredInventoryUtil.isAllowed(filterCheckTarget, (Entity)clickedInventory.getHolder()));

        return;
    }

    private static boolean isAllowed(ItemStack filterCheckTarget, Entity holder) {
        return Filter.getFilterInstance(holder).isItemAllowed(filterCheckTarget);
    }
}
