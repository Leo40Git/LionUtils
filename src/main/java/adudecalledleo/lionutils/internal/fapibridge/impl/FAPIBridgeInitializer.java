package adudecalledleo.lionutils.internal.fapibridge.impl;

import adudecalledleo.lionutils.internal.fapibridge.FAPIBridgeProvider;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ClientPacketRegistryBridgeImpl;
import adudecalledleo.lionutils.internal.fapibridge.impl.network.ServerPacketRegistryBridgeImpl;
import net.fabricmc.loader.api.FabricLoader;

public final class FAPIBridgeInitializer {
    public static void init() {
        assert FabricLoader.getInstance().isModLoaded("fabric") : "Utility that requires Fabric API was called " +
                "without Fabric API being loaded! Please install Fabric API (and also add a dependency on FAPI to your mod).";
        PacketRegistry.init();
    }

    private static final class PacketRegistry {
        public static void init() {
            FAPIBridgeProvider.PacketRegistry.CLIENT = ClientPacketRegistryBridgeImpl.INSTANCE;
            FAPIBridgeProvider.PacketRegistry.SERVER = ServerPacketRegistryBridgeImpl.INSTANCE;
        }
    }
}
