#MinecartChestFilter
##Storage Minecart ... with filters!
###Builds
You can obtain builds of each version from the [release page](https://github.com/Kory33/MinecartChestFilter/releases).

###Description
This plug-in introduces **filtered storage minecarts** to the Bukkit server. They can filter out (in other words, forbid players/hoppers to put) several types of items.

To spawn filtered storage minecarts, simply run `/spawnFilteredStorageMinecart`(alias: `/spawnfsminecart`) command in the game.
By default, this command is only available to the OP groups. Permissions to `filteredstorageminecart.*` may be granted if needed.

##TODOs
Filter types(Brewing, Fuels, NOT-filters etc.)  
Log-in-time update notification  
In-game help command/detailed explanations  

##Compiling
If you want to build from the source, you can either `git clone` or download the source from the [release page](https://github.com/Kory33/MinecartChestFilter/releases). Most dependencies are resolved by the Maven dependencies in pom.xml, however, this project utilizes several classes from NMS(net.minecraft.server). Therefore the NMS packages should be manually added to the build path while building.