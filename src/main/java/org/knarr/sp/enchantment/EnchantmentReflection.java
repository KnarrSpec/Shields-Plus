package org.knarr.sp.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import org.knarr.sp.item.ItemShieldsPlus;

public class EnchantmentReflection extends Enchantment {
    public EnchantmentReflection(){
        super(Rarity.UNCOMMON, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
    }

    @Override
    public int getMaxLevel(){
        return 4;
    }

    @Override
    public int getMinEnchantability(int level){
        return 1 + (level - 1) * 11;
    }

    @Override
    protected boolean canApplyTogether(Enchantment enchantment){
        return super.canApplyTogether(enchantment) && !(enchantment instanceof EnchantmentAbsorption);
    }

    @Override
    public boolean canApply(ItemStack item){
        return item.getItem() instanceof ItemShieldsPlus;
    }
}
