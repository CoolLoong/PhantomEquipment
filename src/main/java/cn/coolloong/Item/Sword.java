package cn.coolloong.Item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Sword extends ItemCustomTool {
    public Sword() {
        super("yes:phantom_sword", "Phantom Sword\n§l§r", "phantom_sword");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .speed(20)
                .addRepairItemName("yes:phantom_cloth", 112)
                .addRepairItemName("minecraft:phantom_membrane", 38)
                .addRepairItemName("minecraft:bone", 14)
                .addRepairItemName("yes:phantom_sword", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .creativeGroup("itemGroup.name.sword")
                .allowOffHand(true)
                .handEquipped(true)
                .build();
    }

    @Override
    public int getMaxDurability() {
        return 112;
    }

    @Override
    public int getAttackDamage() {
        return 9;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public boolean isSword() {
        return true;
    }
}
