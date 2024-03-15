package net.hynse.sgi;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;


public class SchedulerUtil {

    private static Boolean IS_FOLIA = null;

    private static boolean tryFolia() {
        try {
            Bukkit.getAsyncScheduler();
            return true;
        } catch (Throwable ignored) {
        }
        return false;
    }

    public static Boolean isFolia() {
        if (IS_FOLIA == null) IS_FOLIA = tryFolia();
        return IS_FOLIA;
    }

    public static void runAtLocation(Location location, Plugin plugin, Runnable runnable) {
        // Check if Folia is available
        if (isFolia()) {
            RegionScheduler scheduler = Bukkit.getRegionScheduler();
            scheduler.run(plugin, location.getWorld(), location.getBlockX(), location.getBlockZ(), task -> {
                runnable.run();
            });
        } else {
            // Folia is not available, fall back to Bukkit scheduler
            Bukkit.getScheduler().runTask(plugin, runnable);
        }
    }
}
