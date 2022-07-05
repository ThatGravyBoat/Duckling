package tech.thatgravyboat.duckling.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

import static tech.thatgravyboat.duckling.Duckling.modId;

public class ModSoundsImpl {
    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        var object = Registry.register(Registry.SOUND_EVENT, modId(id), sound.get());
        return () -> object;
    }
}
