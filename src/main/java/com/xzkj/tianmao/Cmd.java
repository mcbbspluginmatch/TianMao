package com.xzkj.tianmao;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            Tool.log("请务必以玩家身份打开!", null);
            return true;
        }
        Player p = (Player) sender;
        TMHashMap hashmap = new TMHashMap();
        Api api = hashmap.getData(p);
        View view = hashmap.getDataView(p);
        if(args.length == 0){
            for (int i = 0; i < MainData.TM_Help.size(); i++) {
                Tool.log(MainData.TM_Help.get(i), p);
            }

            return true;
        }
        if (args[0].equalsIgnoreCase("openTianMao")){
            view.openTianMaoShop(p);
            return true;
        }
        if (args[0].equalsIgnoreCase("open")){
            view.openPlayerShop(p, Bukkit.getPlayer(args[1]));
            return true;
        }
        if (args[0].equalsIgnoreCase("newShop")){
            api.setPlayerShop(p, args[1]);
            return true;
        }
        if (args[0].equalsIgnoreCase("newShopSale")){
            api.setShopSale(p, args[1]);
            return true;
        }

        return false;
    }
}
