package pers.yufiria.trackingArrow.config;

import crypticlib.config.ConfigHandler;
import crypticlib.config.entry.StringConfigEntry;

@ConfigHandler(path = "messages.yml")
public class MessageConfig {

    public static final StringConfigEntry commandOn = new StringConfigEntry("command.on", "&e自动追踪已开启!");
    public static final StringConfigEntry commandOff = new StringConfigEntry("command.off", "&a自动追踪已关闭!");

}
