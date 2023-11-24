package net.unemployedgames.redstoneutilz.content.block.entities.display;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.block.entities.placer.PlacerBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DisplayBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = DirectionalBlock.FACING;
    //public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    public DisplayBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return RegisterBlockEntities.DISPLAY_BLOCK_ENTITY.get().create(pPos, pState);
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
        return false;
    }

    public void debug(String msg) {
        System.out.println(msg);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

    public void activate(BlockState pState, Level pLevel, BlockPos pPos, Player player) {
        if(pLevel.getBlockEntity(pPos) instanceof DisplayBlockEntity displayBlockEntity) {
            if(!pLevel.isClientSide()) {
                if(player.isShiftKeyDown()) {
                    displayBlockEntity.removeLine("display_headline");
                    displayBlockEntity.removeLine("display_space1");
                    displayBlockEntity.removeLine("display_group1");
                    displayBlockEntity.removeLine("display_group2");
                    displayBlockEntity.removeLine("display_group3");
                } else {
                    displayBlockEntity.addLine("display_headline", "Storage System", 30, ChatFormatting.GREEN, 1);
                    displayBlockEntity.addLine("display_space1", "", 30, ChatFormatting.WHITE, 2);
                    displayBlockEntity.addLine("display_group1", "  - Chests: 20", 15, ChatFormatting.WHITE, 3);
                    displayBlockEntity.addLine("display_group2", "  - Tubes: 345", 15, ChatFormatting.WHITE, 4);
                    displayBlockEntity.addLine("display_group3", "  - Wood: 10245211", 15, ChatFormatting.WHITE, 5);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(pHand == InteractionHand.MAIN_HAND) {
            activate(pState, pLevel, pPos, pPlayer);
            pPlayer.sendSystemMessage(Component.literal("X Start: "+ DisplayHelper.get1stDisplayPosition_X_OR_Y(pLevel, pPos, pState.getValue(FACING)).z_y));
            pPlayer.sendSystemMessage(Component.literal("X End: "+ DisplayHelper.get2stDisplayPosition_X_OR_Y(pLevel, pPos, pState.getValue(FACING)).z_y));
            pPlayer.sendSystemMessage(Component.literal("X Count: "+ DisplayHelper.countXAmountDisplays(pLevel, pPos, pState.getValue(FACING))));
            pPlayer.sendSystemMessage(Component.literal(""));
            pPlayer.sendSystemMessage(Component.literal("Y Start: "+ DisplayHelper.get1stDisplayPosition_Y(pLevel, pPos)));
            pPlayer.sendSystemMessage(Component.literal("Y End: "+ DisplayHelper.get2stDisplayPosition_Y(pLevel, pPos)));
            pPlayer.sendSystemMessage(Component.literal("Y Count: "+ DisplayHelper.countYAmountDisplays(pLevel, pPos)));
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction dir = pContext.getNearestLookingDirection().getOpposite();
        if(dir == Direction.UP || dir == Direction.DOWN)
            dir = Direction.NORTH;

        return this.defaultBlockState().setValue(FACING, dir);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
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
