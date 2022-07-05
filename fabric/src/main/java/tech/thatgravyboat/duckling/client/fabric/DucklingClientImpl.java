package tech.thatgravyboat.duckling.client.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class DucklingClientImpl {
    public static <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory) {
        EntityRendererRegistry.register(type, factory);
    }
}
