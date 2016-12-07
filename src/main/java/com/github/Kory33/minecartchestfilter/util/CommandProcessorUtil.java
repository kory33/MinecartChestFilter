package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.Kory33.minecartchestfilter.core.MinecartChestFilter;

/**
 * Util class for processing commands
 * @author Kory33
 */
public class CommandProcessorUtil {
    /**
     * Execute "/spawnFilteredStorageMinecart <type>"
     * 
     * @param sender
     * @param command
     * @param label
     * @param args
     * @return whether the command is executed successfully
     */
    public static boolean spawnFSMinecart(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Command only available for players.");
            return true;
        }
        
        //TODO implement argument(type) processing
        
        Player pSender = (Player) sender;
        String filterKey = MinecartChestFilter.FILTERED_MINECART_TAG_FURNACE;
        
        if(!EntityUtil.spawnFilteredMinecart(pSender.getWorld(), pSender.getLocation(), filterKey)){
            pSender.getServer().getLogger().info("Something went wrong; could not spawn filtered minecart!");
        }
        
        return true;
    }
}
