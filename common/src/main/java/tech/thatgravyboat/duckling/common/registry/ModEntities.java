package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tech.thatgravyboat.duckling.common.entity.DuckEggEntity;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;
import tech.thatgravyboat.duckling.platform.CommonServices;

import java.util.function.Supplier;

public class ModEntities {

    public static final Supplier<EntityType<DuckEntity>> DUCK = CommonServices.REGISTRY.registerEntity("duck",
            DuckEntity::new, MobCategory.WATER_AMBIENT, 0.7F, 0.6F);
    public static final Supplier<EntityType<QuacklingEntity>> QUACKLING = CommonServices.REGISTRY.registerEntity("quackling",
            QuacklingEntity::new, MobCategory.CREATURE, 1.5f, 0.75f);
    public static final Supplier<EntityType<DuckEggEntity>> DUCK_EGG = CommonServices.REGISTRY.registerEntity("duck_egg",
            DuckEggEntity::new, MobCategory.MISC, 0.25f, 0.25f);

    public static void register() {
        //Initialize class.
    }
}
