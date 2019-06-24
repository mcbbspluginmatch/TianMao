package com.xzkj.tianmao;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Api {
    //获取税收

    public int getTianMaoShopTax(Player p){
        File PlayerShopFile = new File(MainData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        return MainData.config.getInt("税务配置店铺等级." + getTianMaoShopSegment(p) + ".税收");
    }
    //获取店铺等级

    public String getTianMaoShopSegment(Player p){
        File PlayerShopFile = new File(MainData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        int integral = playerShopYml.getInt("商店积分");
        String Segment = MainData.TM_Shop_Name.get(0);
        for (int i = 0; i < MainData.TM_Shop_Name.size(); i++) {
            if (integral >= MainData.TM_Shop_Integral.get(i)){
                Segment = MainData.TM_Shop_Name.get(i);
            }
        }
        return Segment;
    }
    //创建店铺

    public void setPlayerShop(Player p, String ShopName) {
        File PlayerShopFile = new File(MainData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        if (!PlayerShopFile.exists()) {
            try {
                if (MainData.TMEcecon.bankHas(p.getName(), Double.valueOf(MainData.TM_Create)).transactionSuccess()) {
                    PlayerShopFile.createNewFile();//创建这个文件
                    MainData.TMEcecon.bankWithdraw(p.getName(), MainData.TM_Create);
                    Tool.log(">>>创建天猫店铺完成", p);
                } else {
                    Tool.log(">>>金钱不足!无法创建店铺!", p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Tool.log(">>您已经创建过天猫店铺!", p);
            return;
        }
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        playerShopYml.set("商店名称", ShopName);
        playerShopYml.set("商店玩家名称", p.getName());
        playerShopYml.set("商店积分", 0);
        playerShopYml.set("商店历史销量", 0);
        playerShopYml.set("商店商品数量", 0);
        try {
            playerShopYml.save(PlayerShopFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //上架手中商品

    public void setShopSale(Player p, String price) {
        File PlayerShopFile = new File(MainData.PlayerShop.getAbsolutePath() + "\\" + p.getName() + ".yml");
        if (!PlayerShopFile.exists()) {
            Tool.log(">>>占时没有创建任何天猫商店!", p);
            return;
        }
        if (p.getItemInHand() == null) {
            Tool.log(">>>当前手中没有任何物品!", p);
            return;
        }

        ItemStack ShopSale = p.getItemInHand();
        ItemMeta ShopSaleMeta = ShopSale.getItemMeta();
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        int ShopShop = playerShopYml.getInt("商店商品数量");
        playerShopYml.set("商店商品数量", ShopShop + 1);
        Integer ShopShopItem = playerShopYml.getInt( ShopSale.getData().toString() + ".数量");
        playerShopYml.set("商品." +  ShopSale.getData().toString() + ".数量", ShopSale.getAmount() + ShopShopItem);
        playerShopYml.set("商品." +  ShopSale.getData().toString() + ".Stack", ShopSale);
        playerShopYml.set("商品." +  ShopSale.getData().toString() + ".Meta", ShopSaleMeta);
        playerShopYml.set("商品." +  ShopSale.getData().toString() + ".价格", price);
        playerShopYml.set("商品." +  ShopSale.getData().toString() + ".唯一标识", ShopSale.getData().toString());
        try {
            playerShopYml.save(PlayerShopFile);
            p.getItemInHand().setAmount(0);
            Tool.log(">>>上架完成!", p);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //遍历全部店铺文件

    public List<File> getTianMaoShopList(){
        File[] fs = MainData.PlayerShop.listFiles();
        List<File> refl = new ArrayList<>();
        for (File datafl: fs){
            if(!datafl.isDirectory()){
                refl.add(datafl);
            }
        }
        return refl;
    }







}
