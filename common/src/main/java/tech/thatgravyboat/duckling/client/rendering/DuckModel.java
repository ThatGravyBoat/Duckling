package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class DuckModel extends GeoModel<DuckEntity> {
    @Override
    public ResourceLocation getModelResource(DuckEntity object) {
        return Duckling.modId("geo/duck.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DuckEntity object) {
        return object.getTexture().texture;
    }

    @Override
    public ResourceLocation getAnimationResource(DuckEntity animatable) {
        return Duckling.modId("animations/duck.animation.json");
    }
}
