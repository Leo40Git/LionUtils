package adudecalledleo.lionutils.internal.fapibridge.impl.nop.network;

import adudecalledleo.lionutils.internal.fapibridge.network.PacketConsumerBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.PacketRegistryBridge;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

abstract class NullPacketRegistryBridge implements PacketRegistryBridge {
    @Override
    public Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf) {
        return null;
    }

    @Override
    public void register(Identifier packetID, PacketConsumerBridge consumer) { }
}
