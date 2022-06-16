package tech.thatgravyboat.duckling.common.constants;

import net.minecraft.resources.ResourceLocation;
import tech.thatgravyboat.duckling.Duckling;

import java.util.Locale;

public enum DuckVariant {
    AGENTD(0, Duckling.modId("textures/entity/agent_d.png")),
    MALLARD(1, Duckling.modId("textures/entity/mallard.png")),
    PEKIN(2, Duckling.modId("textures/entity/duck.png"));

    public final ResourceLocation texture;
    public final byte id;

    DuckVariant(int id, ResourceLocation texture) {
        this.texture = texture;
        this.id = (byte)id;
    }

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static DuckVariant getVariant(byte id) {
        for (DuckVariant value : values()) {
            if (value.id == id) return value;
        }
        return DuckVariant.PEKIN;
    }

    public static DuckVariant getVariant(String id) {
        try {
            return DuckVariant.valueOf(id.toUpperCase(Locale.ROOT));
        }catch (Exception e) {
            return DuckVariant.PEKIN;
        }
    }
}
