package tech.thatgravyboat.duckling.common.constants;

import software.bernie.geckolib.core.animation.RawAnimation;

public class AnimationConstants {

    public static final RawAnimation WALKING = RawAnimation.begin().thenLoop("walking");
    public static final RawAnimation FALLING = RawAnimation.begin().thenLoop("falling");
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    public static final RawAnimation FISHING = RawAnimation.begin().thenLoop("fishing");
}
