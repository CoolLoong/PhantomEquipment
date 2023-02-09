package cn.coolloong.Item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

/**
 * The type My pickaxe.
 */
public class Pickaxe extends ItemCustomTool {
    public Pickaxe() {
        super("yes:phantom_pickaxe", "Phantom Pickaxe\n§l§r", "phantom_pickaxe");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 780)
                .addRepairItemName("minecraft:phantom_membrane", 260)
                .addRepairItemName("minecraft:bone", 98)
                .addRepairItemName("yes:phantom_pickaxe", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .speed(11)
                .creativeGroup("itemGroup.name.pickaxe")
                .allowOffHand(true)
                .handEquipped(true)
                .build();
    }

    @Override
    public int getMaxDurability() {
        return 780;
    }

    @Override
    public boolean isPickaxe() {
        return true;
    }

    @Override
    public int getAttackDamage() {
        return 3;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }
}
