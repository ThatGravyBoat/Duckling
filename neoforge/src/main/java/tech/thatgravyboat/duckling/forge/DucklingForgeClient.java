package tech.thatgravyboat.duckling.forge;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import tech.thatgravyboat.duckling.client.DucklingClient;

public class DucklingForgeClient {

    public static void init(IEventBus bus) {
        bus.addListener(DucklingForgeClient::onClientInit);
    }

    public static void onClientInit(FMLCommonSetupEvent event) {
        DucklingClient.init();
    }
}
