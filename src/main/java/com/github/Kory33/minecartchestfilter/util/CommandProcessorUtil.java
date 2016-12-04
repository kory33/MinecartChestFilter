package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        
        //TODO spawn MinecartChest along with NBT that indicates a filter
        
        return true;
    }
}
