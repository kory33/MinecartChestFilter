package com.github.Kory33.minecartchestfilter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;
import com.github.Kory33.minecartchestfilter.util.inventory.FilteredInventoryUtil;

/**
 * Class that intercepts Bukkit-implemented events
 * @author Kory33
 */
public class EventInterceptor implements Listener {

    public EventInterceptor(MinecartChestFilter plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        plugin.getLogger().info("Registered core interceptors");
    }
    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (FilteredInventoryUtil.isTopInventoryFiltered(event)){
            FilteredInventoryUtil.processFilteredInventoryClick(event);
        }
    }
    
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        if (FilteredInventoryUtil.isTopInventoryFiltered(event)){
            FilteredInventoryUtil.processFilteredInventoryDrag(event);
        }
    }
    
    
    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event){
        if (FilteredInventoryUtil.isInventoryFiltered(event.getDestination())){
            FilteredInventoryUtil.processFilteredInventoryMoveItem(event);
        }
    }
}
