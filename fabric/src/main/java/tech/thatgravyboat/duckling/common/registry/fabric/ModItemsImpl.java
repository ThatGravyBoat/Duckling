package tech.thatgravyboat.duckling.common.registry.fabric;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class ModItemsImpl {

    public static <T extends Mob> Supplier<SpawnEggItem> registerSpawnEgg(Supplier<EntityType<T>> entity, int primaryColor, int secondaryColor, Item.Properties settings) {
        return () -> new SpawnEggItem(entity.get(), primaryColor, secondaryColor, settings);
    }
}
