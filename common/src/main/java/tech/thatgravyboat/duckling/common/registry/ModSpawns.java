package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.duckling.platform.CommonServices;
import tech.thatgravyboat.duckling.platform.SpawnData;

public class ModSpawns {

    public static void addSpawns() {
        CommonServices.REGISTRY.addEntityToBiome(BiomeTags.IS_RIVER,
                new SpawnData(ModEntities.DUCK.get(), MobCategory.WATER_AMBIENT, 5, 3, 4, precipitation -> !precipitation.equals(Biome.Precipitation.SNOW)));
        CommonServices.REGISTRY.addEntityToBiome(BiomeTags.HAS_RUINED_PORTAL_SWAMP,
                new SpawnData(ModEntities.QUACKLING.get(), MobCategory.CREATURE, 1, 0, 1));
    }

    public static void addSpawnRules() {
        CommonServices.REGISTRY.setSpawnRules(ModEntities.DUCK.get(),
                SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (a, b, c, d, e) -> true);
        CommonServices.REGISTRY.setSpawnRules(ModEntities.QUACKLING.get(),
                SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}
