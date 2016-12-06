package com.github.Kory33.minecartchestfilter.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Util class that interacts with recipes
 * @author Kory33
 *
 */
public class RecipeUtil {
    /**
     * Get a set of all smeltable ItemStacks
     * @return set of all smeltable ItemStacks
     */
    public static Set<ItemStack> getSmeltableItemStacks(){
        Set<ItemStack> smeltableItemStacks = new HashSet<>();

        // Acquire a list of all the smeltable itemstacks.
        Iterator<Recipe> itr = Bukkit.recipeIterator();
        while (itr.hasNext()){
            Recipe recipe = itr.next();
            if (!(recipe instanceof FurnaceRecipe)){
                continue;
            }	
            smeltableItemStacks.add(((FurnaceRecipe) recipe).getInput());
        }

        return smeltableItemStacks;
    }
}
