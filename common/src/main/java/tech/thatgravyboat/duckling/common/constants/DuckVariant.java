package tech.thatgravyboat.duckling.common.constants;

import net.minecraft.util.Identifier;
import tech.thatgravyboat.duckling.Duckling;

import java.util.Locale;

public enum DuckVariant {
    AGENTD(Duckling.modId("textures/entity/agent_d.png")),
    MALLARD(Duckling.modId("textures/entity/mallard.png")),
    PEKIN(Duckling.modId("textures/entity/duck.png"));

    public final Identifier texture;

    DuckVariant(Identifier texture) {
        this.texture = texture;
    }

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static DuckVariant getVariant(String id) {
        try {
            return DuckVariant.valueOf(id.toUpperCase(Locale.ROOT));
        }catch (Exception e) {
            return DuckVariant.PEKIN;
        }
    }
}
