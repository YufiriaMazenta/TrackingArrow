package pers.yufiria.trackingArrow.task;

import crypticlib.scheduler.CrypticLibRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.trackingArrow.listener.TrackingHandler;

import java.util.UUID;

public class ArrowTask extends CrypticLibRunnable {

    private Long shootTime;
    private UUID arrowId;
    private UUID targetId;

    public ArrowTask(UUID arrowId, UUID targetId) {
        this.shootTime = System.currentTimeMillis();
        this.targetId = targetId;
        this.arrowId = arrowId;
    }

    @Override
    public void run() {
        Entity entity = Bukkit.getEntity(arrowId);
        if (entity == null) {
            cancel();
            return;
        }
        if (!(entity instanceof Arrow arrow)) {
            cancel();
            return;
        }
        if (targetId == null) {
            cancel();
            return;
        }
        long currentTime = System.currentTimeMillis();
        if ((currentTime - shootTime) > 20000) {
            cancel();
            return;
        }
        Entity target = Bukkit.getEntity(targetId);
        if (target == null) {
            cancel();
            return;
        }
        LivingEntity livingEntity = (LivingEntity) target;
        Location targetLoc;
        if (livingEntity instanceof EnderDragon) {
            targetLoc = livingEntity.getLocation();
        } else {
            targetLoc = livingEntity.getEyeLocation();
        }
        Vector vector = targetLoc.subtract(arrow.getLocation()).toVector().normalize();
        vector.multiply(arrow.getVelocity().length());
        arrow.setVelocity(vector);
    }

    @Override
    public void cancel() {
        TrackingHandler.INSTANCE.getArrowTaskMap().remove(arrowId);
        super.cancel();
    }
}
