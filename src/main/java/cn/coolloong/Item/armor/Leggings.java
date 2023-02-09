package cn.coolloong.Item.armor;

import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Leggings extends ItemCustomArmor {
    public Leggings() {
        super("yes:phantom_leggings", "Phantom Leggings\n§l§r", "phantom_leggings");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .armorBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 248)
                .addRepairItemName("yes:phantom_leggings", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .addRepairItemName("minecraft:phantom_membrane", 83)
                .addRepairItemName("minecraft:bone", 31)
                .creativeGroup("itemGroup.name.leggings")
                .build();
    }

    @Override
    public boolean isLeggings() {
        return true;
    }

    @Override
    public int getTier() {
        return ItemArmor.TIER_NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 248;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public int getArmorPoints() {
        return 3;
    }
}
