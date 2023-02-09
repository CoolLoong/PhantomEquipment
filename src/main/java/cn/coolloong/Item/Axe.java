package cn.coolloong.Item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomTool;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Axe extends ItemCustomTool {
    public Axe() {
        super("yes:phantom_axe", "Phantom Axe\n§l§r", "phantom_axe");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .toolBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 780)
                .addRepairItemName("minecraft:phantom_membrane", 260)
                .addRepairItemName("minecraft:bone", 98)
                .addRepairItemName("yes:phantom_axe", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .speed(11)
                .creativeGroup("itemGroup.name.axe")
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
        return 4;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public boolean isAxe() {
        return true;
    }
}
