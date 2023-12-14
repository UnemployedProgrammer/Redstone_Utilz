package net.unemployedgames.redstoneutilz.content.networking.pkgs;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import net.unemployedgames.redstoneutilz.content.block.entities.configurable_button.ConfigurableCustomButtonEntity;
import net.unemployedgames.redstoneutilz.content.block.entities.renamer.RenamerBlockEntity;

import javax.sql.rowset.serial.SerialStruct;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class SSetNbtRenamerBlockPkg {
    private final String rename_to;
    private final BlockPos entity;

    public SSetNbtRenamerBlockPkg(String rename_to, BlockPos entity) {
        this.entity = entity;
        this.rename_to = rename_to;
    }

    public SSetNbtRenamerBlockPkg(FriendlyByteBuf buf) {
        this(buf.readComponent().getString(), buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeComponent(Component.literal(rename_to));
        buf.writeBlockPos(this.entity);
    }

    public boolean handle(Supplier<NetworkEvent.Context> context) {
        final var sucess = new AtomicBoolean(false);

        context.get().enqueueWork(() -> {
            BlockEntity bentity = context.get().getSender().level().getBlockEntity(entity);

            if (bentity instanceof RenamerBlockEntity renamerBlockEntity) {
                renamerBlockEntity.setRename_to(this.rename_to);
                sucess.set(true);
            }
        });
        context.get().setPacketHandled(true);
        return sucess.get();
    }
}
