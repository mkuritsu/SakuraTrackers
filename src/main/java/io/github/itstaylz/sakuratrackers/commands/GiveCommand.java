package io.github.itstaylz.sakuratrackers.commands;

import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuratrackers.trackers.Tracker;
import io.github.itstaylz.sakuratrackers.trackers.TrackerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GiveCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("givetracker")) {
            if (args.length < 2) {
                sender.sendMessage(StringUtils.colorize("&cUse /" + label + " <player> <tracker>"));
                return true;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(StringUtils.colorize("&cPlayer not found!"));
                return true;
            }
            Tracker tracker = TrackerManager.getTrackerFromId(args[1]);
            if (tracker == null) {
                sender.sendMessage(StringUtils.colorize("&cThat tracker does not exists!"));
                return true;
            }
            target.getInventory().addItem(tracker.getTrackerItem());
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("givetracker") && args.length == 2)
            return List.copyOf(TrackerManager.getAllTrackersIds());
        List<String> players = new ArrayList<>();
        for (Player online : Bukkit.getOnlinePlayers())
            players.add(online.getName());
        return players;
    }
}
