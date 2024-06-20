package pers.yufiria.trackingArrow.listener;

import crypticlib.listener.BukkitListener;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import pers.yufiria.trackingArrow.TrackingArrowBukkit;
import pers.yufiria.trackingArrow.config.PluginConfig;
import pers.yufiria.trackingArrow.task.ArrowTask;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@BukkitListener
public enum TrackingHandler implements Listener {

    INSTANCE;

    private final Map<UUID, Boolean> enableMap = new ConcurrentHashMap<>();
    private final Map<UUID, ArrowTask> arrowTaskMap = new ConcurrentHashMap<>();

    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!isEnabled(player)) {
            return;
        }
        Arrow arrow = (Arrow) event.getProjectile();
        UUID targetUUID = TrackingArrowBukkit.INSTANCE.getPlayerAimTask().getAimed(player);
        if (targetUUID == null) {
            for (Entity nearbyEntity : player.getNearbyEntities(50, 50, 50)) {
                if (nearbyEntity.equals(player)) {
                    continue;
                }
                if (nearbyEntity instanceof LivingEntity) {
                    targetUUID = nearbyEntity.getUniqueId();
                    break;
                }
            }
        }
        if (targetUUID == null) {
            return;
        }

        ArrowTask arrowTask = new ArrowTask(arrow.getUniqueId(), targetUUID);
        arrowTaskMap.put(arrow.getUniqueId(), arrowTask);
        arrowTask.runTaskTimer(
            TrackingArrowBukkit.INSTANCE,
            PluginConfig.trackingDelay.value(),
            PluginConfig.trackingPeriod.value()
        );
    }

    public void enable(Player player) {
        enableMap.put(player.getUniqueId(), true);
    }

    public void disable(Player player) {
        enableMap.remove(player.getUniqueId());
    }

    public boolean isEnabled(Player player) {
        return enableMap.containsKey(player.getUniqueId());
    }

    public Map<UUID, ArrowTask> getArrowTaskMap() {
        return arrowTaskMap;
    }

}
