package xyz.xynsia.miniserver;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getLogger().info("Plugin has been Enabled");
        getServer().getPluginManager().registerEvents(new PluginListener(), this);
        getCommand("mt").setExecutor(new PluginCommand());
        getCommand("heal").setExecutor(new PluginCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().warning("Plugin has been Disabled");
    }
}
