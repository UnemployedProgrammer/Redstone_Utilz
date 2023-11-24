package net.unemployedgames.redstoneutilz.content.block.entities.display;

import com.sun.jna.platform.unix.solaris.LibKstat;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.util.TickableBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class DisplayBlockEntity extends BlockEntity implements TickableBlockEntity {
    private HashMap<String, String> TEXT = new HashMap<String, String>();
    private HashMap<String, String> DATA = new HashMap<String, String>();
    private HashMap<String, String> UUDIS_FOR_TEXTS_AND_DATA = new HashMap<String, String>();

    public DisplayBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(RegisterBlockEntities.DISPLAY_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        CompoundTag text = pTag.getCompound("text");
        CompoundTag data = pTag.getCompound("data");
        CompoundTag uuids = pTag.getCompound("uuids");

        for (String textAllKey : text.getAllKeys()) {
            TEXT.put(textAllKey, text.getString(textAllKey));
        }
        for (String textAllKey : data.getAllKeys()) {
            DATA.put(textAllKey, data.getString(textAllKey));
        }
        for (String textAllKey : uuids.getAllKeys()) {
            UUDIS_FOR_TEXTS_AND_DATA.put(textAllKey, uuids.getString(textAllKey));
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        CompoundTag text = new CompoundTag();
        CompoundTag data = new CompoundTag();
        CompoundTag uuids = new CompoundTag();
        if(TEXT.isEmpty() || DATA.isEmpty() || UUDIS_FOR_TEXTS_AND_DATA.isEmpty()) {
            text.putString("_", "_");
            data.putString("_", "_");
            uuids.putString("_", "_");
        } else {
            for (String i : TEXT.keySet()) {
                text.putString(i, TEXT.get(i));
            }
            for (String i : DATA.keySet()) {
                data.putString(i, DATA.get(i));
            }
            for (String i : UUDIS_FOR_TEXTS_AND_DATA.keySet()) {
                uuids.putString(i, UUDIS_FOR_TEXTS_AND_DATA.get(i));
            }
        }

        pTag.put("text", text);
        pTag.put("data", data);
        pTag.put("uuids", uuids);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    //public CompoundTag getLines() {
    //    return ;
    //}

    public void addLine(String key, String text, int fontSize, ChatFormatting textColor, int row) {
        String uuid = UUID.randomUUID().toString();
        TEXT.put(uuid, text);
        DATA.put(uuid, encodeData(fontSize, textColor, row));
        UUDIS_FOR_TEXTS_AND_DATA.put(key, uuid);
        setChanged();
    }

    public void removeLine(String key) {
        String uuid = getUUIDforKey(key);
        TEXT.remove(uuid);
        DATA.remove(uuid);
        UUDIS_FOR_TEXTS_AND_DATA.remove(key);
        setChanged();
    }
    // Does not work currently
    public void clearScreen() {
        //
        setChanged();
    }

    public String getUUIDforKey(String key) {
        return UUDIS_FOR_TEXTS_AND_DATA.get(key);
    }

    // Does not work currently
    public boolean unsafeAddLine(String text, int fontSize, ChatFormatting textColor) {
        Boolean worked;
        try {
            //
            setChanged();
            worked = true;
        } catch(Exception e) {
            worked = false;
        }
        return worked;
    }
    // Does not work currently
    public boolean unsafeRemoveLine(int index) {
        Boolean worked;
        try {
            //
            setChanged();
            worked = true;
        } catch(Exception e) {
            worked = false;
        }
        return worked;
    }
    // Does not work currently
    public boolean unsafeClearScreen() {
        Boolean worked;
        try {
            //
            worked = true;
        } catch(Exception e) {
            worked = false;
        }
        return worked;
    }

    public String encodeData(int fontSize, ChatFormatting textColor, int row) {
        return "fontsize:"+fontSize+"|textcolor:"+textColor.getName()+"|row:"+row;
    }

    @Override
    public void tick() {
        if(this.level == null || this.level.isClientSide())
            return;

        setChanged();

        this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt); // Call the superclass method to handle the default behavior.
        CompoundTag pTag = pkt.getTag();
        CompoundTag text = pTag.getCompound("text");
        CompoundTag data = pTag.getCompound("data");
        CompoundTag uuids = pTag.getCompound("uuids");

        for (String textAllKey : text.getAllKeys()) {
            TEXT.put(textAllKey, text.getString(textAllKey));
        }
        for (String textAllKey : data.getAllKeys()) {
            DATA.put(textAllKey, data.getString(textAllKey));
        }
        for (String textAllKey : uuids.getAllKeys()) {
            UUDIS_FOR_TEXTS_AND_DATA.put(textAllKey, uuids.getString(textAllKey));
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        load(tag);
    }
}
