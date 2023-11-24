package net.unemployedgames.redstoneutilz.content.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.unemployedgames.redstoneutilz.content.block.entities.display.DisplayHelper;

public class CopycatBlock extends Block {

    public CopycatBlock(Properties pProperties) {
        super(pProperties);
    }

    public static Boolean debug = true;

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if(pHand == InteractionHand.MAIN_HAND && !pPlayer.getItemInHand(pHand).isEmpty()) {
            ItemStack itemInHand = pPlayer.getItemInHand(pHand);

            if (itemInHand.getItem() instanceof BlockItem) {
                Block blockItem = ((BlockItem) itemInHand.getItem()).getBlock();
                pLevel.setBlockAndUpdate(pPos, blockItem.defaultBlockState());
            }

            //pLevel.setBlock(pPos, )
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
        System.out.println("1st:" + DisplayHelper.get1stDisplayPosition_Y(pLevel, pPos));
        System.out.println("2st:" + DisplayHelper.get2stDisplayPosition_Y(pLevel, pPos));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        System.out.println("1st:" + DisplayHelper.get1stDisplayPosition_Y(pLevel, pPos));
        System.out.println("2st:" + DisplayHelper.get2stDisplayPosition_Y(pLevel, pPos));
    }
}
