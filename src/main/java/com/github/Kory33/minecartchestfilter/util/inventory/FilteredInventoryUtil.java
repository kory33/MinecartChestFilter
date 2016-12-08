package com.github.Kory33.minecartchestfilter.util.inventory;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.entity.Entity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.github.Kory33.minecartchestfilter.filter.Filter;
import com.github.Kory33.minecartchestfilter.util.NBTUtil;

/**
 * Collection of miscellaneous methods related to filtered inventory
 * @author Kory33
 *
 */
public final class FilteredInventoryUtil {
    /**
     * Process the InventoryClickEvent for the filtered inventory.
     * This method can set the event cancelled,
     * i.e. it runs event.setCancelled if the event should be cancelled.
     * @param event
     */
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

        // if the item should be filtered out
        if(!FilteredInventoryUtil.isAllowed(filterCheckTarget, (Entity)destInventory.getHolder())){
            event.setCancelled(true);
        }

        return;
    }

    /**
     * Process the InventoryDragEvent for the filtered inventory.
     * This method can set the event cancelled,
     * i.e. it runs event.setCancelled if the event should be cancelled.
     * @param event
     */
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

    /**
     * Process the InventoryMoveItemEvent for the filtered inventory.
     * This method can set the event cancelled,
     * i.e. it runs event.setCancelled if the event should be cancelled.
     * @param event
     */
    public static void processFilteredInventoryMoveItem(InventoryMoveItemEvent event) {
        if(!isAllowed(event.getItem(), (Entity)event.getDestination().getHolder())){
            event.setCancelled(true);
        }
    }
    
    /**
     * Evaluates if the given itemstack is allowed to be placed in the filtered inventory.
     * @param filterCheckTarget the itemstack against which the filtering is done
     * @param holder the holder of the filtered inventory.
     * @return if the given itemstack is allowed to be placed in the filtered inventory.
     */
    private static boolean isAllowed(ItemStack filterCheckTarget, Entity holder) {
        return Filter.getFilterInstance(holder).isItemAllowed(filterCheckTarget);
    }

    /**
     * Evaluates if the top inventory is owned by a filtered storage minecart
     * @param event in which the status is to be evaluated
     * @return if the top inventory is owned by a filtered storage minecart
     */
    public static boolean isTopInventoryFiltered(InventoryEvent event){
        return FilteredInventoryUtil.isInventoryFiltered(event.getView().getTopInventory());
    }
    
    /**
     * Evaluates if the given inventory is owned by a filtered storage minecart
     * @param checkTarget target for the evaluation
     * @return if the given inventory is owned by a filtered storage minecart
     */
    public static boolean isInventoryFiltered(Inventory checkTarget){
        InventoryHolder inventoryHolder = checkTarget.getHolder();
        
        if(!(inventoryHolder instanceof Entity)){
            return false;
        }
        
        return NBTUtil.isEntityFilteredStorgeMinecart((Entity) inventoryHolder);
    }
}
