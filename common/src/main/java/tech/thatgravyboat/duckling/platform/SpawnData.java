package tech.thatgravyboat.duckling.platform;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Predicate;

public record SpawnData(EntityType<?> entityType, MobCategory group, int weight, int min, int max, Predicate<Biome.Precipitation> shouldSpawn) {

    public SpawnData(EntityType<?> entityType, MobCategory group, int weight, int min, int max) {
        this(entityType, group, weight, min, max, b -> true);
    }

}
