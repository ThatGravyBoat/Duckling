package tech.thatgravyboat.duckling.common.registry;

import com.google.common.collect.ImmutableSet;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.entity.DuckEggEntity;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class ModEntities {

    public static final ResourcefulRegistry<EntityType<?>> ENTITIES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, Duckling.MODID);

    public static final RegistryEntry<EntityType<DuckEntity>> DUCK = ENTITIES.register("duck",
            () -> createType(DuckEntity::new, MobCategory.WATER_AMBIENT,0.7F, 0.6F));

    public static final RegistryEntry<EntityType<QuacklingEntity>> QUACKLING = ENTITIES.register("quackling",
            () -> createType(QuacklingEntity::new, MobCategory.CREATURE,0.75f, 1.5f));

    public static final RegistryEntry<EntityType<DuckEggEntity>> DUCK_EGG = ENTITIES.register("duck_egg",
            () -> createType(DuckEggEntity::new, MobCategory.MISC, 0.25f, 0.25f));

    private static <T extends Entity> EntityType<T> createType(EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        return new EntityType<>(
                factory, category,
                true, true, false, false,
                ImmutableSet.of(), EntityDimensions.scalable(width, height),
                5, 3, FeatureFlags.VANILLA_SET
        );
    }
}
