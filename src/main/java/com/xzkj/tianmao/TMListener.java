package com.xzkj.tianmao;

import com.xzkj.tianmao.utils.ItemSerializerUtils;
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
        // 基于 title 的背包判断是不好的，建议更换为 InventoryView —— 754503921
        if (e.getView().getTitle().equalsIgnoreCase("§e[§2天猫商店§e]")) {
            String pShop = e.getCurrentItem().getItemMeta().getDisplayName();
            // 不应在 InventoryClickEvent 中开启其他背包 —— 754503921
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
                ItemStack[] Base64dataStack = ItemSerializerUtils.fromBase64(playerShopYml.getString("商品." + ShopItemName + ".Base64ItemStack"));

                MainData.TMEcecon.bankWithdraw(p.getName(), PriceTax);
                MainData.TMEcecon.depositPlayer(p, CommodityPrice);
                p.getInventory().addItem(Base64dataStack);
                e.setCancelled(true);
                playerShopYml.set("商品." + ShopItemName, null);

                int integral = playerShopYml.getInt("商店积分") + CommodityPrice;
                int history = playerShopYml.getInt("商店历史销量") + 1;
                int Quantity = playerShopYml.getInt("商店商品数量") - 1;
                playerShopYml.set("商店积分", integral);
                playerShopYml.set("商店历史销量", history);
                playerShopYml.set("商店商品数量", Quantity);
                try {
                    // 主线程上进行 IO 操作，可能造成服务器卡顿 —— 754503921
                    playerShopYml.save(PlayerShopFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                p.closeInventory();


            } else {
                e.setCancelled(true); // 既然哪个分支都要 setCancelled 为什么不在一开始就cancel了
                // closeInventory 不应在 InventoryClickEvent 中被调用 —— 754503921
                p.closeInventory();
                Tool.log("金币不足无法购买!", p);
            }
        }
    }
}
