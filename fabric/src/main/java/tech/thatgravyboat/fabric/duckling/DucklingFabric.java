package tech.thatgravyboat.fabric.duckling;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.registry.Registry;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;
import tech.thatgravyboat.fabric.duckling.services.FabricRegistryService;
import net.fabricmc.api.ModInitializer;

import java.util.HashMap;
import java.util.Map;

import static tech.thatgravyboat.duckling.Duckling.modId;

public class DucklingFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Duckling.init();

        Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer.Builder> attributes = new HashMap<>();
        Duckling.addEntityAttributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);

        ModSpawns.addSpawnRules();
        ModSpawns.addSpawns();
    }
}
