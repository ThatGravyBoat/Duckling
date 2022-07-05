package tech.thatgravyboat.duckling.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.duckling.Duckling;

import java.util.function.Supplier;

public class ModSounds {

    public static final Supplier<SoundEvent> QUACK = registerSound("quack",
            () -> new SoundEvent(Duckling.modId("quack")));
    public static final Supplier<SoundEvent> DEEP_QUACK = registerSound("deep_quack",
            () -> new SoundEvent(Duckling.modId("deep_quack")));
    public static final Supplier<SoundEvent> DUCK_DEATH = registerSound("duck_death",
            () -> new SoundEvent(Duckling.modId("duck_death")));
    public static final Supplier<SoundEvent> QUACKLING_DEATH = registerSound("quackling_death",
            () -> new SoundEvent(Duckling.modId("quackling_death")));

    public static void register() {
        //Init class
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> serializer) {
        throw new AssertionError();
    }
}
