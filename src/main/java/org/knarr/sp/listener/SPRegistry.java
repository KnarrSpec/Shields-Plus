package org.knarr.sp.listener;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.knarr.sp.item.ItemShieldsPlus;

public class SPRegistry {
    public final static Item WOODEN_SHIELD = new ItemShieldsPlus(new Item.Builder().maxDamage(250).group(ItemGroup.COMBAT), ItemShieldsPlus.ShieldMaterial.WOOD);
    public final static Item STONE_SHIELD = new ItemShieldsPlus(new Item.Builder().maxDamage(400).group(ItemGroup.COMBAT), ItemShieldsPlus.ShieldMaterial.STONE);
    public final static Item IRON_SHIELD = new ItemShieldsPlus(new Item.Builder().maxDamage(800).group(ItemGroup.COMBAT), ItemShieldsPlus.ShieldMaterial.IRON);
    public final static Item GOLD_SHIELD = new ItemShieldsPlus(new Item.Builder().maxDamage(150).group(ItemGroup.COMBAT), ItemShieldsPlus.ShieldMaterial.GOLD);
    public final static Item DIAMOND_SHIELD = new ItemShieldsPlus(new Item.Builder().maxDamage(1000).group(ItemGroup.COMBAT), ItemShieldsPlus.ShieldMaterial.DIAMOND);

}
