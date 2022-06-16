package tech.thatgravyboat.duckling.client;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import tech.thatgravyboat.duckling.client.rendering.DuckRenderer;
import tech.thatgravyboat.duckling.client.rendering.QuacklingRenderer;
import tech.thatgravyboat.duckling.common.registry.ModEntities;
import tech.thatgravyboat.duckling.platform.ClientServices;

public class DucklingClient {

    public static void init() {
        ClientServices.CLIENT.registerEntityRenderer(ModEntities.DUCK.get(), DuckRenderer::new);
        ClientServices.CLIENT.registerEntityRenderer(ModEntities.QUACKLING.get(), QuacklingRenderer::new);
        ClientServices.CLIENT.registerEntityRenderer(ModEntities.DUCK_EGG.get(), ThrownItemRenderer::new);
    }
}
