package cn.coolloong;

import cn.coolloong.Item.*;
import cn.coolloong.Item.armor.Boots;
import cn.coolloong.Item.armor.Chestplate;
import cn.coolloong.Item.armor.Helmet;
import cn.coolloong.Item.armor.Leggings;
import cn.coolloong.utils.Utils;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.scoreboard.scoreboard.IScoreboard;
import cn.nukkit.scoreboard.scoreboard.Scoreboard;

import java.util.List;
import java.util.Timer;

public class PhantomEquipment extends PluginBase {
    public static PluginLogger log;
    public static PhantomEquipment instance;
    public static IScoreboard whipAnimation;
    public static IScoreboard phantomInvis;
    public static Timer timer;

    @Override
    public void onLoad() {
        instance = this;
        log = this.getLogger();
        whipAnimation = new Scoreboard("whip_animation", " ");
        phantomInvis = new Scoreboard("phantom_invis", " ");
        timer = new Timer("whip_animation");
        Item.registerCustomItem(
                List.of(
                        Axe.class, Hoe.class, PhantomWhip.class,
                        Pickaxe.class, Shovel.class, Sword.class,
                        Boots.class, Chestplate.class, Helmet.class,
                        Leggings.class, PhantomCloth.class
                )
        ).assertOK();
    }

    @Override
    public void onEnable() {
//        RecipeManager112.registerRecipeToServer(this, "recipes/");
        this.getServer().getPluginManager().registerEvents(new MyEventHandler(), this);
        this.getServer().getScoreboardManager().addScoreboard(whipAnimation);
        this.getServer().getScoreboardManager().addScoreboard(phantomInvis);
        Utils.slientRunCommand(false, this.getServer().getConsoleSender(),
                "scoreboard objectives setdisplay belowname phantom_invis",
                "scoreboard objectives setdisplay list whip_animation");
        log.info("PhantomEquipment running success!");
    }

    @Override
    public void onDisable() {
        this.getServer().getScoreboardManager().removeScoreboard(whipAnimation);
        this.getServer().getScoreboardManager().removeScoreboard(phantomInvis);
        timer.cancel();
    }
}
