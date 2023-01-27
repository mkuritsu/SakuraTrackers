package io.github.itstaylz.sakuratrackers.listeners;

import io.github.itstaylz.hexlib.utils.PDCUtils;
import io.github.itstaylz.sakuratrackers.SakuraTrackersPlugin;
import io.github.itstaylz.sakuratrackers.trackers.Tracker;
import io.github.itstaylz.sakuratrackers.trackers.TrackerManager;
import io.github.itstaylz.sakuratrackers.trackers.Trackers;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.UUID;

public class StatListener implements Listener {

    private final SakuraTrackersPlugin plugin;
    private final HashMap<UUID, Float> walkHashMap = new HashMap<>();
    private final HashMap<UUID, Float> aviateHashMap = new HashMap<>();
    private final HashMap<UUID, Float> sprintHashMap = new HashMap<>();

    public StatListener(SakuraTrackersPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        walkHashMap.put(player.getUniqueId(), player.getStatistic(Statistic.WALK_ONE_CM) / 100f);
        aviateHashMap.put(player.getUniqueId(), player.getStatistic(Statistic.AVIATE_ONE_CM) / 100f);
        sprintHashMap.put(player.getUniqueId(), player.getStatistic(Statistic.SPRINT_ONE_CM) / 100f);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        walkHashMap.remove(player.getUniqueId());
        aviateHashMap.remove(player.getUniqueId());
        sprintHashMap.remove(player.getUniqueId());
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();
        ItemStack elytra = player.getInventory().getChestplate();
        float walkStat = player.getStatistic(Statistic.WALK_ONE_CM) / 100f;
        float sprintStat = player.getStatistic(Statistic.SPRINT_ONE_CM) / 100f;
        float aviateStat = player.getStatistic(Statistic.AVIATE_ONE_CM) / 100f;
        if (!player.isGliding() && boots != null && TrackerManager.itemHasTrackerApplied(boots, Trackers.BLOCKS_WALKED)) {
            float walkDifference = walkStat - walkHashMap.get(player.getUniqueId());
            float sprintDifference = sprintStat - sprintHashMap.get(player.getUniqueId());
            float total = walkDifference + sprintDifference;
            changeTracker(boots, Trackers.BLOCKS_WALKED, total);
        } else if (player.isGliding() && elytra != null && TrackerManager.itemHasTrackerApplied(elytra, Trackers.BLOCKS_FLOWN)) {
            float flyDifference = aviateStat - aviateHashMap.get(player.getUniqueId());
            changeTracker(elytra, Trackers.BLOCKS_FLOWN, flyDifference);
        }
        walkHashMap.put(player.getUniqueId(), walkStat);
        aviateHashMap.put(player.getUniqueId(), aviateStat);
        sprintHashMap.put(player.getUniqueId(), sprintStat);
    }

    private void changeTracker(ItemStack item, Tracker tracker, float amount) {
        NamespacedKey key = new NamespacedKey(this.plugin, tracker.id());
        Float current = PDCUtils.getPDCValue(item, key, PersistentDataType.FLOAT);
        if (current != null) {
            current += amount;
            PDCUtils.setPDCValue(item, key, PersistentDataType.FLOAT, current);
            TrackerManager.refreshItemLore(item);
        }
    }


    @EventHandler
    private void onStatIncrement(PlayerStatisticIncrementEvent event) {
        Player player = event.getPlayer();
        Tracker tracker = TrackerManager.getTrackerFromStatistic(event.getStatistic());
        if (tracker == Trackers.BLOCKS_WALKED || tracker == Trackers.BLOCKS_FLOWN)
            return;
        if (tracker == Trackers.ARROWS_SHOT && event.getMaterial() != Material.BOW && event.getMaterial() != Material.CROSSBOW)
            return;
        if (tracker != null) {
            ItemStack hand = player.getInventory().getItemInMainHand();
            changeTracker(hand, tracker, event.getNewValue() - event.getPreviousValue());
        }
    }
}
