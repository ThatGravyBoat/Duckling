package tech.thatgravyboat.forge.duckling.services;

import com.google.common.collect.ArrayListMultimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.platform.SpawnData;
import tech.thatgravyboat.duckling.platform.services.IRegistryHelper;

import java.util.function.Supplier;

public class ForgeRegistryService implements IRegistryHelper {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Duckling.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Duckling.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Duckling.MODID);

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }

    @Override
    public <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        return ITEMS.register(id, () -> new ForgeSpawnEggItem(entity, primaryColor, secondaryColor, settings));
    }


    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, MobCategory group, float height, float width) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(factory, group).sized(width, height).build(id));
    }

    @Override
    public Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        return SOUNDS.register(id, sound);
    }

    @Override
    public void addEntityToBiome(TagKey<Biome> category, SpawnData data) {

    }

    @Override
    public <T extends Mob> void setSpawnRules(EntityType<T> entityType, SpawnPlacements.Type location, Heightmap.Types type, SpawnPlacements.SpawnPredicate<T> predicate) {
        SpawnPlacements.register(entityType, location, type, predicate);
    }


}
