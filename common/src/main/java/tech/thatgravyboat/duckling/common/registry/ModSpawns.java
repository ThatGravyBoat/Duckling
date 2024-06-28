package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.world.entity.*;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class ModSpawns {

    public static void addSpawnRules(SpawnRegistrar registrar) {
        registrar.register(ModEntities.DUCK.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canDuckSpawn);
        registrar.register(ModEntities.QUACKLING.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }

    public interface SpawnRegistrar {
        <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate);
    }
}
