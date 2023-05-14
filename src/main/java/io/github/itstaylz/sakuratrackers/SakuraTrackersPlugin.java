package io.github.itstaylz.sakuratrackers;

import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuratrackers.commands.GiveCommand;
import io.github.itstaylz.sakuratrackers.commands.MenuCommand;
import io.github.itstaylz.sakuratrackers.listeners.InventoryListener;
import io.github.itstaylz.sakuratrackers.listeners.StatListener;
import io.github.itstaylz.sakuratrackers.trackers.Trackers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraTrackersPlugin extends JavaPlugin implements CommandExecutor {

    private InventoryListener invListener;

    @Override
    public void onEnable() {
        Trackers.registerAll();
        GiveCommand giveCommand = new GiveCommand();
        getCommand("givetracker").setExecutor(giveCommand);
        getCommand("givetracker").setTabCompleter(giveCommand);
        getCommand("trackersmenu").setExecutor(new MenuCommand());
        this.invListener = new InventoryListener(this);
        Bukkit.getPluginManager().registerEvents(invListener, this);
        Bukkit.getPluginManager().registerEvents(new StatListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("trackersreload")) {
            invListener.reloadMessages();
            sender.sendMessage(StringUtils.colorize("&aTrackers have been reloaded!"));
            return true;
        }
        return false;
    }
}
