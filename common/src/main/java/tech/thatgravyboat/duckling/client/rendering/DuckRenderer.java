package tech.thatgravyboat.duckling.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DuckModel());
    }

    @Override
    public GeoEntityRenderer<DuckEntity> withScale(float scale) {
        return super.withScale(scale);
    }

    @Override
    public void preRender(PoseStack poseStack, DuckEntity animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        this.scaleHeight = this.scaleWidth = animatable.isBaby() ? 0.5f : 1f;
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DuckEntity entity) {
        return entity.getTexture().texture;
    }

    @Override
    public RenderType getRenderType(DuckEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
