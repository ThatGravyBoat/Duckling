package tech.thatgravyboat.forge.duckling.services;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import tech.thatgravyboat.duckling.platform.services.IClientHelper;

public class ForgeClientService implements IClientHelper {

    @Override
    public <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererFactory<E> factory) {
        EntityRenderers.register(type, factory);
    }
}
