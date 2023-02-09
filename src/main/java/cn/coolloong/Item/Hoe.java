package cn.coolloong.Item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Hoe extends ItemCustomTool {
    public Hoe() {
        super("yes:phantom_hoe", "Phantom Hoe\n§l§r", "phantom_hoe");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 780)
                .addRepairItemName("minecraft:phantom_membrane", 260)
                .addRepairItemName("minecraft:bone", 98)
                .addRepairItemName("yes:phantom_hoe", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .speed(12)
                .creativeGroup("itemGroup.name.hoe")
                .allowOffHand(true)
                .handEquipped(true)
                .build();
    }

    @Override
    public int getMaxDurability() {
        return 780;
    }

    @Override
    public int getAttackDamage() {
        return 3;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public boolean isHoe() {
        return true;
    }
}
