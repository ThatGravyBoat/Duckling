package tech.thatgravyboat.duckling.platform;

import tech.thatgravyboat.duckling.platform.services.IClientHelper;

import java.util.ServiceLoader;

public class ClientServices {

    public static final IClientHelper CLIENT = load(IClientHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
