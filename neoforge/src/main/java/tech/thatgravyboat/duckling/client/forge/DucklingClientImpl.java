package tech.thatgravyboat.duckling.client.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class DucklingClientImpl {
    public static <E extends Entity> void registerEntityRenderer(EntityType<E> type, EntityRendererProvider<E> factory) {
        EntityRenderers.register(type, factory);
    }

    public static void setRenderType(Block block, RenderType type) {
        ItemBlockRenderTypes.setRenderLayer(block, type);
    }
}
