package tech.thatgravyboat.fabric.duckling.services;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import tech.thatgravyboat.duckling.platform.services.IClientHelper;

public class FabricClientService implements IClientHelper {

    @Override
    public <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererFactory<E> factory) {
        EntityRendererRegistry.register(type, factory);
    }

}
