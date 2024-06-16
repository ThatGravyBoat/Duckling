package tech.thatgravyboat.duckling.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class QuacklingRenderer extends GeoEntityRenderer<QuacklingEntity> {

    private ItemStack fishingRod;

    public QuacklingRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new QuacklingModel());
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull QuacklingEntity entity) {
        return this.model.getTextureResource(entity);
    }

    @Override
    public void preRender(PoseStack poseStack, QuacklingEntity animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        this.fishingRod = animatable.getEntityData().get(QuacklingEntity.FISHING_ROD);
    }

    @Override
    public void renderRecursively(PoseStack stack, QuacklingEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (bone.getName().equals("rod") && !fishingRod.isEmpty() && bone.getPosY() == 13 && bone.getPosZ() == -12) {
            stack.pushPose();
            stack.mulPose(Axis.YN.rotationDegrees(180));

            stack.translate(0, 0.7, 0.75);
            stack.mulPose(Axis.XN.rotationDegrees(30));

            Minecraft.getInstance().getItemRenderer().renderStatic(fishingRod, ItemDisplayContext.FIRST_PERSON_LEFT_HAND, packedLight, packedOverlay, stack, bufferSource, animatable.level(), 0);

            stack.popPose();
        }
        super.renderRecursively(stack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    public RenderType getRenderType(QuacklingEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
