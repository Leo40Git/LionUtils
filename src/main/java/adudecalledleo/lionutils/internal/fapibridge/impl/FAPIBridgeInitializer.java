package adudecalledleo.lionutils.internal.fapibridge.impl;

import adudecalledleo.lionutils.internal.fapibridge.FAPIBridgeProvider;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ClientPacketRegistryBridgeImpl;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ServerPacketRegistryBridgeImpl;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Logger;

public final class FAPIBridgeInitializer {
    public static void init() {
        assert FabricLoader.getInstance().isModLoaded("fabric") : "Utility that requires Fabric API was called " +
                "without Fabric API being loaded!";
        PacketRegistry.init();
    }

    private static final class PacketRegistry {
        public static void init() {
            FAPIBridgeProvider.PacketRegistry.CLIENT = ClientPacketRegistryBridgeImpl.INSTANCE;
            FAPIBridgeProvider.PacketRegistry.SERVER = ServerPacketRegistryBridgeImpl.INSTANCE;
        }
    }
}
