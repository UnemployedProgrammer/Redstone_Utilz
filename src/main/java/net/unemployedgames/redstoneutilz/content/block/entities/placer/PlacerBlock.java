package net.unemployedgames.redstoneutilz.content.block.entities.placer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.FireworkRocketItem;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlacerBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    //public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public PlacerBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return RegisterBlockEntities.PLACER_BLOCK_ENTITY.get().create(pPos, pState);
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
        if(isRedstoneOn) debug("rs_is_on");

        if (isRedstoneOn)
            activate(pState, pLevel, pPos);
    }

    public void activate(BlockState pState, Level pLevel, BlockPos pPos) {
        if(pLevel.getBlockEntity(pPos) instanceof PlacerBlockEntity placerBlockEntity) {
            BlockPos postoplace = pPos.relative(pState.getValue(FACING));
            Block blockItem = null;
            debug("is_be");
            if(!placerBlockEntity.getItem().isEmpty()) {
                blockItem = ((BlockItem) placerBlockEntity.getItem().getItem()).getBlock();
            }
            //boolean isAlreadyOn = pState.getValue(TRIGGERED);
            Boolean isAlreadyThere;
            if(blockItem != null)
                isAlreadyThere = pLevel.getBlockState(postoplace).is(blockItem);
            else
                isAlreadyThere = false;
            if(!pLevel.isClientSide && !placerBlockEntity.getItem().isEmpty() && !isAlreadyThere && shouldDestroy(postoplace, pLevel)) {
                debug("fn_all_if_ok");
                pLevel.destroyBlock(postoplace, true);
                if(blockItem != null) {
                    pLevel.setBlockAndUpdate(postoplace, blockItem.defaultBlockState());
                    ItemStack itemStack = placerBlockEntity.getItem().copy();
                    itemStack.setCount(itemStack.getCount() - 1);
                    placerBlockEntity.setItem(itemStack);
                }
            }
        }
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(!pLevel.isClientSide())
            if(pLevel.getBlockEntity(pPos) instanceof PlacerBlockEntity placerBlockEntity) {
                ItemStackHandler inv = placerBlockEntity.getInventory();
                for (int index = 0; index < inv.getSlots(); index++) {
                    ItemStack stack = inv.getStackInSlot(index);
                    ItemEntity itemEntity = new ItemEntity(pLevel, pPos.getX(), pPos.getY()+0.5, pPos.getZ()+0.5, stack);
                    pLevel.addFreshEntity(itemEntity);
                }
            }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        //pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(false)), 4);
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
