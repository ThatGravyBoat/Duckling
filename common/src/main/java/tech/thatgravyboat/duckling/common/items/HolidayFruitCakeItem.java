package tech.thatgravyboat.duckling.common.items;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

public class HolidayFruitCakeItem extends BlockItem {

    public HolidayFruitCakeItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            return super.place(context);
        }
        return InteractionResult.FAIL;
    }
}
