package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new DuckModel());
    }

    @Override
    public void renderEarly(DuckEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);

        if (animatable.isBaby())
            stackIn.scale(0.5f, 0.5f, 0.5f);
    }

    @Override
    public RenderLayer getRenderType(DuckEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
