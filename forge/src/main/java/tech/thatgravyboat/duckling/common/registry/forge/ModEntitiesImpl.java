package tech.thatgravyboat.duckling.common.registry.forge;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.duckling.Duckling;

import java.util.function.Supplier;

public class ModEntitiesImpl {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Duckling.MODID);

    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(factory, category).sized(width, height).build(id));
    }
}
