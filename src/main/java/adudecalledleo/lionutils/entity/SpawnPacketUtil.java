package adudecalledleo.lionutils.entity;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.RequiresFabricAPI;
import adudecalledleo.lionutils.network.PacketBufUtil;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

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
     *     return EntityUtil.createSpawnPacket(this, packetID);
     * }
     * </pre>
     * Make sure you register your {@code packetId} on the client-side with {@link #register(Identifier)}!
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

    /**
     * Registers a handler for an entity spawn packet received from the server.<br>
     * Should be used in tandem with {@link #create(Entity, Identifier)} on the server-side.
     *
     * @param packetID
     *         spawn packet ID
     */
    // @author UpcraftLP
    @Environment(EnvType.CLIENT)
    public static void register(Identifier packetID) {
        ClientSidePacketRegistry.INSTANCE.register(packetID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = PacketBufUtil.readVec3d(byteBuf);
            float pitch = PacketBufUtil.readAngle(byteBuf);
            float yaw = PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                ClientWorld world = MinecraftClient.getInstance().world;
                if (world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                EntityUtil.setPos(e, pos);
                e.pitch = pitch;
                e.yaw = yaw;
                e.setEntityId(entityId);
                e.setUuid(uuid);
                world.addEntity(entityId, e);
            });
        });
    }
}
