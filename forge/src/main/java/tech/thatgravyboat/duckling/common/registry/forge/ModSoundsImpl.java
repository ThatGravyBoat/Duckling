package tech.thatgravyboat.duckling.common.registry.forge;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tech.thatgravyboat.duckling.Duckling;

import java.util.function.Supplier;

public class ModSoundsImpl {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Duckling.MODID);

    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        return SOUNDS.register(id, sound);
    }
}
