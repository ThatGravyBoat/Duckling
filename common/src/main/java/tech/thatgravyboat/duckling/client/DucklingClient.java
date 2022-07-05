package tech.thatgravyboat.duckling.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.duckling.client.rendering.DuckRenderer;
import tech.thatgravyboat.duckling.client.rendering.QuacklingRenderer;
import tech.thatgravyboat.duckling.common.registry.ModEntities;

public class DucklingClient {

    public static void init() {
        registerEntityRenderer(ModEntities.DUCK.get(), DuckRenderer::new);
        registerEntityRenderer(ModEntities.QUACKLING.get(), QuacklingRenderer::new);
        registerEntityRenderer(ModEntities.DUCK_EGG.get(), ThrownItemRenderer::new);
    }

    @ExpectPlatform
    public static <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory) {
        throw new AssertionError();
    }
}
