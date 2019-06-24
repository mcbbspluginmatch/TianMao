package com.xzkj.tianmao;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class TMHashMap {
    public static HashMap<String, Api> apiData = new HashMap<String, Api>();
    public static HashMap<String, View> viewData = new HashMap<String, View>();
    public Api getData(Player player) {
        if (!apiData.containsKey(player.getName())) {
            apiData.put(player.getName(), new Api());
            return apiData.get(player.getName());
        } else {
            return apiData.get(player.getName());
        }
    }
    public View getDataView(Player player) {
        if (!viewData.containsKey(player.getName())) {
            viewData.put(player.getName(), new View());
            return viewData.get(player.getName());
        } else {
            return viewData.get(player.getName());
        }
    }
}
