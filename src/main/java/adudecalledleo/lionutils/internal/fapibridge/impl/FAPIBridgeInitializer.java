package adudecalledleo.lionutils.internal.fapibridge.impl;

import net.fabricmc.loader.api.FabricLoader;

import static adudecalledleo.lionutils.internal.fapibridge.FAPIBridgeProvider.LOGGER;

public class FAPIBridgeInitializer {

    public static void init() {
        FabricLoader loader = FabricLoader.getInstance();
        if (loader.isModLoaded("fabric")) {
            PacketRegistryBridgeInitializer.init();
        } else {
            LOGGER.warn("!!!!! WARNING !!!!!");
            LOGGER.warn("Fabric API Bridge was accessed, but Fabric API isn't installed!");
            LOGGER.warn("Proceeding with no-op bridge - no FAPI methods will be called!");
            LOGGER.warn("Expect bad things to happen!!!");
            LOGGER.warn("!!!!! WARNING !!!!!");
        }
    }
}
