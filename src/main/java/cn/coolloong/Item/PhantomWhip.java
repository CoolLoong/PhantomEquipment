package cn.coolloong.Item;

import cn.coolloong.PhantomEquipment;
import cn.coolloong.utils.ScoreAnimationTimeTask;
import cn.coolloong.utils.Utils;
import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;
import cn.nukkit.item.customitem.data.Offset;
import cn.nukkit.item.customitem.data.RenderOffsets;
import cn.nukkit.level.Level;
import cn.nukkit.math.*;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AnimateEntityPacket;
import cn.nukkit.network.protocol.AnimatePacket;
import cn.nukkit.scoreboard.scoreboard.IScoreboardLine;
import cn.nukkit.scoreboard.scorer.PlayerScorer;

import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;

public class PhantomWhip extends ItemCustomTool {
    public PhantomWhip() {
        super("yes:phantom_whip", """
                        Phantom Whip
                        §l§r
                              
                        §6Special Attack:
                        §e[§9+7 atk§e]
                        §e[§90.75 secs§e]
                        §e[§91.04 secs§e]
                                  """,
                "phantom_whip");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 1560)
                .addRepairItemName("yes:phantom_whip", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .addRepairItemName("minecraft:phantom_membrane", 520)
                .addRepairItemName("minecraft:bone", 196)
                .renderOffsets(new RenderOffsets(
                                Offset.builder()
                                        .position(0.8f, 0.1f, 80.0f)
                                        .rotation(11.696f, -64.536f, 79.413f)
                                        .scale(0.05f, 0.05f, 0.05f),
                                Offset.builder()
                                        .position(0.258f, 0.979f, -0.541f)
                                        .rotation(-63.268f, -43.969f, 144.041f)
                                        .scale(0.094f, 0.094f, 0.094f),
                                Offset.builder()
                                        .position(-1.053f, 0.136f, -0.803f)
                                        .rotation(27.273f, 67.731f, -64.494f)
                                        .scale(0.063f, 0.063f, 0.063f),
                                Offset.builder()
                                        .position(0.258f, 0.979f, -0.541f)
                                        .rotation(-63.268f, -43.969f, 144.041f)
                                        .scale(0.094f, 0.094f, 0.094f)
                        )
                )
                .canDestroyInCreative(false)
                .creativeGroup("itemGroup.name.sword")
                .allowOffHand(false)
                .handEquipped(true)
                .customBuild(nbt -> nbt.getCompound("components")
                        .putCompound("minecraft:cooldown", new CompoundTag()
                                .putString("category", "phantom_whip")
                                .putFloat("duration", 1.04f)));
    }

    @Override
    public int getMaxDurability() {
        return 1560;
    }

    @Override
    public int getAttackDamage() {
        return 2;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public boolean isSword() {
        return true;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        if (!player.containTag("using_whip")) {
            player.setItemCoolDown(30, "phantom_whip");
            player.addTag("using_whip");
            player.playAnimation(AnimateEntityPacket.Animation.builder()
                    .animation("animation.whip_attack")
                    .controller("__runtime_controller")
                    .stopExpression("query.any_animation_finished")
                    .stopExpressionVersion(16777216)
                    .nextState("default")
                    .blendOutTime(0f)
                    .build());
            player.playActionAnimation(AnimatePacket.Action.SWING_ARM, 0);
            IScoreboardLine line = PhantomEquipment.whipAnimation.getLine(new PlayerScorer(player));
            if (line != null) {
                line.setScore(1);
            } else {
                PhantomEquipment.whipAnimation.addLine(new PlayerScorer(player), 1);
            }
            animationTimeline(player);
            PhantomEquipment.instance.getServer().getScheduler().scheduleDelayedTask(PhantomEquipment.instance, () -> {
                player.removeTag("using_whip");
            }, 30);
            this.setDamage(this.getDamage() - 1);
            return true;
        } else return false;
    }

    private void animationTimeline(Player player) {
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 2), 220);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 3), 300);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 4), 380);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 5), 460);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 6), 540);
        PhantomEquipment.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                IScoreboardLine line = PhantomEquipment.whipAnimation.getLine(new PlayerScorer(player));
                assert line != null;
                line.setScore(7);

                Utils.slientRunCommand(true, player,
                        "particle minecraft:basic_crit_particle ^^1.8^2"
                        , "particle minecraft:basic_crit_particle ^^1.8^2"
                        , "particle minecraft:basic_crit_particle ^^1.8^4"
                        , "particle minecraft:basic_crit_particle ^^1.8^5"
                        , "playsound weapon.whip_hit_weewee @a[r=20] ^^1.8^5 1 1 0.5");

                var tmp = getVector3AroundChunkEntity(player.getLevel(), player.asBlockVector3());
                tmp.remove(player);
                var entities = tmp.stream().filter(e -> e.isAlive() && e instanceof EntityLiving).toList();

                var dec = BVector3.fromPos(player.getDirectionVector()).rotatePitch(-10);
                Vector3 start = player.getLocation().clone();

                end:
                for (int i = 1; i <= 10; i++) {
                    var end = dec.setLength(i).addToPos(player);
                    double x1, x2, y1, y2, z1, z2;
                    if (start.getX() > end.getX()) {
                        x1 = end.getX();
                        x2 = start.getX();
                    } else {
                        x1 = start.getX();
                        x2 = end.getX();
                    }
                    if (start.getY() > end.getY()) {
                        y1 = end.getY();
                        y2 = start.getY();
                    } else {
                        y1 = start.getY();
                        y2 = end.getY();
                    }
                    if (start.getZ() > end.getZ()) {
                        z1 = end.getZ();
                        z2 = start.getZ();
                    } else {
                        z1 = start.getZ();
                        z2 = end.getZ();
                    }
                    AxisAlignedBB areaAABB = new SimpleAxisAlignedBB(x1, y1, z1, x2, y2, z2);
                    areaAABB.expand(0.5, 0.5, 0.5);
                    for (var e : entities) {
                        var entityAABB = e.getBoundingBox();
                        if (areaAABB.getMaxY() >= entityAABB.getMinY() && areaAABB.getMinY() <= entityAABB.getMaxY() &&
                                areaAABB.getMaxX() >= entityAABB.getMinX() && areaAABB.getMinX() <= entityAABB.getMaxX()) {
                            if (areaAABB.getMaxZ() >= entityAABB.getMinZ() && areaAABB.getMinZ() <= entityAABB.getMaxZ()) {
                                e.attack(7);
                                e.setMotion(player.getDirectionVector());
                                break end;
                            }
                        }
                    }

                    start = end;
                }
            }
        }, 780);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 6), 940);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 5), 980);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 4), 1020);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 3), 1060);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 2), 1100);
        PhantomEquipment.timer.schedule(new ScoreAnimationTimeTask(player, 1), 1140);
        PhantomEquipment.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                IScoreboardLine line = PhantomEquipment.whipAnimation.getLine(new PlayerScorer(player));
                assert line != null;
                line.setScore(0);
                player.playAnimation(AnimateEntityPacket.Animation.builder()
                        .animation("animation.player.move.arms.stationary")
                        .controller("__runtime_controller")
                        .stopExpression("query.any_animation_finished")
                        .stopExpressionVersion(16777216)
                        .nextState("default")
                        .blendOutTime(0f)
                        .build());
            }
        }, 1460);
    }


    private Set<Entity> getVector3AroundChunkEntity(Level level, BlockVector3 vector3) {
        final int x0 = vector3.x >> 4;
        final int z0 = vector3.z >> 4;
        var chunk = level.getChunk(x0, z0);
        var entities = new HashSet<>(chunk.getEntities().values());
        entities.addAll(level.getChunkEntities(x0 + 1, z0).values());
        entities.addAll(level.getChunkEntities(x0 - 1, z0).values());
        entities.addAll(level.getChunkEntities(x0, z0 + 1).values());
        entities.addAll(level.getChunkEntities(x0, z0 - 1).values());
        entities.addAll(level.getChunkEntities(x0 + 1, z0 + 1).values());
        entities.addAll(level.getChunkEntities(x0 - 1, z0 - 1).values());
        entities.addAll(level.getChunkEntities(x0 + 1, z0 - 1).values());
        entities.addAll(level.getChunkEntities(x0 - 1, z0 + 1).values());
        return entities;
    }
}
