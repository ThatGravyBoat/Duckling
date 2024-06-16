package tech.thatgravyboat.duckling.common.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Tags which are common between both neo and fabric.
 */
public class ModTags {

    public static final TagKey<Item> SHEARS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("c", "tools/shear")
    );

    public static final TagKey<Item> FISHING_RODS = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("c", "tools/fishing_rod")
    );
}
