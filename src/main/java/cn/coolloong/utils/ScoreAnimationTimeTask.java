package cn.coolloong.utils;

import cn.coolloong.PhantomEquipment;
import cn.nukkit.Player;
import cn.nukkit.scoreboard.scoreboard.IScoreboardLine;
import cn.nukkit.scoreboard.scorer.PlayerScorer;

import java.util.TimerTask;

public class ScoreAnimationTimeTask extends TimerTask {
    private final Player player;
    private final int value;

    public ScoreAnimationTimeTask(Player player, int value) {
        this.player = player;
        this.value = value;
    }

    @Override
    public void run() {
        IScoreboardLine line = PhantomEquipment.whipAnimation.getLine(new PlayerScorer(player));
        assert line != null;
        line.setScore(value);
    }
}
