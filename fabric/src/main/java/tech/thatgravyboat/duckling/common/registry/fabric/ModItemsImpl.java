package tech.thatgravyboat.duckling.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

import static tech.thatgravyboat.duckling.Duckling.modId;

public class ModItemsImpl {

    public static Supplier<Item> registerItem(String id, Supplier<Item> item) {
        var object = Registry.register(Registry.ITEM, modId(id), item.get());
        return () -> object;
    }

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(String id, Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        var object = Registry.register(Registry.ITEM, modId(id), new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings));
        return () -> object;
    }
}
