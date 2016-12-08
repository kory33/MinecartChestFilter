package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        
        if(args.length > 1){
            return false;
        }
        
        // tag defaults to the furnace filter
        String filterKey = TagsUtil.FILTERED_MINECART_TAG_FURNACE;

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("fuel")){
                filterKey = TagsUtil.FILTERED_MINECART_TAG_FUEL;
            }else{
                return false;
            }
        }
        
        Player pSender = (Player) sender;
        
        if(!EntityUtil.spawnFilteredMinecart(pSender.getWorld(), pSender.getLocation(), filterKey)){
            pSender.getServer().getLogger().info("Something went wrong; could not spawn filtered minecart!");
        }
        
        return true;
    }
}
