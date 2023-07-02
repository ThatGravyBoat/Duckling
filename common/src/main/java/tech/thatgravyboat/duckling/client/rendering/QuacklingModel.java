package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.client.DucklingClient;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class QuacklingModel extends GeoModel<QuacklingEntity> {
    @Override
    public ResourceLocation getModelResource(QuacklingEntity object) {
        if (!isMaid(object) && !object.isDripped() && DucklingClient.isDecember) {
            return Duckling.modId("geo/quackling_christmas.geo.json");
        }
        return Duckling.modId("geo/quackling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QuacklingEntity object) {
        if (isMaid(object)) return Duckling.modId("textures/entity/maid_quackling.png");
        if (object.isDripped()) return Duckling.modId("textures/entity/dripped_out_quackling.png");
        return DucklingClient.isDecember ? Duckling.modId("textures/entity/christmas_quackling.png") : Duckling.modId("textures/entity/quackling.png");
    }

    @Override
    public ResourceLocation getAnimationResource(QuacklingEntity animatable) {
        return Duckling.modId("animations/quackling.animation.json");
    }

    private boolean isMaid(QuacklingEntity object) {
        return object.getName().getString().equalsIgnoreCase("haley") || object.getName().getString().equalsIgnoreCase("maid");
    }
}
