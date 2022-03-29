package tech.thatgravyboat.duckling;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import software.bernie.geckolib3.GeckoLib;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;
import tech.thatgravyboat.duckling.common.registry.*;
import tech.thatgravyboat.duckling.platform.CommonServices;

import java.util.Map;

public class Duckling {
    public static final String MODID = "duckling";

    public static void init() {
        GeckoLib.initialize();
        ModItems.register();
        ModEntities.register();
        ModSounds.register();
        ModCriteria.register();
    }

    public static void lateInit() {
    }

    public static void addEntityAttributes(Map<EntityType<? extends LivingEntity>, DefaultAttributeContainer.Builder> attributes) {
        attributes.put(ModEntities.DUCK.get(), DuckEntity.createDuckAttributes());
        attributes.put(ModEntities.QUACKLING.get(), QuacklingEntity.createQuacklingAttributes());
    }

    public static Identifier modId(String path) {
        return new Identifier(MODID, path);
    }
}
