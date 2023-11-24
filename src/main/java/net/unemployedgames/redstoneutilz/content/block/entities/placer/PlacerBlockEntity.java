package net.unemployedgames.redstoneutilz.content.block.entities.placer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.infrastructure.content.blocks.RootOneStackInventoryBlockEntity;

public class PlacerBlockEntity extends RootOneStackInventoryBlockEntity {
    public PlacerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegisterBlockEntities.PLACER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }
}
