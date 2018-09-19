package org.knarr.sp.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import org.knarr.sp.model.ModelShieldsPlus;
import org.knarr.sp.item.ItemShieldsPlus;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static org.knarr.sp.utils.Reference.MOD_ID;

@Mixin(TileEntityItemStackRenderer.class)
public class MixinShieldRenderer {
    ModelShieldsPlus modelShieldsPlus = new ModelShieldsPlus();

    @Shadow
    @Final
    private final TileEntityBanner banner = new TileEntityBanner();

    @Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
    protected void onRenderByItem(ItemStack p_renderByItem_1_, CallbackInfo ci){
        Item lvt_2_1_ = p_renderByItem_1_.getItem();
        if(lvt_2_1_ instanceof ItemShieldsPlus){
            if (p_renderByItem_1_.getSubCompound("BlockEntityTag") != null) {
                this.banner.loadFromItemStack(p_renderByItem_1_, ItemShield.getColor(p_renderByItem_1_));
                String base_url = "";
                switch(((ItemShieldsPlus)lvt_2_1_).getMaterial()){
                    case WOOD:
                        base_url = "textures/item/shields/wooden_shield/shield_base.png";
                        break;
                    case STONE:
                        base_url = "textures/item/shields/stone_shield/shield_base.png";
                        break;
                    case IRON:
                        base_url = "textures/item/shields/iron_shield/shield_base.png";
                        break;
                    case GOLD:
                        base_url = "textures/item/shields/gold_shield/shield_base.png";
                        break;
                    case DIAMOND:
                        base_url = "textures/item/shields/diamond_shield/shield_base.png";
                        break;
                }
                BannerTextures.Cache SHIELDS_PLUS_DESIGNS = new BannerTextures.Cache("shield_", new ResourceLocation(MOD_ID,base_url), "textures/entity/shield/");
                Minecraft.getMinecraft().getTextureManager().bindTexture(SHIELDS_PLUS_DESIGNS.getResourceLocation(this.banner.getPatternResourceLocation(), this.banner.getPatternList(), this.banner.getColorList()));
            } else {
                String texture_url = "";
                switch(((ItemShieldsPlus)lvt_2_1_).getMaterial()){
                    case WOOD:
                        texture_url = "textures/item/shields/wooden_shield/shield_base_nopattern.png";
                        break;
                    case STONE:
                        texture_url = "textures/item/shields/stone_shield/shield_base_nopattern.png";
                        break;
                    case IRON:
                        texture_url = "textures/item/shields/iron_shield/shield_base_nopattern.png";
                        break;
                    case GOLD:
                        texture_url = "textures/item/shields/gold_shield/shield_base_nopattern.png";
                        break;
                    case DIAMOND:
                        texture_url = "textures/item/shields/diamond_shield/shield_base_nopattern.png";
                        break;

                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(MOD_ID, texture_url));
            }

            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0F, -1.0F, -1.0F);
            modelShieldsPlus.render();
            if (p_renderByItem_1_.hasEffect()) {
                ModelShieldsPlus var10001 = modelShieldsPlus;
                modelShieldsPlus.getClass();
                this.renderEffect(var10001::render, p_renderByItem_1_);
            }

            GlStateManager.popMatrix();
            ci.cancel();
        }
    }

    private void renderEffect(Runnable p_renderEffect_1_, ItemStack p_renderEffect_2_) {
        GlStateManager.color(0.5019608F, 0.2509804F, 0.8F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ItemRenderer.RES_ITEM_GLINT);
        ItemRenderer.renderEffect(Minecraft.getMinecraft().getTextureManager(), p_renderEffect_1_, 1);
        double alpha = Math.pow(p_renderEffect_2_.getOrCreateTagCompound().getFloat("damage_lvl")/25, 3.0);
        GlStateManager.color(1, 0, 1, (float)alpha);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(MOD_ID, "textures/misc/absorption_pat.png"));
        ItemRenderer.renderEffect(Minecraft.getMinecraft().getTextureManager(), p_renderEffect_1_, 1);
    }
}
