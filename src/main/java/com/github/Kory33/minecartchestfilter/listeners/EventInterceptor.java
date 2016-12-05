package com.github.Kory33.minecartchestfilter.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.PluginManager;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;
import com.github.Kory33.minecartchestfilter.event.PlayerInteractFilteredStorageMinecartEvent;
import com.github.Kory33.minecartchestfilter.util.NBTUtil;

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
    public void onPlayerInteract(PlayerInteractEntityEvent event){
        Entity clickedEntity = event.getRightClicked();
        
        if (event instanceof PlayerInteractFilteredStorageMinecartEvent){
            return;
        }
        
        // if the clicked entity is a filtered storage minecart
        if (NBTUtil.isEntityFilteredStorgeMinecart(clickedEntity)){
            event.setCancelled(true);
            
            PlayerInteractFilteredStorageMinecartEvent newEvent = 
                    new PlayerInteractFilteredStorageMinecartEvent(event.getPlayer(), (StorageMinecart) clickedEntity);

            this.pManager.callEvent(newEvent);
        }
        
        return;
    }

}
