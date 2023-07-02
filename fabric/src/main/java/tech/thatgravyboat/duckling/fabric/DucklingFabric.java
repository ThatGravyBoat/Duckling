package tech.thatgravyboat.duckling.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;

import java.util.HashMap;
import java.util.Map;

public class DucklingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Duckling.init();

        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Duckling.addEntityAttributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);

        ModSpawns.addSpawnRules();
        FabricSpawns.addSpawns();

        Duckling.lateInit();

        ItemGroupEvents.MODIFY_ENTRIES_ALL.register((group, items) -> {
            ResourceLocation key = BuiltInRegistries.CREATIVE_MODE_TAB.getKey(group);
            if (key == null) return;
            Duckling.addCreativeTabContent(ResourceKey.create(Registries.CREATIVE_MODE_TAB, key), items::accept);
        });
    }
}
