package tech.thatgravyboat.duckling.common.registry.fabric;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

import static tech.thatgravyboat.duckling.Duckling.modId;

public class ModEntitiesImpl {
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        var object = Registry.register(Registry.ENTITY_TYPE, modId(id), FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.scalable(width, height)).build());
        return () -> object;
    }
}
