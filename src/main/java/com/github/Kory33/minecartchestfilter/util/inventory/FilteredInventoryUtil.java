package com.github.Kory33.minecartchestfilter.util.inventory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.github.Kory33.minecartchestfilter.filter.Filter;
import com.github.Kory33.minecartchestfilter.util.NBTUtil;

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

    public static void processFilteredInventoryDrag(InventoryDragEvent event) {
        Inventory topInventory = event.getView().getTopInventory();

        if(isAllowed(event.getOldCursor(), (Entity)topInventory.getHolder())){
            return;
        }
        
        Set<Integer> rawDraggedSlots = event.getRawSlots();
        Set<Integer> rawFilteredDraggedSlots = new HashSet<>();

        Iterator<Integer> slotItr = rawDraggedSlots.iterator();
        while(slotItr.hasNext()){
            int nextSlot = slotItr.next();
            
            // if the slot is included in the top inventory
            if(nextSlot < topInventory.getSize()){
                rawFilteredDraggedSlots.add(nextSlot);
            }
        }
        
        if(rawFilteredDraggedSlots.size() > 0){
            event.setCancelled(true);
        }
        return;
    }

    private static boolean isAllowed(ItemStack filterCheckTarget, Entity holder) {
        return Filter.getFilterInstance(holder).isItemAllowed(filterCheckTarget);
    }

    /**
     * Evaluates if the top inventory is of filtered storage minecart
     * @param event in which the status is to be evaluated
     * @return if the top inventory is of filtered storage minecart
     */
    public static boolean isTopInventoryFiltered(InventoryEvent event){
        InventoryView inventoryView = event.getView();
        InventoryHolder topInventoryHolder = inventoryView.getTopInventory().getHolder();
        
        if(!(topInventoryHolder instanceof Entity)){
            return false;
        }
        
        return NBTUtil.isEntityFilteredStorgeMinecart((Entity)topInventoryHolder);
    }
}
