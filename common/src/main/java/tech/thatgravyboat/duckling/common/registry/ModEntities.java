package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import tech.thatgravyboat.duckling.common.constants.DuckVariant;
import tech.thatgravyboat.duckling.common.entity.DuckEggEntity;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;
import tech.thatgravyboat.duckling.common.entity.TrackedEnum;
import tech.thatgravyboat.duckling.platform.CommonServices;

import java.util.function.Supplier;

public class ModEntities {

    public static final TrackedDataHandler<DuckVariant> DUCK_VARIANT = new TrackedEnum<>(DuckVariant.class);
    static {
        TrackedDataHandlerRegistry.register(DUCK_VARIANT);
    }

    public static final Supplier<EntityType<DuckEntity>> DUCK = CommonServices.REGISTRY.registerEntity("duck",
            DuckEntity::new, SpawnGroup.WATER_CREATURE, 0.75f, 0.75f);
    public static final Supplier<EntityType<QuacklingEntity>> QUACKLING = CommonServices.REGISTRY.registerEntity("quackling",
            QuacklingEntity::new, SpawnGroup.CREATURE, 1.5f, 0.75f);
    public static final Supplier<EntityType<DuckEggEntity>> DUCK_EGG = CommonServices.REGISTRY.registerEntity("duck_egg",
            DuckEggEntity::new, SpawnGroup.MISC, 0.25f, 0.25f);

    public static void register() {
        //Initialize class.
    }
}
