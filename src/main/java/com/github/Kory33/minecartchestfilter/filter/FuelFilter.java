package com.github.Kory33.minecartchestfilter.filter;

import com.github.Kory33.minecartchestfilter.util.NMSReflUtil;
import jp.llv.reflection.Refl;
import org.bukkit.inventory.ItemStack;

/**
 * Filter that only allows fuel items.
 * Implementation utilizes version-dependent methods.
 * @author Kory33
 */
public final class FuelFilter extends Filter {
    private static final Refl.RClassMethod fuelJudgeMethod;
    static {
        Refl.RClassMethod method = null;
        try {
            Refl.RClass<?> nmsItemStackClass = NMSReflUtil.getNMSClass("ItemStack");
            Refl.RClass<?> tileEntityFurnaceClass = NMSReflUtil.getNMSClass("TileEntityFurnace");
            method = tileEntityFurnaceClass.getMethod("isFuel", nmsItemStackClass.unwrap());
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        fuelJudgeMethod = method;
    }

    private static boolean isFuel(Object nmsItemStack) {
        try {
            return fuelJudgeMethod.invoke(nmsItemStack).unwrapAsBoolean();
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isItemAllowed(ItemStack itemStack) {
        return isFuel(NMSReflUtil.getNMSItemStack(itemStack));
    }
}
