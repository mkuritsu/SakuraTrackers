package io.github.itstaylz.sakuratrackers;

import io.github.itstaylz.sakuratrackers.commands.GiveCommand;
import io.github.itstaylz.sakuratrackers.commands.MenuCommand;
import io.github.itstaylz.sakuratrackers.listeners.InventoryListener;
import io.github.itstaylz.sakuratrackers.listeners.StatListener;
import io.github.itstaylz.sakuratrackers.trackers.Trackers;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraTrackersPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Trackers.registerAll();
        GiveCommand giveCommand = new GiveCommand();
        getCommand("givetracker").setExecutor(giveCommand);
        getCommand("givetracker").setTabCompleter(giveCommand);
        getCommand("trackersmenu").setExecutor(new MenuCommand());
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new StatListener(this), this);
    }
}
