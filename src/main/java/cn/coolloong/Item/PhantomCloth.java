package cn.coolloong.Item;

import cn.nukkit.item.customitem.CustomItemDefinition;
import cn.nukkit.item.customitem.ItemCustom;
import cn.nukkit.item.customitem.data.ItemCreativeCategory;

public class PhantomCloth extends ItemCustom {


    public PhantomCloth() {
        super("yes:phantom_cloth", "Phantom Cloth\n§l§r", "phantom_cloth");
    }

    @Override
    public CustomItemDefinition getDefinition() {
        return CustomItemDefinition.simpleBuilder(this, ItemCreativeCategory.ITEMS)
                .creativeGroup("itemGroup.name.ingot")
                .allowOffHand(false)
                .build();
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }
}
