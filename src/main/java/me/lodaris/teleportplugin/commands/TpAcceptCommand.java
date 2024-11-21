package me.lodaris.teleportplugin.commands;

import me.lodaris.teleportplugin.ChatUtil;
import me.lodaris.teleportplugin.TeleportRequestManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAcceptCommand implements CommandExecutor {

    private final TeleportRequestManager requestManager;

    public TpAcceptCommand(TeleportRequestManager requestManager) {
        this.requestManager = requestManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[TP] &7Только игроки могут использовать эту команду."));
            return true;
        }

        Player target = (Player) sender;
        TeleportRequestManager.TeleportRequest request =  requestManager.getRequest(target);

        if(request == null || requestManager.isRequestExpired(target)) {
            ChatUtil.sendMessage(target, "Запрос на телепортацию истек или не существует.", "info");
            return true;
        }

        Player senderPlayer = target.getServer().getPlayer(request.getSenderId());
        if(senderPlayer == null) {
            ChatUtil.sendMessage(target, "Игрок отправлявший вам запрос больше не в сети.", "info");
            requestManager.removeRequest(target);
            return true;
        }

        senderPlayer.teleport(target.getLocation());
        ChatUtil.sendMessage(target, "Вы приняли запрос на телепортацию.", "info");
        ChatUtil.sendMessage(senderPlayer, senderPlayer.getName() + " принял ваш запрос на телепортацию.", "info");
        return true;
    }
}
