package tech.thatgravyboat.duckling.client.rendering;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.common.entity.QuacklingEntity;

public class QuacklingModel extends AnimatedGeoModel<QuacklingEntity> {
    @Override
    public Identifier getModelLocation(QuacklingEntity object) {
        return Duckling.modId("geo/quackling.geo.json");
    }

    @Override
    public Identifier getTextureLocation(QuacklingEntity object) {
        return object.isDripped() ? Duckling.modId("textures/entity/dripped_out_quackling.png") : Duckling.modId("textures/entity/quackling.png");
    }

    @Override
    public Identifier getAnimationFileLocation(QuacklingEntity animatable) {
        return Duckling.modId("animations/quackling.animation.json");
    }
}
