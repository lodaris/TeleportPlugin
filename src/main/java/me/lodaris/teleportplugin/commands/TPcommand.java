package me.lodaris.teleportplugin.commands;

import me.lodaris.teleportplugin.ChatUtil;
import me.lodaris.teleportplugin.TeleportPlugin;
import me.lodaris.teleportplugin.TeleportRequestManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPcommand implements CommandExecutor {
    private final TeleportRequestManager requestManager;

    public TPcommand(TeleportRequestManager requestManager) {
        this.requestManager = requestManager;
    }
    @Override

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length != 1) {
                ChatUtil.sendMessage(p, "Команда введена неверно! Правильное использование: /tpa [nickname].","error");
                return true;
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null){
                    ChatUtil.sendMessage(p, "Игрок не найден.", "error");
                    return true;
                }
                if(!target.equals(p)) {
                    requestManager.addRequest(p, target);
                    ChatUtil.sendMessage(p,"Запрос на телепортацию отправлен игроку " + target.getName(), "info");
                    ChatUtil.sendMessage(target,"Игрок " + p.getName() + " отправил запрос на телепортацию.", "info");
                    ChatUtil.sendMessage(target,"Чтобы принять напишите /tpaccept.", "info");

                    Bukkit.getScheduler().runTaskLaterAsynchronously(TeleportPlugin.getInstance(), () -> {
                        if(requestManager.isRequestExpired(target)) {
                            requestManager.removeRequest(target);
                            ChatUtil.sendMessage(target,"Запрос на телепортацию от " + p.getName() + " истек.", "info");
                            ChatUtil.sendMessage(p,"Ваш запрос на телепортацию к " + target.getName() + " истек.", "info");
                        }
                    }, 600L);

                } else {
                    ChatUtil.sendMessage(p,"Вы не можете отправить запрос самому себе.", "error");
                    return true;
                }
            }
        }
        return true;
    }
}
