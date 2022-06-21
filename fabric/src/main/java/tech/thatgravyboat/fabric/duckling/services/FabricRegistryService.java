package tech.thatgravyboat.fabric.duckling.services;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.duckling.platform.SpawnData;
import tech.thatgravyboat.duckling.platform.services.IRegistryHelper;

import java.util.function.Supplier;

import static tech.thatgravyboat.duckling.Duckling.modId;

public class FabricRegistryService implements IRegistryHelper {

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var object = Registry.register(Registry.ITEM, modId(id), item.get());
        return () -> object;
    }

    @Override
    public <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        var object = Registry.register(Registry.ITEM, modId(id), new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings));
        return () -> object;
    }


    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory group, float height, float width) {
        var object = Registry.register(Registry.ENTITY_TYPE, modId(id), FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.scalable(width, height)).build());
        return () -> object;
    }

    @Override
    public Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        var object = Registry.register(Registry.SOUND_EVENT, modId(id), sound.get());
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
