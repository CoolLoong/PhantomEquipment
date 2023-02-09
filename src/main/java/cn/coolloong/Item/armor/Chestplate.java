package cn.coolloong.Item.armor;

import cn.nukkit.item.ItemArmor;
import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustomArmor;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class Chestplate extends ItemCustomArmor {
    public Chestplate() {
        super("yes:phantom_chestplate", "Phantom Cloak\n§l§r", "phantom_chestplate");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition
                .armorBuilder(this, ItemCreativeCategory.EQUIPMENT)
                .addRepairItemName("yes:phantom_cloth", 264)
                .addRepairItemName("yes:phantom_chestplate", "context.other->query.remaining_durability + 0.05 * context.other->query.max_durability")
                .addRepairItemName("minecraft:phantom_membrane", 88)
                .addRepairItemName("minecraft:bone", 33)
                .creativeGroup("itemGroup.name.boots")
                .creativeGroup("itemGroup.name.chestplate")
                .build();
    }

    @Override
    public boolean isChestplate() {
        return true;
    }

    @Override
    public int getTier() {
        return ItemArmor.TIER_NETHERITE;
    }

    @Override
    public int getMaxDurability() {
        return 264;
    }

    @Override
    public int getEnchantAbility() {
        return 12;
    }

    @Override
    public int getArmorPoints() {
        return 4;
    }
}
