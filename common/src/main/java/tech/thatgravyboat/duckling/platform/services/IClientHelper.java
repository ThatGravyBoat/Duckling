package tech.thatgravyboat.duckling.platform.services;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public interface IClientHelper {

    <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererFactory<E> factory);
}
