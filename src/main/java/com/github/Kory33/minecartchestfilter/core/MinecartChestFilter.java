package com.github.Kory33.minecartchestfilter.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.Kory33.minecartchestfilter.listeners.EventInterceptor;
import com.github.Kory33.minecartchestfilter.util.CommandProcessorUtil;

/**
 * Core class of the MinecartChestFilter plug-in
 * @author Kory33
 *
 */
public class MinecartChestFilter extends JavaPlugin {
    public static final String FILTERED_MINECART_TAG_FURNACE = "CartInventoryFilteringType";
    // set of all the tags for filtering
    private static final Set<String> FILTERED_MINECART_TAG_SET = Stream.of(
                FILTERED_MINECART_TAG_FURNACE
            ).collect(Collectors.toSet());
    
    
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
    
    /**
     * Get the set of all the valid filter tags.
     * @return set of all the valid filter tags
     */
    public static Set<String> getFilteredMinecartTagSet(){
        return new HashSet<>(MinecartChestFilter.FILTERED_MINECART_TAG_SET);
    }
}
