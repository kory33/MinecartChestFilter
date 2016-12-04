package com.github.Kory33.minecartchestfilter.event;

import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Represents an event that is called when a player right clicks a filtered-storage minecart.
 * @author Kory33
 *
 */
public class PlayerInteractFilteredStorageMinecartEvent extends PlayerInteractEntityEvent{
    private StorageMinecart clickedStorageMinecart;

    public PlayerInteractFilteredStorageMinecartEvent(Player who, StorageMinecart clickedStorageMinecart) {
        super(who, clickedStorageMinecart);
        this.clickedStorageMinecart = clickedStorageMinecart;
    }
    
    public StorageMinecart getRightClickedMinecart(){
        return this.clickedStorageMinecart;
    }

}
