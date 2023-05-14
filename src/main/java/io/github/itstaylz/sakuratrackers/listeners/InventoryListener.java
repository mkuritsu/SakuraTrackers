package io.github.itstaylz.sakuratrackers.listeners;

import io.github.itstaylz.hexlib.storage.file.YamlFile;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuratrackers.trackers.Tracker;
import io.github.itstaylz.sakuratrackers.trackers.TrackerManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class InventoryListener implements Listener {

    private String trackerAddedMessage, trackerAlreadyEquippedMessage, trackerInvalidItemMessage;
    private YamlFile messagesFile;

    public InventoryListener(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        this.messagesFile = new YamlFile(new File(plugin.getDataFolder(), "config.yml"));
        reloadMessages();
    }

    public void reloadMessages() {
        this.trackerAddedMessage = messagesFile.getConfig().getString("tracker_add_message");
        this.trackerAlreadyEquippedMessage = messagesFile.getConfig().getString("tracker_already_equipped_message");
        this.trackerInvalidItemMessage = messagesFile.getConfig().getString("tracker_invalid_item_message");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player && player.getInventory().getViewers().contains(player) && player.getGameMode() != GameMode.CREATIVE) {
            ItemStack cursor = event.getCursor();
            ItemStack clicked = event.getCurrentItem();
            if (clicked != null && cursor != null && clicked.getType() != Material.AIR) {
                Tracker tracker = TrackerManager.getTrackerFromTrackerItem(cursor);
                if (tracker != null) {
                    event.setCancelled(true);
                    if (tracker.type().canBeAppliedTo(clicked.getType())) {
                        if (!TrackerManager.itemHasTrackerApplied(clicked, tracker)) {
                            player.setItemOnCursor(null);
                            TrackerManager.applyTrackerToItem(clicked, tracker);
                            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                            player.sendMessage(StringUtils.colorize(this.trackerAddedMessage));
                        } else {
                            player.sendMessage(StringUtils.colorize(this.trackerAlreadyEquippedMessage));
                            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        }
                    } else {
                        player.sendMessage(StringUtils.colorize(this.trackerInvalidItemMessage));
                        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    }
                }
            }
        }
    }
}
