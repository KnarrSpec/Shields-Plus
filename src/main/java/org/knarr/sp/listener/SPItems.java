package org.knarr.sp.listener;

import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import org.dimdev.rift.listener.ItemAdder;

import static org.knarr.sp.listener.SPRegistry.*;
import static org.knarr.sp.utils.Reference.MOD_ID;

public class SPItems implements ItemAdder {
    @Override
    public void registerItems(){
        Item.register(new ResourceLocation(MOD_ID, "wooden_shield"), WOODEN_SHIELD);
        Item.register(new ResourceLocation(MOD_ID, "stone_shield"), STONE_SHIELD);
        Item.register(new ResourceLocation(MOD_ID, "iron_shield"), IRON_SHIELD);
        Item.register(new ResourceLocation(MOD_ID, "gold_shield"), GOLD_SHIELD);
        Item.register(new ResourceLocation(MOD_ID, "diamond_shield"), DIAMOND_SHIELD);
    }
}
