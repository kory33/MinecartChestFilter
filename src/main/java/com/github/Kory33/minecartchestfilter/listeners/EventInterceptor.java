package com.github.Kory33.minecartchestfilter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.PluginManager;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;
import com.github.Kory33.minecartchestfilter.util.inventory.FilteredInventoryUtil;

/**
 * Class that intercepts Bukkit-implemented events
 * @author Kory33
 */
public class EventInterceptor implements Listener {
    private PluginManager pManager;

    public EventInterceptor(MinecartChestFilter plugin){
        this.pManager = plugin.getServer().getPluginManager();
        this.pManager.registerEvents(this, plugin);
        
        plugin.getLogger().info("Registered core interceptors");
    }
    
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (FilteredInventoryUtil.isInventoryFiltered(event.getClickedInventory())){
            FilteredInventoryUtil.processFilteredInventoryClick(event);
            
            if(event.isCancelled()){
                return;
            }
        }
        return;
    }
    
    
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event){
        if (FilteredInventoryUtil.isTopInventoryFiltered(event)){
            FilteredInventoryUtil.processFilteredInventoryDrag(event);
            
            if(event.isCancelled()){
                return;
            }
        }
        return;
    }
}
