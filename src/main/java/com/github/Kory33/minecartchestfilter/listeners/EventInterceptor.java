package com.github.Kory33.minecartchestfilter.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.PluginManager;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;
import com.github.Kory33.minecartchestfilter.util.NBTUtil;
import com.github.Kory33.minecartchestfilter.util.inventory.FilteredInventoryUtil;

/**
 * Class that intercepts Bukkit top-level events
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
        InventoryView inventoryView = event.getView();
        InventoryHolder topInventoryHolder = inventoryView.getTopInventory().getHolder();

        if (topInventoryHolder instanceof Entity && NBTUtil.isEntityFilteredStorgeMinecart((Entity)topInventoryHolder)){
            FilteredInventoryUtil.processFilteredInventoryClick(event);
            
            if(event.isCancelled()){
                return;
            }
        }
        
        return;
    }
}
