package com.github.Kory33.minecartchestfilter.filter;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import com.github.Kory33.minecartchestfilter.util.RecipeUtil;

/**
 * Filter that only allows smeltable items. 
 * @author Kory33
 *
 */
public class FurnaceFilter extends Filter {
    static private Set<ItemStack> smeltableItemStackSet;
    
    public FurnaceFilter() {
        initFilterDictionary();
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean isItemAllowed(ItemStack itemStack) {
        Iterator<ItemStack> smeltableItr = FurnaceFilter.smeltableItemStackSet.iterator();
        boolean isItemSmeltable = false;
        while(smeltableItr.hasNext()){
            if(smeltableItr.next().getTypeId() == itemStack.getTypeId()){
                isItemSmeltable = true;
                break;
            }
        }
        
        return isItemSmeltable;
    }

    /**
     * Initialize the dictionary of items that are allowed to pass the filter
     */
    private void initFilterDictionary() {
        if(smeltableItemStackSet != null){
            return;
        }
        
        smeltableItemStackSet = RecipeUtil.getSmeltableItemStacks();
        return;
    }

}
