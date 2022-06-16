package tech.thatgravyboat.duckling.common.registry;

import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.duckling.Duckling;
import tech.thatgravyboat.duckling.platform.CommonServices;

import java.util.function.Supplier;

public class ModSounds {

    public static final Supplier<SoundEvent> QUACK = CommonServices.REGISTRY.registerSound("quack",
            () -> new SoundEvent(Duckling.modId("quack")));
    public static final Supplier<SoundEvent> DEEP_QUACK = CommonServices.REGISTRY.registerSound("deep_quack",
            () -> new SoundEvent(Duckling.modId("deep_quack")));
    public static final Supplier<SoundEvent> DUCK_DEATH = CommonServices.REGISTRY.registerSound("duck_death",
            () -> new SoundEvent(Duckling.modId("duck_death")));
    public static final Supplier<SoundEvent> QUACKLING_DEATH = CommonServices.REGISTRY.registerSound("quackling_death",
            () -> new SoundEvent(Duckling.modId("quackling_death")));

    public static void register() {
        //Init class
    }
}
