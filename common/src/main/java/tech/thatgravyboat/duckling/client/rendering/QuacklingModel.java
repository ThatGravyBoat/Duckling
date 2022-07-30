package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class QuacklingModel extends AnimatedGeoModel<QuacklingEntity> {
    @Override
    public ResourceLocation getModelResource(QuacklingEntity object) {
        return Duckling.modId("geo/quackling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(QuacklingEntity object) {
        var string = object.getName().getString();
        if (string.equalsIgnoreCase("haley") || string.equalsIgnoreCase("maid")) return Duckling.modId("textures/entity/maid_quackling.png");
        return object.isDripped() ? Duckling.modId("textures/entity/dripped_out_quackling.png") : Duckling.modId("textures/entity/quackling.png");
    }

    @Override
    public ResourceLocation getAnimationResource(QuacklingEntity animatable) {
        return Duckling.modId("animations/quackling.animation.json");
    }
}
