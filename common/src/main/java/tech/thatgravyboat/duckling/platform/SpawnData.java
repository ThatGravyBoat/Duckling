package tech.thatgravyboat.duckling.platform;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public record SpawnData(EntityType<?> entityType, SpawnGroup group, int weight, int min, int max) {}
