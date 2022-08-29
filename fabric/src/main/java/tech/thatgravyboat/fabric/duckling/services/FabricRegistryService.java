package tech.thatgravyboat.fabric.duckling.services;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.platform.SpawnData;
import tech.thatgravyboat.duckling.platform.services.IRegistryHelper;

import java.util.function.Supplier;

public class FabricRegistryService implements IRegistryHelper {

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        var object = Registry.register(Registry.ITEM, new Identifier(Duckling.MODID, id), item.get());
        return () -> object;
    }

    @Override
    public <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings) {
        var object = Registry.register(Registry.ITEM, new Identifier(Duckling.MODID, id), new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings));
        return () -> object;
    }


    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, SpawnGroup group, float height, float width) {
        var object = FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.changing(width, height)).build();
        var reg = Registry.register(Registry.ENTITY_TYPE, new Identifier(Duckling.MODID, id), object);
        return () -> reg;
    }

    @Override
    public Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        var object = Registry.register(Registry.SOUND_EVENT, new Identifier(Duckling.MODID, id), sound.get());
        return () -> object;
    }

    @Override
    public void addEntityToBiome(Biome.Category category, SpawnData data) {
        BiomeModifications.addSpawn(BiomeSelectors.categories(category).and(ctx -> data.shouldSpawn().test(ctx.getBiome().getPrecipitation())), data.group(), data.entityType(), data.weight(), data.min(), data.max());
    }

    @Override
    public <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestrictionAccessor.callRegister(entityType, location, type, predicate);
    }


}
