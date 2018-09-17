package org.knarr.sp.mixin.client;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.dimdev.rift.mixin.hook.client.MixinEntityPlayerSP;
import org.knarr.sp.enchantment.EnchantmentAbsorption;
import org.knarr.sp.enchantment.EnchantmentReflection;
import org.knarr.sp.item.ItemShieldsPlus;
import org.knarr.sp.listener.SPItems;
import org.knarr.sp.listener.SPRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(EntityPlayer.class)
public class MixinShieldDamage {

    @Shadow
    public InventoryPlayer inventory;


    @Inject(method="damageShield", at=@At("HEAD"))
    protected void onDamageShield(float p_damageShield_1_, CallbackInfo ci){
        if (p_damageShield_1_ >= 3.0F && inventory.player.getActiveItemStack().getItem() instanceof ItemShieldsPlus) {
            int lvt_2_1_ = 1 + MathHelper.floor(p_damageShield_1_);
            inventory.player.getActiveItemStack().damageItem(lvt_2_1_, inventory.player);

            if (inventory.player.getActiveItemStack().isEmpty()) {
                EnumHand lvt_3_1_ = inventory.player.getActiveHand();
                if (lvt_3_1_ == EnumHand.MAIN_HAND) {
                    inventory.player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                } else {
                    inventory.player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
                }

                inventory.player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + inventory.player.world.rand.nextFloat() * 0.4F);
            }
        }
    }

}
