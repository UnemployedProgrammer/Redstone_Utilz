package net.unemployedgames.redstoneutilz.content.block.entities.destroyer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.block.entities.placer.PlacerBlockEntity;
import net.unemployedgames.redstoneutilz.content.util.TickableBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class DestroyerBlock extends Block {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public static final AtomicInteger NEXT_BREAKER_ID = new AtomicInteger();
    protected int breakerId = -NEXT_BREAKER_ID.incrementAndGet();
    private double destroyProgress = 0;
    private int ind = 0;
    private boolean activated;
    private int speed = 20;  // 15 == SLOW, 1 == REALLY FAST(INSTANT)
    private int speedProgress;



    public DestroyerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    public void debug(String msg) {
        System.out.println(msg);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        boolean isRedstoneOn = pLevel.hasNeighborSignal(pPos) || pLevel.hasNeighborSignal(pPos.above());

        if(isRedstoneOn && !pLevel.isClientSide && shouldDestroy(pPos.relative(pState.getValue(FACING)), pLevel))
            activate(pState, pLevel, pPos);
    }

    public void activate(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.destroyBlock(pPos.relative(pState.getValue(FACING)), true);
    }

    Block[] badblocks = {
            Blocks.BEDROCK,
            Blocks.OBSIDIAN,
            Blocks.FURNACE,
            Blocks.WATER,
            Blocks.LAVA
    };

    public Boolean shouldDestroy(BlockPos pos, Level lvl) {
        for (int i = 0; i < badblocks.length; i++) {
            if(lvl.getBlockState(pos).is(badblocks[i]))
                return false;
            else
                return true;
        }
        return false;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        //pBuilder.add(TRIGGERED);
    }
}
