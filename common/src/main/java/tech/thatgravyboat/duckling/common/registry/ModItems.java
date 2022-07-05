package tech.thatgravyboat.duckling.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import tech.thatgravyboat.duckling.common.items.DuckEggItem;
import tech.thatgravyboat.duckling.common.items.HolidayFruitCakeItem;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {

    public static final Supplier<Item> HOLIDAY_FRUIT_CAKE = registerItem("holiday_fruit_cake",
            () -> new HolidayFruitCakeItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));

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
    public static Supplier<Item> registerItem(String id, Supplier<Item> supplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        throw new AssertionError();
    }

}
