package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.entity.DuckEntity;

public class DuckModel extends AnimatedGeoModel<DuckEntity> {
    @Override
    public Identifier getModelLocation(DuckEntity object) {
        return Duckling.modId("geo/duck.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DuckEntity object) {
        return object.getTexture().texture;
    }

    @Override
    public Identifier getAnimationFileLocation(DuckEntity animatable) {
        return Duckling.modId("animations/duck.animation.json");
    }
}
