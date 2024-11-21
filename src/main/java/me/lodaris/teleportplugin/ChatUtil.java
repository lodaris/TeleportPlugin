package me.lodaris.teleportplugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {

    private static final String prefix = "[TP] ";

    public static void sendMessage(Player player, String msg, String msgtype) {
        if(msgtype.equals("error")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&4" + prefix+ "&7" + msg));
        } else if(msgtype.equals("success")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&2" + prefix+ "&7" + msg));
        } else if(msgtype.equals("info")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&6" + prefix+ "&7" + msg));
        }
    }
}
