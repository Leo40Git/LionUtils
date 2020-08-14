package adudecalledleo.lionutils.internal.fapibridge.impl;

import adudecalledleo.lionutils.internal.fapibridge.FAPIBridgeProvider;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ClientPacketRegistryBridgeImpl;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ServerPacketRegistryBridgeImpl;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;

public final class FAPIBridgeInitializer {
    public static void init(Logger logger) {
        if (!FabricLoader.getInstance().isModLoaded("fabric")) {
            logger.warn("!!!!! WARNING !!!!!");
            logger.warn("Fabric API Bridge was accessed, but Fabric API isn't installed!");
            logger.warn("Proceeding with no-op bridge - no FAPI methods will be called!");
            logger.warn("Expect bad things to happen!!!");
            logger.warn("!!!!! WARNING !!!!!");
            return;
        }
        PacketRegistry.init();
    }

    private static final class PacketRegistry {
        public static void init() {
            FAPIBridgeProvider.PacketRegistry.CLIENT = ClientPacketRegistryBridgeImpl.INSTANCE;
            FAPIBridgeProvider.PacketRegistry.SERVER = ServerPacketRegistryBridgeImpl.INSTANCE;
        }
    }
}
