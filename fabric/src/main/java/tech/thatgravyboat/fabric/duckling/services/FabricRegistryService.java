package tech.thatgravyboat.fabric.duckling.services;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
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
    public <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings) {
        var object = new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings);
        ITEMS.put(id, () -> object);
        return () -> object;
    }


    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, SpawnGroup group, float height, float width) {
        var object = FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.fixed(width, height)).build();
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
    public void addEntityToBiome(Biome.Category category, SpawnData data) {
        BiomeModifications.addSpawn(BiomeSelectors.categories(category), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    @Override
    public <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType, location, type, predicate);
    }


}
