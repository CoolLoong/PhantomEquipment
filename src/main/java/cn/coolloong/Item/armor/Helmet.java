package cn.coolloong.Item.armor;

import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Helmet extends ItemCustomArmor {
    public Helmet() {
        super("yes:phantom_helmet", "Phantom Hood\n§l§r", "phantom_helmet");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .armorBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 182)
                .addRepairItemName("yes:phantom_helmet", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .addRepairItemName("minecraft:phantom_membrane", 61)
                .addRepairItemName("minecraft:bone", 23)
                .creativeGroup("itemGroup.name.helmet")
                .build();
    }

    @Override
    public boolean isHelmet() {
        return true;
    }

    @Override
    public int getTier() {
        return ItemArmor.TIER_NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 182;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public int getArmorPoints() {
        return 2;
    }
}
