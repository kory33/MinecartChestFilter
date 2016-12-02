package com.github.Kory33.minecartchestfilter.core;

import org.bukkit.plugin.java.JavaPlugin;

public class MinecartChestFilter extends JavaPlugin {
    @Override
    public void onEnable(){
        this.getLogger().info("Loaded MinecartChestFilter. Started Initialization...");
    }

    @Override
    public void onDisable(){
        this.getLogger().info("Unloaded MinecartChestFilter successfully.");
    }
}
