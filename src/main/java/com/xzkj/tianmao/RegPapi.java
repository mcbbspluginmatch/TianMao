package com.xzkj.tianmao;

import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.entity.Player;

public class RegPapi extends PlaceholderHook {
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        TMHashMap hashmap = new TMHashMap();
        Api api = hashmap.getData(player);
        if (identifier.equalsIgnoreCase("PlayerTianMaoName")){
            return "";
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoNumberOfProducts")){
            return "";
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoSalesVolume")){
            return "";
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoLevel")){
            return "";
        }
        return "作者:小正QQ:1419158026";

    }
}
