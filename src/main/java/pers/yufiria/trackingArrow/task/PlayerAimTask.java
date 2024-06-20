package pers.yufiria.trackingArrow.task;

import crypticlib.scheduler.CrypticLibRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.trackingArrow.config.PluginConfig;
import pers.yufiria.trackingArrow.listener.TrackingHandler;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerAimTask extends CrypticLibRunnable {

    private final Map<UUID, UUID> playerAimMap = new ConcurrentHashMap<>();

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!TrackingHandler.INSTANCE.isEnabled(player)) {
                playerAimMap.remove(player.getUniqueId());
                continue;
            }
            RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                PluginConfig.trackingRayLength.value(), it -> !it.equals(player)
            );
//            System.out.println(rayTraceResult);
            if (rayTraceResult == null) {
                continue;
            }
            Entity hitEntity = rayTraceResult.getHitEntity();
            if (!(hitEntity instanceof LivingEntity livingEntity)) {
                continue;
            }
            playerAimMap.put(player.getUniqueId(), hitEntity.getUniqueId());
        }
        for (UUID value : playerAimMap.values()) {
            LivingEntity target = (LivingEntity) Bukkit.getEntity(value);
            if (target == null) {
                continue;
            }
            target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 30, 0));
        }
    }

    public @Nullable UUID getAimed(Player player) {
        return playerAimMap.get(player.getUniqueId());
    }

}
