package tech.thatgravyboat.forge.duckling.services;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import tech.thatgravyboat.duckling.platform.services.IClientHelper;

public class ForgeClientService implements IClientHelper {

    @Override
    public <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory) {
        EntityRenderers.register(type, factory);
    }
}
