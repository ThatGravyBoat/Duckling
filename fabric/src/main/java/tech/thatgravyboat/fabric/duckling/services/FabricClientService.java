package tech.thatgravyboat.fabric.duckling.services;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.duckling.platform.services.IClientHelper;

public class FabricClientService implements IClientHelper {

    @Override
    public <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory) {
        EntityRendererRegistry.register(type, factory);
    }

}
