package com.github.Kory33.minecartchestfilter.filter;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import com.github.Kory33.minecartchestfilter.util.RecipeUtil;

public class FurnaceFilter extends Filter {
    static private Set<ItemStack> smeltableItemStackSet;
    
    public FurnaceFilter() {
        initFilterDictionary();
    }
    
    @Override
    public boolean isItemAllowed(ItemStack itemStack) {
        Iterator<ItemStack> smeltableItr = FurnaceFilter.smeltableItemStackSet.iterator();
        
        boolean isISSmeltable = false;
        while(smeltableItr.hasNext()){
            if(smeltableItr.next().isSimilar(itemStack)){
                isISSmeltable = true;
                break;
            }
        }
        
        return isISSmeltable;
    }

    @Override
    protected void initFilterDictionary() {
        if(smeltableItemStackSet != null){
            return;
        }
        
        smeltableItemStackSet = RecipeUtil.getSmeltableItemStacks();
        return;
    }

}
