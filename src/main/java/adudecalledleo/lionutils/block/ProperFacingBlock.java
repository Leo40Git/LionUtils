package adudecalledleo.lionutils.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;

/**
 * <p>{@link Block} that faces one of six directions. Facing property is {@link #FACING}.</p>
 * Unlike {@link FacingBlock}, this {@linkplain #appendProperties(StateManager.Builder) appends the property to the
 * block} and {@linkplain #getPlacementState(ItemPlacementContext) automatically configures the placement state}.
 *
 * @see FacingBlock
 * @since 1.0.0
 */
public class ProperFacingBlock extends FacingBlock {
    public ProperFacingBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }
}
