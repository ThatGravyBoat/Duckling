package tech.thatgravyboat.duckling.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class DuckRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DuckModel());
    }

    @Override
    public void renderEarly(DuckEntity animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);

        if (animatable.isBaby())
            stackIn.scale(0.5f, 0.5f, 0.5f);
    }

    @Override
    public void render(@NotNull DuckEntity entity, float entityYaw, float partialTicks, @NotNull PoseStack stack, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
        Entity holdingEntity = entity.getLeashHolder();
        if (holdingEntity != null) {
            this.renderLeash(entity, partialTicks, stack, bufferIn, holdingEntity);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(DuckEntity entity) {
        return entity.getTexture().texture;
    }

    @Override
    public RenderType getRenderType(DuckEntity animatable, float partialTicks, PoseStack stack, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(textureLocation);
    }

    private <E extends Entity> void renderLeash(DuckEntity mob, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, E entity) {
        poseStack.pushPose();
        Vec3 vec3 = entity.getRopeHoldPosition(f);
        double d = (double)(Mth.lerp(f, mob.yBodyRotO, mob.yBodyRot) * 0.017453292F) + 1.5707963267948966D;
        Vec3 vec32 = mob.getLeashOffset();
        double e = Math.cos(d) * vec32.z + Math.sin(d) * vec32.x;
        double g = Math.sin(d) * vec32.z - Math.cos(d) * vec32.x;
        double h = Mth.lerp(f, mob.xo, mob.getX()) + e;
        double i = Mth.lerp(f, mob.yo, mob.getY()) + vec32.y;
        double j = Mth.lerp(f, mob.zo, mob.getZ()) + g;
        poseStack.translate(e, vec32.y, g);
        float k = (float)(vec3.x - h);
        float l = (float)(vec3.y - i);
        float m = (float)(vec3.z - j);
        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.leash());
        Matrix4f matrix4f = poseStack.last().pose();
        float o = Mth.fastInvSqrt(k * k + m * m) * 0.025F / 2.0F;
        float p = m * o;
        float q = k * o;
        BlockPos blockPos = new BlockPos(mob.getEyePosition(f));
        BlockPos blockPos2 = new BlockPos(entity.getEyePosition(f));
        int r = this.getBlockLightLevel(mob, blockPos);
        int s = entity.isOnFire() ? 15 : entity.level.getBrightness(LightLayer.BLOCK, blockPos2);
        int t = mob.level.getBrightness(LightLayer.SKY, blockPos);
        int u = mob.level.getBrightness(LightLayer.SKY, blockPos2);

        int v;
        for(v = 0; v <= 24; ++v) {
            addVertexPair(vertexConsumer, matrix4f, k, l, m, r, s, t, u, 0.025F, p, q, v, false);
        }

        for(v = 24; v >= 0; --v) {
            addVertexPair(vertexConsumer, matrix4f, k, l, m, r, s, t, u, 0.0F, p, q, v, true);
        }

        poseStack.popPose();
    }

    private static void addVertexPair(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, int i, int j, int k, int l, float n, float o, float p, int q, boolean bl) {
        float r = (float)q / 24.0F;
        int s = (int)Mth.lerp(r, (float)i, (float)j);
        int t = (int)Mth.lerp(r, (float)k, (float)l);
        int u = LightTexture.pack(s, t);
        float v = q % 2 == (bl ? 1 : 0) ? 0.7F : 1.0F;
        float w = 0.5F * v;
        float x = 0.4F * v;
        float y = 0.3F * v;
        float z = f * r;
        float aa = g > 0.0F ? g * r * r : g - g * (1.0F - r) * (1.0F - r);
        float ab = h * r;
        vertexConsumer.vertex(matrix4f, z - o, aa + n, ab + p).color(w, x, y, 1.0F).uv2(u).endVertex();
        vertexConsumer.vertex(matrix4f, z + o, aa + (float) 0.025 - n, ab - p).color(w, x, y, 1.0F).uv2(u).endVertex();
    }
}
