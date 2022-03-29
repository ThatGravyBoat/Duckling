package tech.thatgravyboat.forge.duckling.services;

import com.google.common.collect.ArrayListMultimap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.platform.SpawnData;
import tech.thatgravyboat.duckling.platform.services.IRegistryHelper;

import java.util.Map;
import java.util.function.Supplier;

public class ForgeRegistryService implements IRegistryHelper {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Duckling.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Duckling.MODID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Duckling.MODID);
    public static final ArrayListMultimap<Biome.Category, SpawnData> ENTITY_SPAWNS = ArrayListMultimap.create();

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }

    @Override
    public <T extends MobEntity> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Settings settings) {
        return ITEMS.register(id, () -> new ForgeSpawnEggItem(entity, primaryColor, secondaryColor, settings));
    }


    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String id, EntityType.EntityFactory<T> factory, SpawnGroup group, float height, float width) {
        return ENTITIES.register(id, () -> EntityType.Builder.create(factory, group).setDimensions(width, height).build(id));
    }

    @Override
    public Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        return SOUNDS.register(id, sound);
    }

    @Override
    public <C extends CriterionConditions, T extends Criterion<C>> T createCriteria(T criterion) {
        return Criteria.register(criterion);
    }

    @Override
    public void addEntityToBiome(Biome.Category category, SpawnData data) {
        ENTITY_SPAWNS.put(category, data);
    }

    @Override
    public <T extends MobEntity> void setSpawnRules(EntityType<T> entityType, SpawnRestriction.Location location, Heightmap.Type type, SpawnRestriction.SpawnPredicate<T> predicate) {
        SpawnRestriction.register(entityType, location, type, predicate);
    }


}
