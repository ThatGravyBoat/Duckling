package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import tech.thatgravyboat.duckling.common.items.DuckEggItem;
import tech.thatgravyboat.duckling.common.items.HolidayFruitCakeItem;
import tech.thatgravyboat.duckling.platform.CommonServices;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {

    public static final Supplier<Item> HOLIDAY_FRUIT_CAKE = CommonServices.REGISTRY.registerItem("holiday_fruit_cake",
            () -> new HolidayFruitCakeItem(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));

    public static final Supplier<SpawnEggItem> QUACKLING_SPAWN_EGG = CommonServices.REGISTRY.registerSpawnEgg("quackling_spawn_egg",
            ModEntities.QUACKLING, 0xFAC946, 0x84A83C, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    public static final Supplier<SpawnEggItem> DUCK_SPAWN_EGG = CommonServices.REGISTRY.registerSpawnEgg("duck_spawn_egg",
            ModEntities.DUCK, 0x53331E, 0x309627, new Item.Properties().tab(CreativeModeTab.TAB_MISC));

    public static final Supplier<Item> DUCK_EGG = CommonServices.REGISTRY.registerItem("duck_egg",
            () -> new DuckEggItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register() {
        //Initialize class.
    }

}
