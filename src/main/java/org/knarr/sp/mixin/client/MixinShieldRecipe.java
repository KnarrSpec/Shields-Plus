package org.knarr.sp.mixin.client;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShieldRecipes;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShieldRecipes.class)
public class MixinShieldRecipe {
    @Overwrite
    public ItemStack getCraftingResult(IInventory p_getCraftingResult_1_) {
        ItemStack lvt_2_1_ = ItemStack.EMPTY;
        ItemStack lvt_3_1_ = ItemStack.EMPTY;

        for(int lvt_4_1_ = 0; lvt_4_1_ < p_getCraftingResult_1_.getSizeInventory(); ++lvt_4_1_) {
            ItemStack lvt_5_1_ = p_getCraftingResult_1_.getStackInSlot(lvt_4_1_);
            if (!lvt_5_1_.isEmpty()) {
                if (lvt_5_1_.getItem() instanceof ItemBanner) {
                    lvt_2_1_ = lvt_5_1_;
                } else if (lvt_5_1_.getItem() instanceof ItemShield) {
                    lvt_3_1_ = lvt_5_1_.copy();
                }
            }
        }

        if (lvt_3_1_.isEmpty()) {
            return lvt_3_1_;
        } else {
            NBTTagCompound lvt_4_2_ = lvt_2_1_.getSubCompound("BlockEntityTag");
            NBTTagCompound lvt_5_2_ = lvt_4_2_ == null ? new NBTTagCompound() : lvt_4_2_.copy();
            lvt_5_2_.setInteger("Base", ((ItemBanner)lvt_2_1_.getItem()).getColor().getId());
            lvt_3_1_.setTagInfo("BlockEntityTag", lvt_5_2_);
            return lvt_3_1_;
        }
    }
}
