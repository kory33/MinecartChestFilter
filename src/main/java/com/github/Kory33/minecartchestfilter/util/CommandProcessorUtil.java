package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;

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
        Entity spawnedMinecart = pSender.getWorld().spawnEntity(pSender.getLocation(), EntityType.MINECART_CHEST);
        NBTUtil.addFilterNBTToSMinecart((StorageMinecart)spawnedMinecart);
        
        return true;
    }
}
