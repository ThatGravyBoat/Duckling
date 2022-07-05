package tech.thatgravyboat.duckling.fabric;

import net.fabricmc.api.ClientModInitializer;
import tech.thatgravyboat.duckling.client.DucklingClient;

public class DucklingFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        DucklingClient.init();
    }
}
