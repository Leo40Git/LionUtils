package adudecalledleo.lionutils.block.entity.listener;

import adudecalledleo.lionutils.block.BaseBlockWithEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * <p>Represents a listener to the attached block's {@link Block#afterBreak(World, PlayerEntity, BlockPos, BlockState,
 * BlockEntity, ItemStack)} afterBreak} event.</p>
 * <strong>NOTE:</strong> For this to be called, the attached block has to extend {@link BaseBlockWithEntity}!
 *
 * @since 5.0.0
 */
@FunctionalInterface
public interface OnBrokenListener {
    /**
     * Called when the attached block is broken.
     *
     * @param world
     *         world block was broken in
     * @param pos
     *         position of block
     * @param state
     *         state of block
     * @param breaker
     *         player that broke the block
     * @param itemStack
     *         stack player was holding while breaking the block
     */
    void onBroken(World world, BlockPos pos, BlockState state, PlayerEntity breaker, ItemStack itemStack);
}
