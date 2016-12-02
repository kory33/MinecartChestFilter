package com.github.Kory33.minecartchestfilter.core;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecartChestFilter extends JavaPlugin {
    public static final String FILTERED_MINECART_METAKEY = "cart_inventory_filtering_type";
    
    private EventInterceptor eventInterceptor; 
    
    @Override
    public void onEnable(){
        this.getLogger().info("Loaded MinecartChestFilter. Started initialization...");
        this.getLogger().info("Registering the events...");
        
        this.eventInterceptor = new EventInterceptor(this);
        
        this.getLogger().info("Completed initialization.");
    }

    @Override
    public void onDisable(){
        this.getLogger().info("Finalizing MinecartChestFilter...");

        HandlerList.unregisterAll(eventInterceptor);
        
        this.getLogger().info("Unloaded/unregistered MinecartChestFilter successfully.");
    }
}
