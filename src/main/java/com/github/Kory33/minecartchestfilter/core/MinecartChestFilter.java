package com.github.Kory33.minecartchestfilter.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    /** tag for the furnace-filtered storage minecart */
    public static final String FILTERED_MINECART_TAG_FURNACE = "FilteredMinecartFurnaceFilter";

    /** set of all the filtering tags */
    private static final Set<String> FILTERED_MINECART_TAG_SET = Collections.unmodifiableSet(Stream.of(
                FILTERED_MINECART_TAG_FURNACE
            ).collect(Collectors.toSet()));
    
    /** mapping of filter tag to filter name */
    public static final Map<String, String> FILTERED_MINECART_FILTER_NAME_MAP;
    static{
        Map<String, String> map = new HashMap<>();
        map.put(FILTERED_MINECART_TAG_FURNACE, "Furnace filter");
        FILTERED_MINECART_FILTER_NAME_MAP = Collections.unmodifiableMap(map);
    }
    
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
