package tech.thatgravyboat.duckling.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.items.HolidayFruitCakeBlock;

import java.util.function.Supplier;

public class ModBlocks {

    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, Duckling.MODID);

    public static final Supplier<Block> HOLIDAY_FRUIT_CAKE_BLOCK = BLOCKS.register("holiday_fruit_cake", () -> new HolidayFruitCakeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE)));

}
