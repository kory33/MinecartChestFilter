package com.github.Kory33.minecartchestfilter.util.inventory;

import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FilteredInventoryUtil {
    public static void processFilteredInventoryClick(InventoryClickEvent event){
        Inventory clickedInventory = event.getClickedInventory();
        StorageMinecart storageMinecart = (StorageMinecart) clickedInventory.getHolder();
        
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
        
        event.setCancelled(FilteredInventoryUtil.isFiltered(filterCheckTarget, storageMinecart));

        return;
    }

    private static boolean isFiltered(ItemStack filterCheckTarget, StorageMinecart holder) {
        //TODO implementation
        return false;
    }
}
