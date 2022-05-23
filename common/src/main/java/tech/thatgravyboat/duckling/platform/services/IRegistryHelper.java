package tech.thatgravyboat.duckling.platform.services;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import tech.thatgravyboat.duckling.platform.SpawnData;

import java.util.function.Supplier;

public interface IRegistryHelper {

    <T extends Item> Supplier<T> registerItem(String id, Supplier<T> block);

    <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings);

    <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, SpawnGroup group, float height, float width);

    Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> serializer);

    void addEntityToBiome(Biome.Category category, SpawnData data);

    <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate);
}
