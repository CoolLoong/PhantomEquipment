package cn.coolloong.utils;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.InternalPlugin;

import java.util.ArrayList;

public final class Utils {
    public static void slientRunCommand(boolean isAsync, CommandSender sender, String... cmds) {
        final var revert = new ArrayList<Level>();
        for (var level : sender.getServer().getLevels().values()) {
            if (level.getGameRules().getBoolean(GameRule.SEND_COMMAND_FEEDBACK)) {
                level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
                revert.add(level);
            }
        }

        if (isAsync) {
            sender.getServer().getScheduler().scheduleTask(InternalPlugin.INSTANCE, () -> {
                for (var cmd : cmds) {
                    sender.getServer().executeCommand(sender, cmd);
                }
                for (var level : revert) {
                    level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
                }
            });
        } else {
            for (var cmd : cmds) {
                sender.getServer().executeCommand(sender, cmd);
            }
            for (var level : revert) {
                level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
            }
        }
    }

    public static boolean isSeriesArmor(Player player, String[] series) {
        return player.getInventory().getHelmet().getNamespaceId().equals(series[0])
                && player.getInventory().getChestplate().getNamespaceId().equals(series[1])
                && player.getInventory().getLeggings().getNamespaceId().equals(series[2])
                && player.getInventory().getBoots().getNamespaceId().equals(series[3]);
    }
}
