package com.github.Kory33.minecartchestfilter.core;

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
        if ()
    }
}
