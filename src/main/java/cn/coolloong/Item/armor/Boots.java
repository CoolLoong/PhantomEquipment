package cn.coolloong.Item.armor;

import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Boots extends ItemCustomArmor {
    public Boots() {
        super("yes:phantom_boots", "Phantom Boots\n§l§r", "phantom_boots");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .armorBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 215)
                .addRepairItemName("yes:phantom_boots", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .addRepairItemName("minecraft:phantom_membrane", 72)
                .addRepairItemName("minecraft:bone", 27)
                .creativeGroup("itemGroup.name.boots")
                .build();
    }

    @Override
    public boolean isBoots() {
        return true;
    }

    @Override
    public int getTier() {
        return ItemArmor.TIER_NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 215;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public int getArmorPoints() {
        return 1;
    }
}
