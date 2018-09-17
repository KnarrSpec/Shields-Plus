package org.knarr.sp.mixin.client;

import net.minecraft.enchantment.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.knarr.sp.enchantment.EnchantmentAbsorption;
import org.knarr.sp.enchantment.EnchantmentReflection;
import org.knarr.sp.item.ItemShieldsPlus;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.UUID;

@Mixin(EntityLivingBase.class)
public class MixinShieldEnchants {

    @Shadow
    protected ItemStack activeItemStack;


    @Inject(method="attackEntityFrom", at=@At(value="INVOKE", target="Lnet/minecraft/entity/EntityLivingBase;damageShield(F)V"))
    protected void beforeBlockUsingShield(DamageSource p_attackEntityFrom_1_, float p_attackEntityFrom_2_, CallbackInfoReturnable ci){
        Entity damageEntity = p_attackEntityFrom_1_.getTrueSource();
        if(damageEntity instanceof EntityLivingBase) {
            if(activeItemStack.getItem() instanceof ItemShieldsPlus){
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(activeItemStack);
                int reflectionLevel = 1;
                int absorptionLevel = 1;
                boolean hasAbsorption = false;
                boolean hasReflection = false;
                for(Enchantment enchantment : enchantments.keySet()){
                    if(enchantment instanceof EnchantmentReflection){
                        reflectionLevel = enchantments.get(enchantment);
                        hasReflection = true;
                    }
                    if(enchantment instanceof EnchantmentAbsorption){
                        absorptionLevel = enchantments.get(enchantment);
                        hasAbsorption = true;
                    }
                    if(hasAbsorption && hasReflection){
                        break;
                    }
                }
                if(hasReflection){
                    float damage_coefficient = (15 + 20 * reflectionLevel) / 100F;
                    damageEntity.attackEntityFrom(DamageSource.MAGIC, p_attackEntityFrom_2_ * damage_coefficient);
                }

                if(hasAbsorption){
                    ((ItemShieldsPlus) activeItemStack.getItem()).lastDamage = System.currentTimeMillis();
                    float damage_coefficient = (10 + 20 * absorptionLevel) / 100F;
                    float damage_ =  p_attackEntityFrom_2_ * damage_coefficient;
                    ItemShieldsPlus.updateAbsorptionLevel(activeItemStack, damage_, absorptionLevel);
                }
            }
        }
    }
}
