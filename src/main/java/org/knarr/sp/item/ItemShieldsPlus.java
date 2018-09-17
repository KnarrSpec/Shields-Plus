package org.knarr.sp.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.knarr.sp.enchantment.EnchantmentAbsorption;
import org.knarr.sp.enchantment.EnchantmentReflection;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ItemShieldsPlus extends ItemShield {
    public enum ShieldMaterial {WOOD, STONE, IRON, GOLD, DIAMOND};
    private ShieldMaterial material;
    private long lastUseTime = System.currentTimeMillis();
    public long lastDamage = 0;
    private final long maxTimeBetweenClicks = 500;
    private final long minTimeBetweenClicks = 100;
    public ItemShieldsPlus(Builder buidler, ShieldMaterial material){
        super(buidler);
        this.material = material;
        this.lastUseTime = 0;
    }

    public ShieldMaterial getMaterial() {
        return material;
    }

    public void addInformation(ItemStack p_addInformation_1_, @Nullable World p_addInformation_2_, List<ITextComponent> p_addInformation_3_, ITooltipFlag p_addInformation_4_) {
        ItemBanner.appendHoverTextFromTileEntityTag(p_addInformation_1_, p_addInformation_3_);
    }

    public static EnumDyeColor getColor(ItemStack p_getColor_0_) {
        return EnumDyeColor.byId(p_getColor_0_.getOrCreateSubCompound("BlockEntityTag").getInteger("Base"));
    }

    @Override
    public int getItemEnchantability() {
        switch (material){
            default:
                return ItemTier.IRON.getEnchantability();
            case WOOD:
                return ItemTier.WOOD.getEnchantability();
            case STONE:
                return ItemTier.STONE.getEnchantability();
            case IRON:
                return ItemTier.IRON.getEnchantability();
            case GOLD:
                return ItemTier.GOLD.getEnchantability();
            case DIAMOND:
                return ItemTier.DIAMOND.getEnchantability();
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World p_onItemRightClick_1_, EntityPlayer p_onItemRightClick_2_, EnumHand p_onItemRightClick_3_) {
        ActionResult<ItemStack> result = super.onItemRightClick(p_onItemRightClick_1_, p_onItemRightClick_2_, p_onItemRightClick_3_);
        long timeDiff = System.currentTimeMillis() - lastUseTime;
        if(timeDiff <= maxTimeBetweenClicks && timeDiff > minTimeBetweenClicks) {
            if (Math.abs(lastUseTime - lastDamage) > 200) {
                ItemStack p_onItemUseFinish_1_ = p_onItemRightClick_2_.getActiveItemStack();
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(p_onItemUseFinish_1_);
                for (Enchantment enchantment : enchantments.keySet()) {
                    if (enchantment instanceof EnchantmentAbsorption) {
                        float abs_lvl = ItemShieldsPlus.getAbsorptionLevel(p_onItemUseFinish_1_);
                        if (abs_lvl > 0) {
                            p_onItemRightClick_1_.createExplosion(p_onItemRightClick_2_, p_onItemRightClick_2_.posX, p_onItemRightClick_2_.posY, p_onItemRightClick_2_.posZ, abs_lvl * 2, false);
                            ItemShieldsPlus.resetAbsorptionLevel(p_onItemUseFinish_1_);
                        }
                    }
                }
            }
        } else if(timeDiff > minTimeBetweenClicks) {
            lastUseTime = System.currentTimeMillis();
        }
        return result;
    }

    public static void updateAbsorptionLevel(ItemStack shield, float increment, int level){
        float upperLimit = 4 * level;
        NBTTagCompound a = shield.getOrCreateTagCompound();
        float d = a.getFloat("damage_lvl");
        d = d + increment > upperLimit ? upperLimit : d + increment;
        a.setFloat("damage_lvl", d);
    }

    public static void resetAbsorptionLevel(ItemStack shield){
        NBTTagCompound a = shield.getOrCreateTagCompound();
        a.setFloat("damage_lvl", 0);
    }

    public static float getAbsorptionLevel(ItemStack shield){
        NBTTagCompound a = shield.getOrCreateTagCompound();
        return a.getFloat("damage_lvl");
    }
}
