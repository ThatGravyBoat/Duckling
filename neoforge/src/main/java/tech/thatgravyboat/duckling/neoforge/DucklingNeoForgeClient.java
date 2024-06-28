package tech.thatgravyboat.duckling.neoforge;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import tech.thatgravyboat.duckling.client.DucklingClient;

public class DucklingNeoForgeClient {

    public static void init(IEventBus bus) {
        bus.addListener(DucklingNeoForgeClient::onClientInit);
    }

    public static void onClientInit(FMLCommonSetupEvent event) {
        DucklingClient.init();
    }
}
