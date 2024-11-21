package me.lodaris.teleportplugin;

import me.lodaris.teleportplugin.commands.TPcommand;
import me.lodaris.teleportplugin.commands.TpAcceptCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeleportPlugin extends JavaPlugin {

    private static TeleportPlugin instance;

    public static TeleportPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        TeleportRequestManager requestManager = new TeleportRequestManager();
        getCommand("tpa").setExecutor(new TPcommand(requestManager));
        getCommand("tpaccept").setExecutor(new TpAcceptCommand(requestManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
