package tech.thatgravyboat.duckling.platform.services;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface IClientHelper {

    <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory);
}
