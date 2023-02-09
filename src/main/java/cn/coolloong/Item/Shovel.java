package cn.coolloong.Item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Shovel extends ItemCustomTool {
    public Shovel() {
        super("yes:phantom_shovel", "Phantom Shovel\n§l§r", "phantom_shovel");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 780)
                .addRepairItemName("minecraft:phantom_membrane", 260)
                .addRepairItemName("minecraft:bone", 98)
                .addRepairItemName("yes:phantom_shovel", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .speed(11)
                .creativeGroup("itemGroup.name.shovel")
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
        return 2;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public boolean isShovel() {
        return true;
    }
}
