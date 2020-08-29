package adudecalledleo.lionutils.block;

import adudecalledleo.lionutils.block.entity.listener.OnBrokenListener;
import adudecalledleo.lionutils.block.entity.listener.OnPlacedListener;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * <p>{@link net.minecraft.block.Block Block} that provides a {@link net.minecraft.block.entity.BlockEntity
 * BlockEntity}.</p> For a block entity's {@linkplain adudecalledleo.lionutils.block.entity.listener listener events} to
 * be called, the block that is assigned to that block entity has be a subclass of this class.
 *
 * @since 5.0.0
 */
public abstract class BaseBlockWithEntity extends BlockWithEntity {
    public BaseBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!(placer instanceof PlayerEntity))
            throw new IllegalArgumentException("Placed by non-player entity - this shouldn't be possible!");
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof OnPlacedListener)
            ((OnPlacedListener) be).onPlaced(world, pos, state, (PlayerEntity) placer, itemStack);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity,
            ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if (blockEntity instanceof OnBrokenListener)
            ((OnBrokenListener) blockEntity).onBroken(world, pos, state, player, stack);
    }
}
