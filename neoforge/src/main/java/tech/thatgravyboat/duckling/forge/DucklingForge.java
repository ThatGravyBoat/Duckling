package tech.thatgravyboat.duckling.forge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;

import java.util.HashMap;
import java.util.Map;

@Mod(Duckling.MODID)
public class DucklingForge {

    public DucklingForge(IEventBus bus) {
        Duckling.init();

        bus.addListener(DucklingForge::addEntityAttributes);
        bus.addListener(this::onComplete);
        bus.addListener(this::setup);
        bus.addListener(DucklingForge::onModifyCreativeTabs);

        if (FMLLoader.getDist().isClient()) {
            DucklingForgeClient.init(bus);
        }

        NeoForge.EVENT_BUS.register(this);
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        ModSpawns.addSpawnRules();
    }

    public void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(Duckling::lateInit);
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
