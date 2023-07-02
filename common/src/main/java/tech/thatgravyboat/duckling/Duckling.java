package tech.thatgravyboat.duckling;

import net.minecraft.Util;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.GeckoLib;
import tech.thatgravyboat.duckling.common.entity.DuckEggEntity;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.common.registry.ModItems;
import tech.thatgravyboat.duckling.common.registry.ModSounds;

import java.util.Map;
import java.util.function.Consumer;

public class Duckling {
    public static final String MODID = "duckling";

    public static void init() {
        GeckoLib.initialize();
        ModItems.register();
        ModEntities.register();
        ModSounds.register();
    }

    public static void lateInit() {
        DispenserBlock.registerBehavior(ModItems.DUCK_EGG.get(), new AbstractProjectileDispenseBehavior() {
            protected @NotNull Projectile getProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack) {
                return Util.make(new DuckEggEntity(level, pos.x(), pos.y(), pos.z()), (egg) -> egg.setItem(stack));
            }
        });
    }

    public static void addEntityAttributes(Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes) {
        attributes.put(ModEntities.DUCK.get(), DuckEntity.createDuckAttributes());
        attributes.put(ModEntities.QUACKLING.get(), QuacklingEntity.createQuacklingAttributes());
    }

    private static final ResourceKey<CreativeModeTab> FOOD = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("food_and_drinks"));
    private static final ResourceKey<CreativeModeTab> SPAWN_EGGS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("spawn_eggs"));
    private static final ResourceKey<CreativeModeTab> INGREDIENTS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation("ingredients"));

    public static void addCreativeTabContent(ResourceKey<CreativeModeTab> tab, Consumer<Item> consumer) {
        if (tab == FOOD) {
            consumer.accept(ModItems.HOLIDAY_FRUIT_CAKE.get());
            consumer.accept(ModItems.RAW_DUCK.get());
            consumer.accept(ModItems.COOKED_DUCK.get());
        }
        if (tab == SPAWN_EGGS) {
            consumer.accept(ModItems.DUCK_SPAWN_EGG.get());
            consumer.accept(ModItems.QUACKLING_SPAWN_EGG.get());
        }
        if (tab == INGREDIENTS) {
            consumer.accept(ModItems.DUCK_EGG.get());
        }
    }

    public static ResourceLocation modId(String path) {
        return new ResourceLocation(MODID, path);
    }
}
