package adudecalledleo.lionutils.entity;

import adudecalledleo.lionutils.InitializerUtil;
import adudecalledleo.lionutils.network.PacketBufUtil;
import adudecalledleo.lionutils.RequiresFabricAPI;
import adudecalledleo.lionutils.internal.fapibridge.FAPIBridgeProvider;
import adudecalledleo.lionutils.internal.fapibridge.network.PacketContextBridge;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
 * @since 3.0.0
 */
@RequiresFabricAPI
public final class SpawnPacketUtil {
    private SpawnPacketUtil() {
        InitializerUtil.badConstructor();
    }

    /**
     * <p>Creates a S2C packet for spawning the entity on the client.<br>
     * Required for any entity that doesn't extend {@link net.minecraft.entity.LivingEntity LivingEntity}.</p>
     * To use, simply override {@link Entity#createSpawnPacket()} with the following:<pre>
     * &#64;Override
     * public Packet&lt;?&gt; createSpawnPacket() {
     *     return EntityUtil.createSpawnPacket(this, packetID);
     * }
     * </pre>
     * Make sure you register your {@code packetId} on the client-side with {@link #register(Identifier)}!
     * @param e entity to create spawn packet for
     * @param packetID spawn packet ID
     * @return a spawn packet for the specified entity
     * @author UpcraftLP
     */
    public static Packet<?> create(Entity e, Identifier packetID) {
        if (e.world.isClient)
            throw new IllegalStateException("SpawnPacketUtil.create called from client-side!");
        PacketByteBuf byteBuf = new PacketByteBuf(Unpooled.buffer());
        byteBuf.writeVarInt(Registry.ENTITY_TYPE.getRawId(e.getType()));
        byteBuf.writeUuid(e.getUuid());
        byteBuf.writeVarInt(e.getEntityId());
        PacketBufUtil.writeVec3d(byteBuf, e.getPos());
        PacketBufUtil.writeAngle(byteBuf, e.pitch);
        PacketBufUtil.writeAngle(byteBuf, e.yaw);
        return FAPIBridgeProvider.PacketRegistry.SERVER.toPacket(packetID, byteBuf);
    }

    // @author UpcraftLP
    @SuppressWarnings("ConstantConditions")
    @Environment(EnvType.CLIENT)
    private static void consumeSpawnPacket(PacketContextBridge ctx, PacketByteBuf byteBuf) {
        EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
        UUID uuid = byteBuf.readUuid();
        int entityId = byteBuf.readVarInt();
        Vec3d pos = PacketBufUtil.readVec3d(byteBuf);
        float pitch = PacketBufUtil.readAngle(byteBuf);
        float yaw = PacketBufUtil.readAngle(byteBuf);
        ctx.getTaskQueue().execute(() -> {
            ClientWorld world = MinecraftClient.getInstance().world;
            Entity e = et.create(world);
            EntityUtil.setPos(e, pos);
            e.pitch = pitch;
            e.yaw = yaw;
            e.setEntityId(entityId);
            e.setUuid(uuid);
            world.addEntity(entityId, e);
        });
    }

    /**
     * Registers a handler for an entity spawn packet received from the server.<br>
     * Should be used in tandem with {@link #create(Entity, Identifier)} on the server-side.
     * @param packetID spawn packet ID
     * @author UpcraftLP
     */
    @Environment(EnvType.CLIENT)
    public static void register(Identifier packetID) {
        FAPIBridgeProvider.PacketRegistry.CLIENT.register(packetID, SpawnPacketUtil::consumeSpawnPacket);
    }
}
