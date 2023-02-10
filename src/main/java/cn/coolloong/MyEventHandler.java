package cn.coolloong;

import cn.coolloong.utils.Utils;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityArmorChangeEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import cn.nukkit.scoreboard.scorer.PlayerScorer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyEventHandler implements Listener {
    public final static Set<String> ARMOR_SERIES = Set.of("yes:phantom_helmet", "yes:phantom_chestplate", "yes:phantom_leggings", "yes:phantom_boots");

    private final static Map<Player, Integer> playerInvisibleValue = new HashMap<>();

    static {
        Server.getInstance().getScheduler().scheduleRepeatingTask(PhantomEquipment.instance, () -> {
            for (var p : Server.getInstance().getOnlinePlayers().values()) {
                if (Utils.isSeriesArmor(p, ARMOR_SERIES)) {
                    PlayerScorer playerScorer = new PlayerScorer(p);
                    playerInvisibleValue.putIfAbsent(p, 0);
                    int value = playerInvisibleValue.get(p);
                    if (!p.positionChanged) {
                        if (value < 50) {
                            playerInvisibleValue.put(p, value + 2);
                        } else if (value < 100) {
                            playerInvisibleValue.put(p, value + 1);
                        }
                        if (value == 51) {
                            PhantomEquipment.phantomInvis.addLine(playerScorer, 51);
                            Utils.slientRunCommand(false, p, "scoreboard objectives setdisplay belowname phantom_invis", "particle yes:phantom_particle ~~1~", "particle yes:phantom_landing_cloud ~~1~");
                        }
                    } else if (p.isSprinting()) {
                        p.addEffect(Effect.getEffect(Effect.INVISIBILITY).setVisible(false).setDuration(50).setAmplifier(0));
                        if (value - 50 > 0) {
                            playerInvisibleValue.put(p, value - 2);
                        } else {
                            playerInvisibleValue.put(p, 0);
                            PhantomEquipment.phantomInvis.removeLine(playerScorer);
                            p.removeEffect(Effect.INVISIBILITY);
                        }
                    } else if (value > 51 && value < 100) {
                        playerInvisibleValue.put(p, value + 1);
                    }

                    if (value > 50 && value <= 70) {
                        if (p.getEffect(Effect.INVISIBILITY) == null || p.getEffect(Effect.INVISIBILITY).getDuration() < 30) {
                            p.addEffect(Effect.getEffect(Effect.INVISIBILITY).setVisible(false).setDuration(40).setAmplifier(0));
                        }
                    } else if (value > 70 && value < 100) {
                        if (p.getEffect(Effect.INVISIBILITY) == null || p.getEffect(Effect.INVISIBILITY).getDuration() < 140) {
                            p.addEffect(Effect.getEffect(Effect.INVISIBILITY).setVisible(false).setDuration(150).setAmplifier(0));
                        }
                    } else if (value == 100) {
                        p.addEffect(Effect.getEffect(Effect.INVISIBILITY).setVisible(false).setDuration(220).setAmplifier(0));
                    }
                }
            }
        }, 2);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        PlayerScorer playerScorer = new PlayerScorer(player);
        PhantomEquipment.whipAnimation.addLine(playerScorer, 0);
        PhantomEquipment.phantomInvis.removeLine(playerScorer);
        if (player.containTag("using_whip")) {
            player.removeTag("using_whip");
        }
    }

    @EventHandler
    public void onPlayerArmorChange(EntityArmorChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            Item oldItem = event.getOldItem();
            Item newItem = event.getNewItem();
            if (ARMOR_SERIES.contains(oldItem.getNamespaceId())
                    && !ARMOR_SERIES.contains(newItem.getNamespaceId())
                    && Utils.isSeriesArmor(player, ARMOR_SERIES)) {
                PhantomEquipment.phantomInvis.removeLine(new PlayerScorer(player));
                player.removeEffect(Effect.INVISIBILITY);
            }
        }
    }
}
