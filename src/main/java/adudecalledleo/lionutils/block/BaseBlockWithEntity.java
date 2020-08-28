package adudecalledleo.lionutils.block;

import adudecalledleo.lionutils.block.entity.OnPlacedListener;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * <p>{@link net.minecraft.block.Block Block} that provides a {@link net.minecraft.block.entity.BlockEntity
 * BlockEntity}.</p> For a block entity's {@link OnPlacedListener#onPlaced(World, BlockPos, BlockState, LivingEntity,
 * ItemStack) onPlaced} event to be called, the block that is assigned to that block entity has be a subclass of this
 * class.
 *
 * @since 5.0.0
 */
public abstract class BaseBlockWithEntity extends BlockWithEntity {
    public BaseBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof OnPlacedListener)
            ((OnPlacedListener) be).onPlaced(world, pos, state, placer, itemStack);
    }
}
