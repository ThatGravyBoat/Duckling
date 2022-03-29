package tech.thatgravyboat.forge.duckling;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.duckling.client.DucklingClient;

public class DucklingForgeClient {

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DucklingForgeClient::onClientInit);
    }

    public static void onClientInit(FMLCommonSetupEvent event) {
        DucklingClient.init();
    }
}
