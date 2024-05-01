package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class ModSpawns {

    public static void addSpawnRules() {
        SpawnPlacements.register(ModEntities.DUCK.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canDuckSpawn);
        SpawnPlacements.register(ModEntities.QUACKLING.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
