package com.github.Kory33.minecartchestfilter.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;
import com.github.Kory33.minecartchestfilter.event.PlayerInteractFilteredStorageMinecartEvent;
import com.github.Kory33.minecartchestfilter.util.inventory.FilteredInventoryUtil;

public class FilteredStorageInteractEventInterceptor implements Listener{
    private PluginManager pManager;

    public FilteredStorageInteractEventInterceptor(MinecartChestFilter plugin){
        this.pManager = plugin.getServer().getPluginManager();
        this.pManager.registerEvents(this, plugin);
        
        plugin.getLogger().info("Registered listener for PlayerInteractFilteredStorageMinecartEvent");
    }
    
    @EventHandler
    public void onPlayerInteractFilteredStorageMinecart(PlayerInteractFilteredStorageMinecartEvent event){
        FilteredInventoryUtil.openFilteredInventory(event.getPlayer(), event.getRightClickedMinecart());
    }
}
