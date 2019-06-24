package com.xzkj.tianmao;

import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class RegPapi extends PlaceholderHook {
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        TMHashMap hashmap = new TMHashMap();
        Api api = hashmap.getData(player);
        File PlayerShopFile = new File(MainData.PlayerShop.getAbsolutePath() + "\\" + player.getName() + ".yml");
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        if (identifier.equalsIgnoreCase("PlayerTianMaoName")){
            return playerShopYml.getString("商店名称");
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoNumberOfProducts")){
            return playerShopYml.getString("商店商品数量");
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoSalesVolume")){
            return playerShopYml.getString("商店积分");
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoLevel")){
            return api.getTianMaoShopSegment(player);
        }
        return "作者:小正QQ:1419158026";

    }
}
