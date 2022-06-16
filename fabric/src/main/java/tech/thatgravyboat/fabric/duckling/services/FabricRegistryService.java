package tech.thatgravyboat.fabric.duckling.services;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.duckling.platform.SpawnData;
import tech.thatgravyboat.duckling.platform.services.IRegistryHelper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FabricRegistryService implements IRegistryHelper {

    public static final Map<String, Supplier<Item>> ITEMS = new LinkedHashMap<>();
    public static final Map<String, Supplier<EntityType<?>>> ENTITIES = new LinkedHashMap<>();
    public static final Map<String, Supplier<SoundEvent>> SOUNDS = new LinkedHashMap<>();

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var object = item.get();
        ITEMS.put(id, () -> object);
        return () -> object;
    }

    @Override
    public <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        var object = new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings);
        ITEMS.put(id, () -> object);
        return () -> object;
    }


    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory group, float height, float width) {
        var object = FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.scalable(width, height)).build();
        ENTITIES.put(id, () -> object);
        return () -> object;
    }

    @Override
    public Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        var object = sound.get();
        SOUNDS.put(id, sound);
        return () -> object;
    }

    @Override
    public void addEntityToBiome(TagKey<Biome> category, SpawnData data) {
        BiomeModifications.addSpawn(BiomeSelectors.tag(category).and(ctx -> data.shouldSpawn().test(ctx.getBiome().getPrecipitation())), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    @Override
    public <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType, location, type, predicate);
    }


}
