package tech.thatgravyboat.duckling.fabric;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import tech.thatgravyboat.duckling.common.registry.ModEntities;

public class FabricSpawns {

    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_RIVER).and(ctx -> ctx.getBiome().getBaseTemperature() > 0.15f),
                MobCategory.WATER_AMBIENT, ModEntities.DUCK.get(), 5, 3, 4);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.HAS_SWAMP_HUT),
                MobCategory.CREATURE, ModEntities.QUACKLING.get(), 1, 0, 1);
    }
}

