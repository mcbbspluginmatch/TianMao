package com.xzkj.tianmao.utils;
import org.bukkit.inventory.ItemStack;
import java.lang.reflect.Method;
import com.xzkj.tianmao.nms.NMSUtils;
import com.xzkj.tianmao.nms.nbt.NBTUtils;
import com.xzkj.tianmao.utils.ReflectionUtils;
public class ItemSerializer {
    private static Method MOJANGSON_TO_NBT;

    static {
        try {
            MOJANGSON_TO_NBT = ReflectionUtils.getMethod(NMSUtils.getNMSClass("MojangsonParser"), "parse", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Prevent accidental construction

    private ItemSerializer() {
    }

    /**
     * Create the item mojangson data
     *
     * @param itemStack the item
     * @return {@link String}
     */
    public static String getItemStackJson(ItemStack itemStack) {
        Object savedTag = NBTUtils.saveItemNBT(itemStack);
        if (savedTag != null) {
            return savedTag.toString();
        } else {
            return "null";
        }
    }

    /**
     * Load a item by using mojangson data
     *
     * @param mojangson the mojangson data
     * @return {@link ItemStack}
     */
    public static ItemStack loadItemStackJson(String mojangson) {
        ItemStack itemStack = null;
        try {
            Object nbtTag = ReflectionUtils.invokeMethod(MOJANGSON_TO_NBT, null, mojangson);
            Object nmsItem = NBTUtils.newNMSItemStack(nbtTag);
            itemStack = (ItemStack) NMSUtils.getBukkitItem(nmsItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemStack;
    }

}
