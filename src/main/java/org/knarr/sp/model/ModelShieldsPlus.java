package org.knarr.sp.model;

import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.client.renderer.entity.model.ModelRenderer;

public class ModelShieldsPlus extends ModelBase {
    private final ModelRenderer plate;
    private final ModelRenderer handle;

    public ModelShieldsPlus() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.plate = new ModelRenderer(this, 0, 0);
        this.plate.addBox(-6.0F, -11.0F, -2.0F, 12, 22, 1, 0.0F);
        this.handle = new ModelRenderer(this, 26, 0);
        this.handle.addBox(-1.0F, -3.0F, -1.0F, 2, 6, 6, 0.0F);
    }

    public void render() {
        this.plate.render(0.0625F);
        this.handle.render(0.0625F);
    }
}
