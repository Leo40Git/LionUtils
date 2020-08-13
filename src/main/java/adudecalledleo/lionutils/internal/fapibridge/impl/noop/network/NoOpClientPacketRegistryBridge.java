package adudecalledleo.lionutils.internal.fapibridge.impl.noop.network;

import adudecalledleo.lionutils.internal.fapibridge.network.ClientPacketRegistryBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.PacketConsumerBridge;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class NoOpClientPacketRegistryBridge implements ClientPacketRegistryBridge {
    public static final ClientPacketRegistryBridge INSTANCE = new NoOpClientPacketRegistryBridge();

    private NoOpClientPacketRegistryBridge() {
    }

    @Override
    public Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf) {
        return null;
    }

    @Override
    public void register(Identifier packetID, PacketConsumerBridge consumer) { }

    @Override
    public void sendToServer(Identifier packetID, PacketByteBuf byteBuf) { }
}
