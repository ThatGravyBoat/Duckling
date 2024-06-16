package tech.thatgravyboat.duckling.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.items.DuckEggItem;
import tech.thatgravyboat.duckling.common.items.HolidayFruitCakeItem;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {

    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, Duckling.MODID);

    private static final FoodProperties FRUIT_CAKE_FOOD = new FoodProperties.Builder().nutrition(3).saturationModifier(1).build();
    private static final FoodProperties RAW_DUCK_FOOD = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();
    private static final FoodProperties COOKED_DUCK_FOOD = new FoodProperties.Builder().nutrition(6).saturationModifier(0.6F).build();


    public static final Supplier<Item> HOLIDAY_FRUIT_CAKE = ITEMS.register("holiday_fruit_cake", () -> new HolidayFruitCakeItem(ModBlocks.HOLIDAY_FRUIT_CAKE_BLOCK.get(), new Item.Properties().food(FRUIT_CAKE_FOOD)));
    public static final Supplier<Item> RAW_DUCK = ITEMS.register("raw_duck", () -> new Item(new Item.Properties().food(RAW_DUCK_FOOD)));
    public static final Supplier<Item> COOKED_DUCK = ITEMS.register("cooked_duck", () -> new Item(new Item.Properties().food(COOKED_DUCK_FOOD)));

    public static final Supplier<SpawnEggItem> QUACKLING_SPAWN_EGG = ITEMS.register("quackling_spawn_egg",
            registerSpawnEgg(ModEntities.QUACKLING, 0xFAC946, 0x84A83C, new Item.Properties()));

    public static final Supplier<SpawnEggItem> DUCK_SPAWN_EGG = ITEMS.register("duck_spawn_egg",
            registerSpawnEgg(ModEntities.DUCK, 0x53331E, 0x309627, new Item.Properties()));

    public static final Supplier<Item> DUCK_EGG = ITEMS.register("duck_egg", () -> new DuckEggItem(new Item.Properties()));

    @ExpectPlatform
    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        throw new AssertionError();
    }

}
