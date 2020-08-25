package adudecalledleo.lionutils.internal.fapibridge.network;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface PacketRegistryBridge {
    Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf);

    void register(Identifier packetID, PacketConsumerBridge consumer);
}
