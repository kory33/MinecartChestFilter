package com.github.Kory33.minecartchestfilter.filter;

import com.github.Kory33.minecartchestfilter.util.NMSReflUtil;
import jp.llv.reflection.Refl;
import org.bukkit.inventory.ItemStack;

/**
 * Filter that only allows smeltable items. 
 * Implementation utilizes version-dependent methods.
 * @author Kory33
 */
public final class FurnaceFilter extends Filter {
    private static final Refl.RInstanceMethod recipeGetResultMethod;
    static {
        Refl.RInstanceMethod method = null;
        try {
            Refl.RObject furnaceRecipeInstance = NMSReflUtil.getNMSClass("RecipesFurnace").invoke("getInstance");
            method = furnaceRecipeInstance.getMethod("getResult", NMSReflUtil.getNMSClass("ItemStack").unwrap());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        recipeGetResultMethod = method;
    }

    private static boolean isSmeltable(Object nmsItemStack) {
        try {
            Refl.RObject smeltResult = recipeGetResultMethod.invoke(nmsItemStack);
            if (smeltResult.isNull()) {
                return false;
            }
            return !smeltResult.invoke("getName").unwrapAs(String.class).equals("Air");
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public boolean isItemAllowed(ItemStack itemStack) {
        return isSmeltable(NMSReflUtil.getNMSItemStack(itemStack));
    }
}
