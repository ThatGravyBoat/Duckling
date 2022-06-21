package tech.thatgravyboat.fabric.duckling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;
import tech.thatgravyboat.fabric.duckling.services.FabricRegistryService;

import java.util.HashMap;
import java.util.Map;

import static tech.thatgravyboat.duckling.Duckling.modId;

public class DucklingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Duckling.init();

        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Duckling.addEntityAttributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);

        ModSpawns.addSpawnRules();
        ModSpawns.addSpawns();
    }
}
