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
import net.minecraft.world.phys.BlockHitResult;

public class CopycatBlock extends Block {

    public CopycatBlock(Properties pProperties) {
        super(pProperties);
    }

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
}
