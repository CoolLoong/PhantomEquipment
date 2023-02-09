package cn.coolloong;

import cn.coolloong.utils.Utils;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.potion.Effect;
import cn.nukkit.scoreboard.scoreboard.IScoreboardLine;
import cn.nukkit.scoreboard.scorer.PlayerScorer;

public class MyEventHandler implements Listener {

    static {
        Server.getInstance().getScheduler().scheduleRepeatingTask(PhantomEquipment.instance, () -> {
            for (var p : Server.getInstance().getOnlinePlayers().values()) {
                if (Utils.isSeriesArmor(p, new String[]{"yes:phantom_helmet", "yes:phantom_chestplate", "yes:phantom_leggings", "yes:phantom_boots"})) {
                    PlayerScorer playerScorer = new PlayerScorer(p);
                    if (PhantomEquipment.phantomInvis.getLine(playerScorer) == null) {
                        PhantomEquipment.phantomInvis.addLine(playerScorer, 0);
                    }
                    IScoreboardLine line = PhantomEquipment.phantomInvis.getLine(playerScorer);
                    assert line != null;
                    if (!p.positionChanged) {
                        if (line.getScore() < 100) {
                            line.addScore(1);
                        }
                        if (line.getScore() == 51) {
                            Utils.slientRunCommand(false, p, "particle yes:phantom_particle ~~1~", "particle yes:phantom_landing_cloud ~~1~");
                        }
                    } else if (p.isSprinting()) {
                        p.addEffect(Effect.getEffect(Effect.INVISIBILITY).setVisible(false).setDuration(50).setAmplifier(0));
                        if (line.getScore() - 50 > 0) {
                            line.addScore(-2);
                        } else {
                            p.removeEffect(Effect.INVISIBILITY);
                            PhantomEquipment.phantomInvis.removeLine(playerScorer);
                        }
                    } else if (line.getScore() > 51 && line.getScore() < 100) {
                        line.addScore(1);
                    }

                    if (line.getScore() > 50 && line.getScore() <= 100) {
                        p.addEffect(Effect.getEffect(Effect.INVISIBILITY).setVisible(false).setDuration(400).setAmplifier(0));
                    }
                }
            }
        }, 2);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        var player = e.getPlayer();
        PhantomEquipment.whipAnimation.addLine(new PlayerScorer(player), 0);
        if (player.containTag("using_whip")) {
            player.removeTag("using_whip");
        }
    }
}
