package io.github.itstaylz.sakuratrackers.trackers;

import org.bukkit.Material;

public enum TrackerType {

    WEAPON("SWORD", "AXE", "BOW", "PICKAXE", "SHOVEL"),
    TOOL("PICKAXE", "AXE", "SHOVEL"),
    BOOTS("BOOTS"),
    ELYTRA("ELYTRA");

    private final String[] containsCheckString;

    TrackerType(String... containsCheckString) {
        this.containsCheckString = containsCheckString;
    }

    public boolean canBeAppliedTo(Material material) {
        for (String check : this.containsCheckString) {
            if (material.name().contains(check))
                return true;
        }
        return false;
    }
}
