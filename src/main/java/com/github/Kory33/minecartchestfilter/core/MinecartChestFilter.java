package com.github.Kory33.minecartchestfilter.core;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.Kory33.minecartchestfilter.listeners.EventInterceptor;
import com.github.Kory33.minecartchestfilter.util.CommandProcessorUtil;

public class MinecartChestFilter extends JavaPlugin {
    public static final String FILTERED_MINECART_TAG_BASE = "CartInventoryFilteringType";
    
    private ArrayList<Listener> eventInterceptors;
    
    @Override
    public void onEnable(){
        this.getLogger().info("Started initialization...");
        
        this.eventInterceptors = new ArrayList<Listener>();
        
        this.eventInterceptors.add(new EventInterceptor(this));

        this.getLogger().info("Completed initialization.");
    }

    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("spawnfilteredstorageminecart")){
            return CommandProcessorUtil.spawnFSMinecart(sender, command, label, args);
        }
        
        sender.sendMessage("Command is not yet implemented.");
        return false;
    };
    
    
    @Override
    public void onDisable(){
        for (Listener listener : eventInterceptors) {
            HandlerList.unregisterAll(listener);
        }
        
        this.getLogger().info("Unloaded/unregistered MinecartChestFilter successfully.");
    }
}
