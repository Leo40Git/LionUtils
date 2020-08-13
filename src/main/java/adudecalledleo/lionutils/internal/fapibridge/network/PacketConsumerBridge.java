package adudecalledleo.lionutils.internal.fapibridge.network;

import net.minecraft.network.PacketByteBuf;

@FunctionalInterface
public interface PacketConsumerBridge {
    void accept(PacketContextBridge ctx, PacketByteBuf byteBuf);
}
