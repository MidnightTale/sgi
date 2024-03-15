package net.hynse.sgi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sgi extends JavaPlugin implements Listener {
    public static Sgi instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onStructureGeneration(StructureGrowEvent event) {
        // Check if the structure generation event is about to cause a crash
        if (isPotentialCrashCondition(event)) {
            event.setCancelled(true);
            getLogger().warning("Cancelled structure generation to prevent a crash.");
        }
    }

    private boolean isPotentialCrashCondition(StructureGrowEvent event) {
        // Check if the event involves the CraftStructureTransformer
        if (!event.isFromBonemeal() && event.getBlocks().isEmpty()) {
            getLogger().warning("StructureGrowEvent with no blocks. Cancelled to prevent a crash.");
            return true;
        }
        // Check if the structure is being placed at an invalid location
        Location location = event.getLocation();
        if (location == null || location.getWorld() == null) {
            getLogger().warning("Invalid structure generation location. Cancelled to prevent a crash.");
            return true;
        }
        return false;
    }
}
