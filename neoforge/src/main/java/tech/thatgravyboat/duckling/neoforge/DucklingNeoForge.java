package tech.thatgravyboat.duckling.neoforge;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;

import java.util.HashMap;
import java.util.Map;

@Mod(Duckling.MODID)
public class DucklingNeoForge {

    public DucklingNeoForge(IEventBus bus) {
        Duckling.init();

        bus.addListener(DucklingNeoForge::addEntityAttributes);
        bus.addListener(this::setup);
        bus.addListener(DucklingNeoForge::onModifyCreativeTabs);
        bus.addListener(this::onSpawnPlacement);

        if (FMLLoader.getDist().isClient()) {
            DucklingNeoForgeClient.init(bus);
        }
    }

    public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(Duckling::lateInit);
    }

    public void onSpawnPlacement(SpawnPlacementRegisterEvent event) {
        ModSpawns.addSpawnRules(new ModSpawns.SpawnRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
                event.register(entityType, spawnPlacementType, types, spawnPredicate, SpawnPlacementRegisterEvent.Operation.OR);
            }
        });
    }

    public static void onModifyCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        Duckling.addCreativeTabContent(event.getTabKey(), event::accept);
    }

    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Duckling.addEntityAttributes(attributes);
        attributes.forEach((entity, builder) -> event.put(entity, builder.build()));
    }
}
