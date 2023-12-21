package net.unemployedgames.redstoneutilz.content.block.entities.renamer;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.util.TickableBlockEntity;
import net.unemployedgames.redstoneutilz.infrastructure.content.blocks.RootInputAndOutputSlotInventoryBlockEntity;

import java.util.Objects;

public class RenamerBlockEntity extends RootInputAndOutputSlotInventoryBlockEntity implements TickableBlockEntity {
    private String rename_to = "";
    private int progress;
    private boolean isJson = false;
    public RenamerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegisterBlockEntities.RENAMER_BLOCK_ENTITY.get(), pPos, pBlockState, Direction.UP, Direction.NORTH);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putString("rename_to", rename_to);
        pTag.putBoolean("isJson", isJson);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains("rename_to", Tag.TAG_STRING)) {
            this.rename_to = pTag.getString("rename_to");
        }if(pTag.contains("isJson")) {
            this.rename_to = pTag.getString("isJson");
        }

    }

    public void setRename_to(String rename_to) {
        if(Objects.equals(rename_to, ".json-on"))
            isJson = true;
        else if (Objects.equals(rename_to, ".json-off"))
            isJson = false;
        else
            this.rename_to = rename_to;
    }
    public String getRename_to() {
        return rename_to;
    }

    public void renameOneItem() {
        if(canName()) {
            ItemStack minus = getInputItem();
            ItemStack name = getInputItem();
            minus.shrink(1);
            setInputItem(minus);
            name.setHoverName(validateText(getRename_to()));
            name.setCount(getOutputItem().getCount() + 1);
            setOutputItem(name);
        } else LogUtils.getLogger().info("Cannot Craft");
    }

    public boolean canName() {
        return getOutputItem().isEmpty() || getOutputItem().is(getInputItem().getItem()) && getOutputItem().getCount() < 64 && getInputItem().getCount() >= 1;
    }
    public Component validateText(String str) {
        return isJson ? Component.Serializer.fromJson(str) : Component.literal(str);
    }

    @Override
    public ItemStackHandler getInputInventory() {
        return super.getInputInventory();
    }

    @Override
    public ItemStackHandler getOutputInventory() {
        return super.getOutputInventory();
    }

    public void moveItems() {
        if(!level.isClientSide()) {
            BlockPos pos = worldPosition.relative(level.getBlockState(worldPosition).getValue(HorizontalDirectionalBlock.FACING).getOpposite());
            spawnItemStack(pos.getX() + 0.5 , pos.getY() + 1.5, pos.getZ() + 0.5, level, getInputItem());
            setInputItem(ItemStack.EMPTY);
            setOutputItem(ItemStack.EMPTY);
        }
    }

    public void spawnItemStack(Double pX, Double pY, Double pZ, Level pLevel, ItemStack pStack) {
        ItemEntity itemEntity = new ItemEntity(pLevel, pX, pY, pZ, pStack);
        pLevel.addFreshEntity(itemEntity);
    }

    @Override
    public void tick() {
        if(this.level == null)
            return;
        if(getInputItem().isEmpty())
            return;
        else {
            progress++;
            if(progress >= 20) {
                renameOneItem();
                if(!getOutputItem().isEmpty())
                    moveItems();
                progress = 0;
            }
        }
    }


    //if(this.level == null)
    //            return;
    //        if(getInputItem().isEmpty())
    //            return;
    //        else {
    //            progress++;
    //            if(progress >= 10) {
    //                renameOneItem();
    //                progress = 0;
    //            }
    //        }

    //@Override
    //public void tick() {
        //renameOneItem();
    //}
}
