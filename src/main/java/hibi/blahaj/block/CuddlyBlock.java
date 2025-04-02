package hibi.blahaj.block;

import com.mojang.serialization.*;

import hibi.blahaj.sound.BlahajSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CuddlyBlock extends HorizontalDirectionalBlock {

	protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
	public static final MapCodec<CuddlyBlock> CODEC = simpleCodec(CuddlyBlock::new);

	public CuddlyBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return CODEC;
	}

	@SuppressWarnings("null")
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	@SuppressWarnings("null")
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player,
			BlockHitResult hitResult) {
				level.playSound(null, pos, BlahajSoundEvents.getRandomSqueak(level.getRandom()), SoundSource.BLOCKS, 0.5f, 1);
		return InteractionResult.SUCCESS;
	}

	@Override
	@SuppressWarnings("null")
	protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
			level.playSound(null, hit.getBlockPos(), BlahajSoundEvents.BLOCK_CUDDLY_ITEM_HIT, SoundSource.BLOCKS, 0.5f, 1);
		super.onProjectileHit(level, state, hit, projectile);
	}



	@Override
	@SuppressWarnings("null")
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}


	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

}
