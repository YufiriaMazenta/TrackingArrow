package pers.yufiria.trackingArrow.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.entry.IntConfigEntry;
import crypticlib.config.entry.LongConfigEntry;

@ConfigHandler(path = "config.yml")
public class PluginConfig {

    public static final LongConfigEntry trackingDelay = new LongConfigEntry("tracking-delay", 20L);
    public static final LongConfigEntry trackingPeriod = new LongConfigEntry("tracking-period", 5L);
    public static final IntConfigEntry trackingRayLength = new IntConfigEntry("tracking-length", 50);

}
