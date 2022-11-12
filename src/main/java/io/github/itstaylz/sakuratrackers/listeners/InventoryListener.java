package io.github.itstaylz.sakuratrackers.listeners;

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

public class InventoryListener implements Listener {

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
                            player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &aTracker added successfully!"));
                        } else {
                            player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &cThis item already has this tracker equipped!"));
                            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                        }
                    } else {
                        player.sendMessage(StringUtils.colorize("&7&l[&d&lSakura&b&lMC&7&l] &cThis tracker can't be applied to this item."));
                        player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                    }
                }
            }
        }
    }
}
