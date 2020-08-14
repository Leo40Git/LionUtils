package adudecalledleo.lionutils.internal.fapibridge.impl.nop.network;

import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public final class NOpClientPacketRegistryBridge extends NOpPacketRegistryBridge
        implements ClientPacketRegistryBridge {
    public static final ClientPacketRegistryBridge INSTANCE = new NOpClientPacketRegistryBridge();

    private NOpClientPacketRegistryBridge() {
    }

    @Override
    public void sendToServer(Identifier packetID, PacketByteBuf byteBuf) { }
}
