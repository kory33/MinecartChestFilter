package com.github.Kory33.minecartchestfilter.core;

import java.util.ArrayList;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.Kory33.minecartchestfilter.listeners.EventInterceptor;
import com.github.Kory33.minecartchestfilter.listeners.FilteredStorageInteractEventInterceptor;

public class MinecartChestFilter extends JavaPlugin {
    public static final String FILTERED_MINECART_METAKEY = "CartInventoryFilteringType";
    
    private ArrayList<Listener> eventInterceptors;
    
    @Override
    public void onEnable(){
        this.getLogger().info("Started initialization...");
        
        this.eventInterceptors = new ArrayList<Listener>();
        
        this.eventInterceptors.add(new EventInterceptor(this));
        this.eventInterceptors.add(new FilteredStorageInteractEventInterceptor(this));

        this.getLogger().info("Completed initialization.");
    }

    @Override
    public void onDisable(){
        for (Listener listener : eventInterceptors) {
            HandlerList.unregisterAll(listener);
        }
        
        this.getLogger().info("Unloaded/unregistered MinecartChestFilter successfully.");
    }
}
