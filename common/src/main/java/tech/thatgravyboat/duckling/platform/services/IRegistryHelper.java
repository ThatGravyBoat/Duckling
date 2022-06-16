package tech.thatgravyboat.duckling.platform.services;


import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.duckling.platform.SpawnData;

import java.util.function.Supplier;

public interface IRegistryHelper {

    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> block);

    <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory group, float height, float width);

    Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> serializer);

    void addEntityToBiome(TagKey<Biome> category, SpawnData data);

    <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate);
}
