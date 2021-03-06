package tech.thatgravyboat.duckling.platform;

import tech.thatgravyboat.duckling.platform.services.IRegistryHelper;

import java.util.ServiceLoader;

public class CommonServices {

    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
    }
}
