package com.github.Kory33.minecartchestfilter.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

/**
 * Collection of methods and constants that are related to Tags
 * @author Kory33
 */
public final class TagsUtil {
    /** tag for the furnace-filtered storage minecart */
    public static final String FILTERED_MINECART_TAG_FURNACE = "FilteredMinecartFurnaceFilter";
    public static final String FILTERED_MINECART_TAG_FUEL = "FilteredMinecartFuelFilter";
    
    
    /** set of all the filtering tags */
    public static final Set<String> FILTERED_MINECART_TAG_SET = Collections.unmodifiableSet(Stream.of(
                FILTERED_MINECART_TAG_FURNACE,
                FILTERED_MINECART_TAG_FUEL
            ).collect(Collectors.toSet()));

    
    /** mapping of filter tag to filter name */
    public static Map<String, String> FILTERED_MINECART_FILTER_NAME_MAP;
    static{
        Map<String, String> map = new HashMap<>();
        map.put(TagsUtil.FILTERED_MINECART_TAG_FURNACE, "Furnace filter");
        map.put(TagsUtil.FILTERED_MINECART_TAG_FUEL, "Fuel Filter");
        FILTERED_MINECART_FILTER_NAME_MAP = Collections.unmodifiableMap(map);
    }
    
    @Nullable
    public static String getFilterKeyFromCommandArgument(String commandArgument){
        if(commandArgument.equalsIgnoreCase("fuel")){
            return TagsUtil.FILTERED_MINECART_TAG_FUEL;
        }else if(commandArgument.equalsIgnoreCase("furnace")){
            return TagsUtil.FILTERED_MINECART_TAG_FURNACE;
        }
        
        return null;
    }
}
