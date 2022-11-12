package io.github.itstaylz.sakuratrackers.trackers;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.utils.ItemUtils;
import io.github.itstaylz.sakuratrackers.SakuraTrackersPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class TrackerManager {

    public static final NamespacedKey TRACKER_ITEM_KEY;
    public static final NamespacedKey UNSTACKABLE_KEY;
    private static final SakuraTrackersPlugin PLUGIN;

    private static final HashMap<Statistic, Tracker> TRACKER_STATISTIC_REGISTRY = new HashMap<>();
    private static final HashMap<String, Tracker> TRACKER_ID_REGISTRY = new HashMap<>();

    static {
        PLUGIN = JavaPlugin.getPlugin(SakuraTrackersPlugin.class);
        TRACKER_ITEM_KEY = new NamespacedKey(PLUGIN, "tracker_item");
        UNSTACKABLE_KEY = new NamespacedKey(PLUGIN, "unstackable");
    }

    public static Set<String> getAllTrackersIds() {
        return TRACKER_ID_REGISTRY.keySet();
    }

    public static Tracker getTrackerFromStatistic(Statistic statistic) {
        return TRACKER_STATISTIC_REGISTRY.get(statistic);
    }

    public static Tracker getTrackerFromId(String id) {
        return TRACKER_ID_REGISTRY.get(id);
    }

    public static void registerTracker(Tracker tracker, Statistic statistic) {
        TRACKER_STATISTIC_REGISTRY.put(statistic, tracker);
        TRACKER_ID_REGISTRY.put(tracker.id(), tracker);
    }

    public static Tracker getTrackerFromTrackerItem(ItemStack item) {
        String id = ItemUtils.getPDCValue(item, TRACKER_ITEM_KEY, PersistentDataType.STRING);
        return id != null ? TRACKER_ID_REGISTRY.get(id) : null;
    }

    public static boolean itemHasTrackerApplied(ItemStack item, Tracker tracker) {
        NamespacedKey key = new NamespacedKey(PLUGIN, tracker.id());
        return ItemUtils.hasPDCValue(item, key, PersistentDataType.FLOAT);
    }

    public static void applyTrackerToItem(ItemStack item, Tracker tracker) {
        NamespacedKey key = new NamespacedKey(PLUGIN, tracker.id());
        ItemUtils.setPDCValue(item, key, PersistentDataType.FLOAT, 0f);
        new ItemBuilder(item)
                .addLore(tracker.itemLore() + " " + 0)
                .build();
    }

    public static void refreshItemLore(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;
        if (meta.getLore() == null)
            meta.setLore(new ArrayList<>());
        List<String> lore = meta.getLore();
        for (int i = 0; i < lore.size(); i++) {
            for (Tracker tracker : TRACKER_ID_REGISTRY.values()) {
                NamespacedKey key = new NamespacedKey(PLUGIN, tracker.id());
                if (lore.get(i).contains(tracker.itemLore())) {
                    Float value = ItemUtils.getPDCValue(item, key, PersistentDataType.FLOAT);
                    String valueFormatted;
                    if (tracker == Trackers.BLOCKS_FLOWN || tracker == Trackers.BLOCKS_WALKED)
                        valueFormatted = String.format("%.2f", value);
                    else
                        valueFormatted = String.format("%.0f", value);
                    lore.set(i, tracker.itemLore() + " " + valueFormatted);
                }
            }
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
    }


}
