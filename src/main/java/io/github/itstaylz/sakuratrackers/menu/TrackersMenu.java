package io.github.itstaylz.sakuratrackers.menu;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.menu.Menu;
import io.github.itstaylz.hexlib.menu.MenuSettings;
import io.github.itstaylz.hexlib.menu.components.Button;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuratrackers.trackers.Tracker;
import io.github.itstaylz.sakuratrackers.trackers.TrackerManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TrackersMenu extends Menu {

    private static final MenuSettings SETTINGS = MenuSettings.builder()
            .withNumberOfRows(5)
            .withTitle(StringUtils.colorize("&9RUNES &7- &cAdmin Menu"))
            .build();

    private static final ItemStack BACK_ARROW = new ItemBuilder(Material.ARROW)
            .setDisplayName(StringUtils.colorize("&ePrevious Page"))
            .build();

    private static final ItemStack NEXT_ARROW = new ItemBuilder(Material.ARROW)
            .setDisplayName(StringUtils.colorize("&eNext Page"))
            .build();

    private final List<Tracker> trackers = new ArrayList<>();

    private final int amountOfPages;

    public TrackersMenu() {
        super(SETTINGS);
        for (String trackerId : TrackerManager.getAllTrackersIds())
            this.trackers.add(TrackerManager.getTrackerFromId(trackerId));
        this.amountOfPages = (int) Math.ceil(this.trackers.size() / 21.0);
        openPage(0);
    }

    private void openPage(int page) {
        getInventory().clear();
        int index = page * 21;
        for (int i = 10; i < 35 && index < this.trackers.size(); i++) {
            if (i == 17 || i == 18 || i == 26 || i == 27)
                continue;
            Tracker tracker = this.trackers.get(index);
            setComponent(i, new Button(tracker.getTrackerItem(), ((menu, player, event) -> {
                player.getInventory().addItem(tracker.getTrackerItem());
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
            })));
            index++;
        }
        if (page > 0)
            setComponent(18, new Button(BACK_ARROW, (menu, player, event) -> {
                openPage(page - 1);
            }));
        if (page + 1 < this.amountOfPages)
            setComponent(26, new Button(NEXT_ARROW, (menu, player, event) -> {
                openPage(page + 1);
            }));
    }
}
