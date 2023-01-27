package io.github.itstaylz.sakuratrackers.trackers;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.utils.PDCUtils;
import io.github.itstaylz.hexlib.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

public record Tracker(String id, TrackerType type, String displayName, List<String> trackerItemLore, String itemLore) {

    public ItemStack getTrackerItem() {
        ItemStack item = new ItemBuilder(Material.COMPASS)
                .setDisplayName(this.displayName)
                .addEnchantment(Enchantment.DURABILITY, 1)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .setLore(
                        StringUtils.colorize("&7" + getTypeName() + " Tracker"),
                        "")
                .addLore(trackerItemLore.toArray(new String[1]))
                .addLore(
                        "",
                        StringUtils.colorize("&7Drag n' Drop to apply to item!")
                        )
                .build();
        PDCUtils.setPDCValue(item, TrackerManager.TRACKER_ITEM_KEY, PersistentDataType.STRING, this.id);
        PDCUtils.setPDCValue(item, TrackerManager.UNSTACKABLE_KEY, PersistentDataType.STRING, UUID.randomUUID().toString());
        return item;
    }

    private String getTypeName() {
        String typeName = type.name().toLowerCase();
        char firstChar = Character.toUpperCase(typeName.charAt(0));
        return typeName.replaceFirst(typeName.charAt(0) + "", firstChar + "");
    }
}
