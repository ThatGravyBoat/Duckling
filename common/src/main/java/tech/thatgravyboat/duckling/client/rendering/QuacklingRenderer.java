package tech.thatgravyboat.duckling.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class QuacklingRenderer extends GeoEntityRenderer<QuacklingEntity> {

    public QuacklingRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new QuacklingModel());
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull QuacklingEntity entity) {
        return this.modelProvider.getTextureResource(entity);
    }

    private ItemStack fishingRod;

    @Override
    public void renderEarly(QuacklingEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
        fishingRod = animatable.getEntityData().get(QuacklingEntity.FISHING_ROD);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.name.equals("rod") && !fishingRod.isEmpty() && bone.getPositionY() == 13 && bone.getPositionZ() == -12) {
            stack.pushPose();
            stack.mulPose(Vector3f.YN.rotationDegrees(180));

            stack.translate(0, 0.7, 0.75);
            stack.mulPose(Vector3f.XN.rotationDegrees(30));

            Minecraft.getInstance().getItemRenderer().renderStatic(fishingRod,
                    ItemTransforms.TransformType.FIRST_PERSON_LEFT_HAND, packedLightIn, packedOverlayIn, stack, this.rtb, 0);

            stack.popPose();

            bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(QuacklingEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }
}
