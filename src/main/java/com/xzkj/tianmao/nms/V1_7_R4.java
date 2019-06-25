package com.xzkj.tianmao.nms;

import com.xzkj.tianmao.utils.UtilBase64;
import net.minecraft.server.v1_7_R4.NBTBase;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;


public class V1_7_R4 {
    public String getStackBase64(org.bukkit.inventory.ItemStack ts){
        net.minecraft.server.v1_7_R4.ItemStack nmsitem = CraftItemStack.asNMSCopy(ts);
        if (!(nmsitem.hasTag())){
            return "";
        }
        NBTTagCompound compound = nmsitem.hasTag() ? nmsitem.getTag() : new NBTTagCompound();
        return UtilBase64.encode(compound.clone().toString().getBytes());
    }
    public org.bukkit.inventory.ItemStack getOrgItemStack(String netItemStackBase){
        net.minecraft.server.v1_7_R4.ItemStack.
    }
}
