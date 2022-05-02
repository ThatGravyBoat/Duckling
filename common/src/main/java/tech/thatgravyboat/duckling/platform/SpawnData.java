package tech.thatgravyboat.duckling.platform;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;

import java.util.function.Predicate;

public record SpawnData(EntityType<?> entityType, SpawnGroup group, int weight, int min, int max, Predicate<Biome.Precipitation> shouldSpawn) {

    public SpawnData(EntityType<?> entityType, SpawnGroup group, int weight, int min, int max) {
        this(entityType, group, weight, min, max, b -> true);
    }

}
