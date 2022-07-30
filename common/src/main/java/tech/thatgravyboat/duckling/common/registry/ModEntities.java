package tech.thatgravyboat.duckling.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tech.thatgravyboat.duckling.common.entity.DuckEggEntity;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

import java.util.function.Supplier;

public class ModEntities {

    public static final Supplier<EntityType<DuckEntity>> DUCK = registerEntity("duck",
            DuckEntity::new, MobCategory.WATER_AMBIENT, 0.7F, 0.6F);
    public static final Supplier<EntityType<QuacklingEntity>> QUACKLING = registerEntity("quackling",
            QuacklingEntity::new, MobCategory.CREATURE, 0.75f, 1.5f);
    public static final Supplier<EntityType<DuckEggEntity>> DUCK_EGG = registerEntity("duck_egg",
            DuckEggEntity::new, MobCategory.MISC, 0.25f, 0.25f);

    public static void register() {
        //Initialize class.
    }

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        throw new AssertionError();
    }
}
