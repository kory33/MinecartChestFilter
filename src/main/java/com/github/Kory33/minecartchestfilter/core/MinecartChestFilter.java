package com.github.Kory33.minecartchestfilter.core;

import org.bukkit.plugin.java.JavaPlugin;

public class MinecartChestFilter extends JavaPlugin {
    public static final String FILTERED_MINECART_METAKEY = "cart_inventory_filtering_type";
    
    @Override
    public void onEnable(){
        this.getLogger().info("Loaded MinecartChestFilter. Started Initialization...");
    }

    @Override
    public void onDisable(){
        this.getLogger().info("Unloaded MinecartChestFilter successfully.");
    }
}
