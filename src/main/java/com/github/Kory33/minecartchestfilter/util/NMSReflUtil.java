package com.github.Kory33.minecartchestfilter.util;

import jp.llv.reflection.Refl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Kory on 6/2/2017.
 */
public final class NMSReflUtil {
    private static final String CRAFTBUKKIT_PACKAGE = "org.bukkit.craftbukkit";
    private static final String NMS_PACKAGE = "net.minecraft.server";
    private static final String CRAFTBUKKIT_VERSION;
    static{
        CRAFTBUKKIT_VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    private static final Refl.RClass<?> CRAFT_ITEM_STACK_CLASS = getCraftBukkitClass("inventory.CraftItemStack");

    private static Refl.RClass<?> getClass(String parentPackageName, String className) {
        String targetClassName = String.join(".", parentPackageName, CRAFTBUKKIT_VERSION, className);
        try {
            return Refl.getRClass(targetClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Refl.RClass<?> getCraftBukkitClass(String className) {
        return getClass(CRAFTBUKKIT_PACKAGE, className);
    }

    public static Refl.RClass<?> getNMSClass(String className) {
        return getClass(NMS_PACKAGE, className);
    }

    public static Refl.RObject<?> getNMSItemStack(ItemStack itemStack) {
        try {
            return CRAFT_ITEM_STACK_CLASS.invoke("asNMSCopy", itemStack);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Refl.RObject<?> getNMSEntity(Entity entity) {
        try {
            return Refl.wrap(entity).invoke("getHandle");
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
