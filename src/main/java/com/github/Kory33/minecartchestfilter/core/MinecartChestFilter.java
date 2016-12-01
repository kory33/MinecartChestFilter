package com.github.Kory33.minecartchestfilter.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecartChestFilter extends JavaPlugin {
	@Override
	public void onEnable(){
		
	}
	
	@Override
	public void onDisable(){
		
	}
	
	private Set<ItemStack> getSmeltableItemStacks(){
		Set<ItemStack> smeltableItemStacks = new HashSet<>();
		
		// On an assumption that the recipe is "injective" from the input to the output,
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
