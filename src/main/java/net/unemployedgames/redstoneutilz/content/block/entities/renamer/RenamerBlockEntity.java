package net.unemployedgames.redstoneutilz.content.block.entities.renamer;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.util.TickableBlockEntity;
import net.unemployedgames.redstoneutilz.infrastructure.content.blocks.RootInputAndOutputSlotInventoryBlockEntity;

public class RenamerBlockEntity extends RootInputAndOutputSlotInventoryBlockEntity {
    private String rename_to = "";
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
        this.rename_to = rename_to;
    }
    public String getRename_to() {
        return rename_to;
    }

    public void renameOneItem() {
        if(canName()) {
            ItemStack minus = getInputItem();
            ItemStack name = getInputItem();
            minus.setCount(minus.getCount() - 1);
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

    //@Override
    //public void tick() {
        //renameOneItem();
    //}
}
