package pers.yufiria.trackingArrow;

import crypticlib.BukkitPlugin;
import pers.yufiria.trackingArrow.task.ArrowTask;
import pers.yufiria.trackingArrow.task.PlayerAimTask;

public class TrackingArrowBukkit extends BukkitPlugin {

    public static TrackingArrowBukkit INSTANCE;
    private PlayerAimTask playerAimTask;

    @Override
    public void enable() {
        INSTANCE = this;
        playerAimTask = new PlayerAimTask();
        playerAimTask.runTaskTimer(this, 20, 5);
    }

    @Override
    public void disable() {

    }

    public PlayerAimTask getPlayerAimTask() {
        return playerAimTask;
    }


}