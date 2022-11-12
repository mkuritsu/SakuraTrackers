package io.github.itstaylz.sakuratrackers.commands;

import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuratrackers.menu.TrackersMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("trackersmenu")) {
            if (sender instanceof Player player) {
                new TrackersMenu().open(player);
            } else {
                sender.sendMessage(StringUtils.colorize("&cOnly players can execute this command!"));
            }
            return true;
        }
        return false;
    }
}
