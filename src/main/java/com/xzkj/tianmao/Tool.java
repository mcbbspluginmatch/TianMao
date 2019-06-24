package com.xzkj.tianmao;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class Tool {
    public static void log(String str, Player p){
        if (p == null){
            Bukkit.getLogger().info("§d[§5TianMao§d]§7>>>§b " + str);
        }else {
            p.sendMessage("§d[§5TianMao§d]§7>>>§b " + str);
        }

    }
    public static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        MainData.TMEcecon = (Economy)economyProvider.getProvider();
        return MainData.TMEcecon != null;
    }
}
