package com.xzkj.tianmao;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public final class Main extends JavaPlugin {

    //插件被读取

    @Override
    public void onLoad() {
        Tool.log("======TianMao======", null);
        Tool.log("=插件读取完成100%=", null);
        Tool.log("======TianMao======", null);
        saveDefaultConfig();//创建Default配置
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        Tool.log("======TianMao======", null);
        Tool.log("Hello, 我是你们的小可爱!", null);
        Tool.log("下面开始为您预加载本插件!", null);
        //请问 getDataFolder() 是在你的开发环境中不存在吗 - a39
        MainData.pluginPath = new File("./plugins", "\\TianMao");
        Tool.log("设置Plugin路径完成>>>" + MainData.pluginPath, null);
        MainData.PlayerShop = new File("./plugins/TianMao", "\\PlayerShop");
        MainData.PlayerShop.mkdirs();
        Tool.log("设置PlayerShop路径完成>>>" + MainData.PlayerShop, null);
        MainData.configFile = new File(MainData.pluginPath.getAbsolutePath() + "\\config.yml");
        Tool.log("设置Config.yml文件路径完成>>>" + MainData.configFile, null);
        Plugin placeholderAPI = Bukkit.getPluginManager().getPlugin("PlaceholderAPI");
        Plugin playerPoints = Bukkit.getPluginManager().getPlugin("PlayerPoints");
        Plugin vault = Bukkit.getPluginManager().getPlugin("Vault");
        Plugin sQLibrary = Bukkit.getPluginManager().getPlugin("SQLibrary");
        if (placeholderAPI == null) {
            Tool.log("§4(未发现)PlaceholderAPI", null);
        } else {
            PlaceholderAPI.registerPlaceholderHook("TM", new RegPapi());
            Tool.log("§2(发现)PlaceholderAPI" + "§3>>>§d[§9完成Hook§d]", null);
            Tool.log("■■■■■■■■■■■■■■■■成功注册以下变量■■■■■■■■■■■■■■■■", null);
            Tool.log("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■", null);
            Tool.log("★%TM_PlayerTianMaoName% -> 玩家店铺名称", null);
            Tool.log("★%TM_PlayerTianMaoNumberOfProducts% -> 玩家店铺商品数量", null);
            Tool.log("★%TM_PlayerTianMaoSalesVolume% -> 玩家店铺销量总价", null);
            Tool.log("★%TM_PlayerTianMaoLevel% -> 玩家店铺等级", null);
            Tool.log("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■", null);
        }
        if (playerPoints == null) {
            Tool.log("§4(未发现)playerPoints", null);
        } else {
            Tool.log("§2(发现)playerPoints" + "§3>>>§d[§9完成Hook§d]", null);
        }
        if (vault == null) {
            Tool.log("§4(未发现)vault", null);
        } else {
            Tool.setupEconomy();
            Tool.log("§2(发现)vault" + "§3>>>§d[§9完成Hook§d]", null);
            Tool.log("注册Economy完成!", null);
        }
        if (sQLibrary == null) {
            Tool.log("§4(未发现)sQLibrary", null);
        } else {
            Tool.log("§2(发现)sQLibrary" + "§3>>>§d[§9完成Hook§d]", null);

        }
        MainData.config = getConfig();
        FileConfiguration configYml = getConfig();
        MainData.TM_Create = configYml.getInt("天猫配置.创建店铺所需手续费");
        MainData.TM_Change_Img = configYml.getInt("天猫配置.更改店铺图标手续费");
        MainData.TM_Change_Name = configYml.getInt("天猫配置.更改店铺名称手续费");
        MainData.TM_Shop_Name_NO = configYml.getStringList("天猫配置.店铺名称中不可使用的字符");
        MainData.TM_Help = configYml.getStringList("help");



        Set<String> TM_Shop_Name = getConfig().getConfigurationSection("税务配置店铺等级").getKeys(false);
        for (String str : TM_Shop_Name){
            MainData.TM_Shop_Name.add(str);
        }
        for (int i = 0; i < TM_Shop_Name.size(); i++) {
            int Shop_Tax = configYml.getInt("税务配置店铺等级." + MainData.TM_Shop_Name.get(i) + ".税收");
            int Shop_Max_Commodity = configYml.getInt("税务配置店铺等级." + MainData.TM_Shop_Name.get(i) + ".最高上架商品种类");
            int Shop_Integral = configYml.getInt("税务配置店铺等级." + MainData.TM_Shop_Name.get(i) + ".激活积分");
            Boolean Shop_Ticket_Mode = configYml.getBoolean("天税务配置店铺等级." + MainData.TM_Shop_Name.get(i) + ".是否开启点券交易模式");
            MainData.TM_Shop_Tax.add(Shop_Tax);
            MainData.TM_Shop_Max_Commodity.add(Shop_Max_Commodity);
            MainData.TM_Shop_Integral.add(Shop_Integral);
            MainData.TM_Shop_Ticket_Mode.add(Shop_Ticket_Mode);
        }
        Tool.log("■■■■■■■■■■■■■下面开始读取天猫配置■■■■■■■■■■■■■■■■■■■", null);
        for (int i = 0; i < MainData.TM_Help.size(); i++) {
            Tool.log("§d★" + MainData.TM_Help.get(i), null);
        }
        Tool.log("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■", null);
        Tool.log("§d★§5创建店铺所需手续费§d★ §4-> §2" + MainData.TM_Create, null);
        Tool.log("§d★§5更改店铺图标手续费§d★ §4-> §2" + MainData.TM_Change_Img, null);
        Tool.log("§d★§5更改店铺名称手续费§d★ §4-> §2" + MainData.TM_Change_Name, null);
        Tool.log("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■", null);
        for (int i = 0; i < MainData.TM_Shop_Name_NO.size(); i++) {
            Tool.log("§d★§5不可店铺名称§d★ §4-> §2" + MainData.TM_Shop_Name_NO.get(i), null);
        }
        Tool.log("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■", null);
        for (int i = 0; i < MainData.TM_Shop_Name.size(); i++) {
            Tool.log("§d★§5店铺等级名称§d★ §4-> §2" + MainData.TM_Shop_Name.get(i), null);
            Tool.log("§d★§5税收§d★ §4-> §2" + MainData.TM_Shop_Tax.get(i), null);
            Tool.log("§d★§5最高上架商品种类★ §4-> §2" + MainData.TM_Shop_Max_Commodity.get(i), null);
            Tool.log("§d★§5激活积分§d★ §4-> §2" + MainData.TM_Shop_Integral.get(i), null);
            Tool.log("§d★§5是否开启点券交易模式§d★ §4-> §2" + MainData.TM_Shop_Ticket_Mode.get(i), null);
            Tool.log("§9★★★★★★★★★★★★★★★★★★★★★★★★", null);
        }
        Tool.log("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■", null);
        this.getCommand("TM").setExecutor(new Cmd());
        Tool.log("绑定TM命令完成!", null);
        Bukkit.getPluginManager().registerEvents(new TMListener(), this);
        Tool.log("注册TM监听完成!", null);
        Tool.log("|TianMao|作者QQ:1419158026", null);
        Tool.log("==========TianMao==========", null);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Tool.log("==========TianMao==========", null);
        Tool.log("|TianMao|作者QQ:1419158026", null);
        Tool.log("==========TianMao==========", null);
    }
}
