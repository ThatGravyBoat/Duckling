package tech.thatgravyboat.duckling.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.duckling.Duckling;

import java.util.function.Supplier;

public class ModSounds {

    public static final ResourcefulRegistry<SoundEvent> SOUNDS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, Duckling.MODID);

    public static final Supplier<SoundEvent> QUACK = SOUNDS.register("quack",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("quack")));
    public static final Supplier<SoundEvent> DEEP_QUACK = SOUNDS.register("deep_quack",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("deep_quack")));
    public static final Supplier<SoundEvent> DUCK_DEATH = SOUNDS.register("duck_death",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("duck_death")));
    public static final Supplier<SoundEvent> QUACKLING_DEATH = SOUNDS.register("quackling_death",
            () -> SoundEvent.createVariableRangeEvent(Duckling.modId("quackling_death")));
}
