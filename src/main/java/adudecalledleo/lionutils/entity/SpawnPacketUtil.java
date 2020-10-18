package adudecalledleo.lionutils.entity;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.RequiresFabricAPI;
import adudecalledleo.lionutils.network.PacketBufUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Helper class for dealing with entity spawn packets.
 *
 * @since 3.0.0
 */
@RequiresFabricAPI
public final class SpawnPacketUtil {
    private SpawnPacketUtil() {
        InitializerUtil.utilCtor();
    }

    /**
     * Creates a S2C packet for spawning the entity on the client.<br>
     * Required for any entity that doesn't extend {@link net.minecraft.entity.LivingEntity LivingEntity}.<p>
     * To use, simply override {@link Entity#createSpawnPacket()} with the following:<pre>
     * &#64;Override
     * public Packet&lt;?&gt; createSpawnPacket() {
     *     return SpawnPacketUtil.createSpawnPacket(this, packetID);
     * }
     * </pre>
     * Make sure you register your {@code packetId} on the client-side with {@link adudecalledleo.lionutils.client.entity.ClientSpawnPacketUtil#register(Identifier)
     * ClientSpawnPacketUtil.register(Identifier)}!
     *
     * @param e
     *         entity to create spawn packet for
     * @param packetID
     *         spawn packet ID
     * @return a spawn packet for the specified entity
     */
    public static Packet<?> create(Entity e, Identifier packetID) {
        if (e.world.isClient)
            throw new IllegalStateException("SpawnPacketUtil.create called on the logical client!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
        byteBuf.writeUuid(e.getUuid());
        byteBuf.writeVarInt(e.getEntityId());
        PacketBufUtil.writeVec3d(byteBuf, e.getPos());
        PacketBufUtil.writeAngle(byteBuf, e.pitch);
        PacketBufUtil.writeAngle(byteBuf, e.yaw);
        return ServerSidePacketRegistry.INSTANCE.toPacket(packetID, byteBuf);
    }

}
