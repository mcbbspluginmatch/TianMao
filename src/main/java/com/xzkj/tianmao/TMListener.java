package com.xzkj.tianmao;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class TMListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getRawSlot() == -999) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        TMHashMap hashmap = new TMHashMap();
        Api api = hashmap.getData(p);
        View view = hashmap.getDataView(p);
        if (e.getView().getTitle().equalsIgnoreCase("§e[§2天猫商店§e]")) {
            String pShop = e.getCurrentItem().getItemMeta().getDisplayName();
            view.openPlayerShop(p, Bukkit.getPlayer(pShop));
            e.setCancelled(true);
            return;
        }
        if (e.getView().getTitle().contains("§e[§2天猫§e]§4")) {
            String TitleShop = e.getView().getTitle();
            String[] TitleShoptmp = TitleShop.split("§d所属玩家: §2");
            String playerShop = TitleShoptmp[1];
            String ShopItemName = e.getCurrentItem().getItemMeta().getDisplayName();
            File PlayerShopFile = new File(MainData.PlayerShop.getAbsolutePath() + "\\" + playerShop + ".yml");
            YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
            int CommodityPrice = Integer.parseInt(playerShopYml.getString("商品." + ShopItemName + ".价格"));
            int PriceTax = CommodityPrice * api.getTianMaoShopTax(Bukkit.getPlayer(playerShop));
            if (MainData.TMEcecon.bankHas(p.getName(), PriceTax).transactionSuccess()){
                ItemStack dataStack = playerShopYml.getItemStack("商品." + ShopItemName + ".Stack");
                MainData.TMEcecon.bankWithdraw(p.getName(), PriceTax);
                MainData.TMEcecon.depositPlayer(p, CommodityPrice);
                p.getInventory().addItem(dataStack);
                e.setCancelled(true);
                playerShopYml.set("商品." + ShopItemName, null);

                int integral = playerShopYml.getInt("商店积分") + CommodityPrice;
                int history = playerShopYml.getInt("商店历史销量") + 1;
                int Quantity = playerShopYml.getInt("商店商品数量") - 1;
                playerShopYml.set("商店积分", integral);
                playerShopYml.set("商店历史销量", history);
                playerShopYml.set("商店商品数量", Quantity);
                try {
                    playerShopYml.save(PlayerShopFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                p.closeInventory();


            } else {
                e.setCancelled(true);
                p.closeInventory();
                Tool.log("金币不足无法购买!", p);
            }
        }
    }
}
