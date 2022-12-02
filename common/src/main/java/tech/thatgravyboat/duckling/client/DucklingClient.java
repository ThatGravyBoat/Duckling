package tech.thatgravyboat.duckling.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import tech.thatgravyboat.duckling.client.rendering.DuckRenderer;
import tech.thatgravyboat.duckling.client.rendering.QuacklingRenderer;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;

import java.util.Calendar;
import java.util.Date;

public class DucklingClient {

    public static final boolean isDecember = Util.make(Calendar.getInstance(), cal -> cal.setTime(new Date())).get(Calendar.MONTH) == Calendar.DECEMBER;

    public static void init() {
        registerEntityRenderer(ModEntities.DUCK.get(), DuckRenderer::new);
        registerEntityRenderer(ModEntities.QUACKLING.get(), QuacklingRenderer::new);
        registerEntityRenderer(ModEntities.DUCK_EGG.get(), ThrownItemRenderer::new);

        setRenderType(ModItems.HOLIDAY_FRUIT_CAKE_BLOCK.get(), RenderType.cutout());
    }

    @ExpectPlatform
    public static <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void setRenderType(Block block, RenderType type) {
        throw new AssertionError();
    }
}
