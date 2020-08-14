package adudecalledleo.lionutils.internal.fapibridge.impl.network;

import adudecalledleo.lionutils.internal.fapibridge.network.PacketContextBridge;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.thread.ThreadExecutor;

public final class PacketContextBridgeImpl implements PacketContextBridge {
    private final PacketContext packetContext;

    public PacketContextBridgeImpl(PacketContext packetContext) {
        this.packetContext = packetContext;
    }

    @Override
    public EnvType getPacketEnvironment() {
        return packetContext.getPacketEnvironment();
    }

    @Override
    public PlayerEntity getPlayer() {
        return packetContext.getPlayer();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ThreadExecutor getTaskQueue() {
        return packetContext.getTaskQueue();
    }
}
