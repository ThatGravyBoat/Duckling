package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class QuacklingRenderer extends GeoEntityRenderer<QuacklingEntity> {

    public QuacklingRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new QuacklingModel());
    }

    private ItemStack fishingRod;

    @Override
    public void renderEarly(QuacklingEntity animatable, MatrixStack stackIn, float ticks, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        fishingRod = animatable.getDataTracker().get(QuacklingEntity.FISHING_ROD);
    }

    @Override
    public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.name.equals("rod") && !fishingRod.isEmpty() && bone.getPositionY() == 13 && bone.getPositionZ() == -12) {
            stack.push();
            stack.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion(180));

            stack.translate(0, 0.7, 0.75);
            stack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(30));

            MinecraftClient.getInstance().getItemRenderer().renderItem(fishingRod,
                    ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);

            stack.pop();

            bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public RenderLayer getRenderType(QuacklingEntity animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }
}
