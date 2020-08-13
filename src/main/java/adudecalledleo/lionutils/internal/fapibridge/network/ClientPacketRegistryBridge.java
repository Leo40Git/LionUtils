package adudecalledleo.lionutils.internal.fapibridge.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public interface ClientPacketRegistryBridge extends PacketRegistryBridge {
    void sendToServer(Identifier packetID, PacketByteBuf byteBuf);
}
