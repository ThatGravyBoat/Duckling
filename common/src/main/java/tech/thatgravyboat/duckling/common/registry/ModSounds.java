package tech.thatgravyboat.duckling.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.duckling.Duckling;

import java.util.function.Supplier;

public class ModSounds {

    public static final Supplier<SoundEvent> QUACK = registerSound("quack",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("quack")));
    public static final Supplier<SoundEvent> DEEP_QUACK = registerSound("deep_quack",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("deep_quack")));
    public static final Supplier<SoundEvent> DUCK_DEATH = registerSound("duck_death",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("duck_death")));
    public static final Supplier<SoundEvent> QUACKLING_DEATH = registerSound("quackling_death",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("quackling_death")));

    public static void register() {
        //Init class
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> serializer) {
        throw new AssertionError();
    }
}
