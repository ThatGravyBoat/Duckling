package tech.thatgravyboat.duckling.forge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.registry.ModSpawns;
import tech.thatgravyboat.duckling.common.registry.forge.ModEntitiesImpl;
import tech.thatgravyboat.duckling.common.registry.forge.ModItemsImpl;
import tech.thatgravyboat.duckling.common.registry.forge.ModSoundsImpl;

import java.util.HashMap;
import java.util.Map;

@Mod(Duckling.MODID)
public class DucklingForge {

    public DucklingForge() {
        Duckling.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(DucklingForge::addEntityAttributes);
        bus.addListener(this::onComplete);
        bus.addListener(this::setup);
        bus.addListener(DucklingForge::onModifyCreativeTabs);

        ModItemsImpl.BLOCKS.register(bus);
        ModItemsImpl.ITEMS.register(bus);
        ModEntitiesImpl.ENTITIES.register(bus);
        ModSoundsImpl.SOUNDS.register(bus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> DucklingForgeClient::init);
        MinecraftForge.EVENT_BUS.register(this);
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
