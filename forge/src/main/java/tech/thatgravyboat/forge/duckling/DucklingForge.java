package tech.thatgravyboat.forge.duckling;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;
import tech.thatgravyboat.duckling.platform.SpawnData;
import tech.thatgravyboat.forge.duckling.services.ForgeRegistryService;

import java.util.HashMap;
import java.util.Map;

@Mod(Duckling.MODID)
public class DucklingForge {

    public DucklingForge() {
        Duckling.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(DucklingForge::addEntityAttributes);
        bus.addListener(this::onComplete);

        ForgeRegistryService.ITEMS.register(bus);
        ForgeRegistryService.ENTITIES.register(bus);
        ForgeRegistryService.SOUNDS.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> DucklingForgeClient::init);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        ModSpawns.addSpawnRules();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void addSpawns(BiomeLoadingEvent event) {
        ModSpawns.addSpawns();
        for (SpawnData spawnData : ForgeRegistryService.ENTITY_SPAWNS.get(event.getCategory())) {
            event.getSpawns().spawn(spawnData.group(),
                    new SpawnSettings.SpawnEntry(spawnData.entityType(), spawnData.weight(), spawnData.min(), spawnData.max()));
        }
    }

    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer.Builder> attributes = new HashMap<>();
        Duckling.addEntityAttributes(attributes);
        attributes.forEach((entity, builder) -> event.put(entity, builder.build()));
    }
}
