package tech.thatgravyboat.duckling.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.thatgravyboat.duckling.common.items.DuckEggItem;
import tech.thatgravyboat.duckling.common.items.HolidayFruitCakeBlock;
import tech.thatgravyboat.duckling.common.items.HolidayFruitCakeItem;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {

    private static final FoodProperties FRUIT_CAKE_FOOD = new FoodProperties.Builder().nutrition(3).saturationMod(1).build();
    private static final FoodProperties RAW_DUCK_FOOD = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build();
    private static final FoodProperties COOKED_DUCK_FOOD = new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).meat().build();

    public static final Supplier<Block> HOLIDAY_FRUIT_CAKE_BLOCK = registerBlock("holiday_fruit_cake", () -> new HolidayFruitCakeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    public static final Supplier<Item> HOLIDAY_FRUIT_CAKE = registerItem("holiday_fruit_cake",
            () -> new HolidayFruitCakeItem(HOLIDAY_FRUIT_CAKE_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(FRUIT_CAKE_FOOD)));

    public static final Supplier<Item> RAW_DUCK = registerItem("raw_duck",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(RAW_DUCK_FOOD)));

    public static final Supplier<Item> COOKED_DUCK = registerItem("cooked_duck",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(COOKED_DUCK_FOOD)));

    public static final Supplier<SpawnEggItem> QUACKLING_SPAWN_EGG = registerSpawnEgg("quackling_spawn_egg",
            ModEntities.QUACKLING, 0xFAC946, 0x84A83C, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    public static final Supplier<SpawnEggItem> DUCK_SPAWN_EGG = registerSpawnEgg("duck_spawn_egg",
            ModEntities.DUCK, 0x53331E, 0x309627, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    public static final Supplier<Item> DUCK_EGG = registerItem("duck_egg",
            () -> new DuckEggItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register() {
        //Initialize class.
    }

    @ExpectPlatform
    public static Supplier<Block> registerBlock(String id, Supplier<Block> supplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Supplier<Item> registerItem(String id, Supplier<Item> supplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        throw new AssertionError();
    }

}
