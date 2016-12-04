package com.github.Kory33.minecartchestfilter.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;


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
        Entity spawnedMinecart = pSender.getWorld().spawnEntity(pSender.getLocation(), EntityType.MINECART_CHEST);

        if(!NBTUtil.addFilterNBTToSMinecart((StorageMinecart)spawnedMinecart)){
            pSender.getServer().getLogger().info("Something went wrong; could not spawn filtered minecart!");
            spawnedMinecart.remove();
        }
        
        return true;
    }
}
