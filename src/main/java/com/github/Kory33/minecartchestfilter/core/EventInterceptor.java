package com.github.Kory33.minecartchestfilter.core;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.PluginManager;

class EventInterceptor implements Listener {
    protected EventInterceptor(MinecartChestFilter plugin){
        PluginManager pManager = plugin.getServer().getPluginManager();
        pManager.registerEvents(this, plugin);
    }
    
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event){
        Entity clickedEntity = event.getRightClicked();
        
        // if the clicked entity is a storage minecart
        if (clickedEntity instanceof StorageMinecart){
            onPlayerInteractWithStorageMinecart(event);
        }
        
        return;
    }
    
    
    private void onPlayerInteractWithStorageMinecart(PlayerInteractEntityEvent event) {
        Minecart clickedMinecart = (Minecart) event.getRightClicked();
        
        // check if the minecart has the metadata
        if (!clickedMinecart.hasMetadata(MinecartChestFilter.FILTERED_MINECART_METAKEY)){
            return;
        }
        
        event.setCancelled(true);
        return;
    }
}
