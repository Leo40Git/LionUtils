package adudecalledleo.lionutils.internal.fapibridge.impl.network;

import adudecalledleo.lionutils.internal.fapibridge.network.PacketConsumerBridge;
import adudecalledleo.lionutils.internal.fapibridge.network.PacketRegistryBridge;
import net.fabricmc.fabric.api.network.PacketConsumer;
import net.fabricmc.fabric.api.network.PacketRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

abstract class PacketRegistryBridgeImpl implements PacketRegistryBridge {
    private final PacketRegistry delegate;

    protected PacketRegistryBridgeImpl(PacketRegistry delegate) {
        this.delegate = delegate;
    }

    @Override
    public Packet<?> toPacket(Identifier packetID, PacketByteBuf byteBuf) {
        return delegate.toPacket(packetID, byteBuf);
    }

    @Override
    public void register(Identifier packetID, PacketConsumerBridge consumer) {
        delegate.register(packetID, convertBridge(consumer));
    }

    protected PacketConsumer convertBridge(PacketConsumerBridge consumer) {
        return (context, buffer) -> consumer.accept(new PacketContextBridgeImpl(context), buffer);
    }
}
