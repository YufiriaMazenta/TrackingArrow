package pers.yufiria.trackingArrow.command;

import crypticlib.chat.MsgSender;
import crypticlib.command.CommandHandler;
import crypticlib.command.CommandInfo;
import crypticlib.command.SubcommandHandler;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.yufiria.trackingArrow.TrackingArrowBukkit;
import pers.yufiria.trackingArrow.listener.TrackingHandler;

import java.util.List;

@Command
public class TrackingArrowCommand extends CommandHandler {

    public TrackingArrowCommand() {
        super(new CommandInfo("trackingarrow", new PermInfo("trackingarrow.command"), new String[] { "ta" }));
    }

    @Subcommand
    SubcommandHandler enable = new SubcommandHandler("enable") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return true;
            }
            TrackingHandler.INSTANCE.enable(player);
            MsgSender.sendMsg(player, "Arrow tracking enabled!");
            return true;
        }
    };

    @Subcommand
    SubcommandHandler disable = new SubcommandHandler("disable") {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            if (!(sender instanceof Player player)) {
                return true;
            }
            TrackingHandler.INSTANCE.disable(player);
            MsgSender.sendMsg(player, "Arrow tracking disabled!");
            return true;
        }
    };

    @Subcommand
    SubcommandHandler reload = new SubcommandHandler("reload", new PermInfo("trackingarrow.command.reload")) {
        @Override
        public boolean execute(@NotNull CommandSender sender, @NotNull List<String> args) {
            TrackingArrowBukkit.INSTANCE.reloadConfig();
            MsgSender.sendMsg(sender, "[TrackingArrow] Reloaded");
            return true;
        }
    };

}
