package cn.coolloong.utils;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.InternalPlugin;

import java.util.ArrayList;
import java.util.Set;

public final class Utils {
    public static void slientRunCommand(boolean isAsync, CommandSender sender, String... cmds) {
        if (isAsync) {
            Server.getInstance().getScheduler().scheduleTask(InternalPlugin.INSTANCE, () -> {
                final var revert = new ArrayList<Level>();
                final var server = Server.getInstance();
                for (var level : server.getLevels().values()) {
                    if (level.getGameRules().getBoolean(GameRule.SEND_COMMAND_FEEDBACK)) {
                        level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
                        revert.add(level);
                    }
                }

                if (sender instanceof ConsoleCommandSender) {
                    for (var cmd : cmds) {
                        server.executeCommand(server.getConsoleSender(), cmd);
                    }
                } else {
                    for (var cmd : cmds) {
                        server.executeCommand(server.getConsoleSender(), "execute as " + "\"" + sender.getName() + "\" run " + cmd);
                    }
                }

                for (var level : revert) {
                    level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
                }
            });
        } else {
            final var revert = new ArrayList<Level>();
            final var server = Server.getInstance();
            for (var level : server.getLevels().values()) {
                if (level.getGameRules().getBoolean(GameRule.SEND_COMMAND_FEEDBACK)) {
                    level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
                    revert.add(level);
                }
            }
            if (sender instanceof ConsoleCommandSender) {
                for (var cmd : cmds) {
                    server.executeCommand(server.getConsoleSender(), cmd);
                }
            } else {
                for (var cmd : cmds) {
                    server.executeCommand(server.getConsoleSender(), "execute as " + "\"" + sender.getName() + "\" run " + cmd);
                }
            }

            for (var level : revert) {
                level.getGameRules().setGameRule(GameRule.SEND_COMMAND_FEEDBACK, true);
            }
        }
    }

    public static boolean isSeriesArmor(Player player, Set<String> series) {
        return series.contains(player.getInventory().getHelmet().getNamespaceId())
                && series.contains(player.getInventory().getChestplate().getNamespaceId())
                && series.contains(player.getInventory().getLeggings().getNamespaceId())
                && series.contains(player.getInventory().getBoots().getNamespaceId());
    }
}
