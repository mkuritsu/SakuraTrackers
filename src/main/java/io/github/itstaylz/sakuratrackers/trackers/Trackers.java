package io.github.itstaylz.sakuratrackers.trackers;

import io.github.itstaylz.hexlib.utils.StringUtils;
import org.bukkit.Statistic;

import java.util.List;

public class Trackers {

    private static final String TRACKER_PREFIX = "&7&l[&d&lSakura&b&lTracker&7&l] ";

    public static final Tracker PLAYERS_KILLED = new Tracker("players_killed",
            TrackerType.WEAPON,
            StringUtils.colorize(TRACKER_PREFIX + "&4&lPvP Tracker"),
            List.of(StringUtils.colorize("&7&oTracks how many players you've killed!")),
            StringUtils.colorize("&cPlayers Killed:&f")
            );

    public static final Tracker MOBS_KILLED = new Tracker("mobs_killed",
            TrackerType.WEAPON,
            StringUtils.colorize(TRACKER_PREFIX + "&d&lMobs Slayer"),
            List.of(StringUtils.colorize("&7&oTracks how many mobs you've slain!")),
            StringUtils.colorize("&dMobs Killed:&f")
    );

    public static final Tracker ARROWS_SHOT = new Tracker("arrows_shot",
            TrackerType.WEAPON,
            StringUtils.colorize(TRACKER_PREFIX + "&d&lArrows Shot"),
            List.of(StringUtils.colorize("&7&oTracks how many arrows you've shot!")),
            StringUtils.colorize("&dArrows Shot:&f")
    );

    public static final Tracker DAMAGE_DEALT = new Tracker("damage_dealt",
            TrackerType.WEAPON,
            StringUtils.colorize(TRACKER_PREFIX + "&d&lDamage Dealt"),
            List.of(StringUtils.colorize("&7&oTracks how much damage you've dealt!")),
            StringUtils.colorize("&dDamage Dealt:&f")
    );

    public static final Tracker BLOCKS_BROKEN = new Tracker("blocks_broken",
            TrackerType.TOOL,
            StringUtils.colorize(TRACKER_PREFIX + "&d&lBlocks Broken"),
            List.of(StringUtils.colorize("&7&oTracks how many block you've broken!")),
            StringUtils.colorize("&dBlocks Broken:&f")
    );

    public static final Tracker BLOCKS_FLOWN = new Tracker("blocks_flown",
            TrackerType.ELYTRA,
            StringUtils.colorize(TRACKER_PREFIX + "&d&lBlocks Flown"),
            List.of(StringUtils.colorize("&7&oTracks how many block you've flown!")),
            StringUtils.colorize("&dBlocks Flown:&f")
    );

    public static final Tracker BLOCKS_WALKED = new Tracker("blocks_walked",
            TrackerType.BOOTS,
            StringUtils.colorize(TRACKER_PREFIX + "&d&lBlocks Walked"),
            List.of(StringUtils.colorize("&7&oTracks how many block you've walked!")),
            StringUtils.colorize("&dBlocks Walked:&f")
    );

    public static void registerAll() {
        TrackerManager.registerTracker(PLAYERS_KILLED, Statistic.PLAYER_KILLS);
        TrackerManager.registerTracker(MOBS_KILLED, Statistic.MOB_KILLS);
        TrackerManager.registerTracker(ARROWS_SHOT, Statistic.USE_ITEM);
        TrackerManager.registerTracker(DAMAGE_DEALT, Statistic.DAMAGE_DEALT);
        TrackerManager.registerTracker(BLOCKS_BROKEN, Statistic.MINE_BLOCK);
        TrackerManager.registerTracker(BLOCKS_FLOWN, Statistic.AVIATE_ONE_CM);
        TrackerManager.registerTracker(BLOCKS_WALKED, Statistic.WALK_ONE_CM);
    }
}
