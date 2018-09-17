package org.knarr.sp.listener;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;
import org.dimdev.rift.listener.EnchantmentAdder;
import org.knarr.sp.enchantment.EnchantmentAbsorption;
import org.knarr.sp.enchantment.EnchantmentReflection;

import static org.knarr.sp.utils.Reference.MOD_ID;

public class SPEnchantments implements EnchantmentAdder {
    public static Enchantment REFLECTION;
    public static Enchantment ABSORPTION;


    @Override
    public void registerEnchantments(){
        ABSORPTION = registerEnchantment(new ResourceLocation(MOD_ID, "absorption"), new EnchantmentAbsorption());
        REFLECTION = registerEnchantment(new ResourceLocation(MOD_ID, "reflection"), new EnchantmentReflection());
    }

    private Enchantment registerEnchantment(ResourceLocation location, Enchantment enchantment){
        Enchantment.registerEnchantment(location.toString(), enchantment);
        return Enchantment.REGISTRY.get(location);
    }

}
