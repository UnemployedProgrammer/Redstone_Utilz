package net.unemployedgames.redstoneutilz.block.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.unemployedgames.redstoneutilz.block.custom.ButtonBlockRedstoneMod;
import net.unemployedgames.redstoneutilz.gui.block.CopyCatButtonConfigurationScreen;
import net.unemployedgames.redstoneutilz.util.ClientHooks;
import org.jetbrains.annotations.Nullable;

public class ConfigurableCustomButton extends ButtonBlockRedstoneMod implements EntityBlock {

    //public static final IntegerProperty TicksPressed = IntegerProperty.create("ticks_pressed", 10, 3600);
    //public static final IntegerProperty SignalStrengh = IntegerProperty.create("signal_strengh", 1, 15);

    public ConfigurableCustomButton(Properties pProperties, BlockSetType pType, int pTicksToStayPressed, boolean pArrowsCanPress) {
        super(pProperties, pType, pTicksToStayPressed, pArrowsCanPress);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return RegisterBlockEntities.CONFIGURABLE_CUSTOM_BUTTON_ENTITY.get().create(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);

        //pBuilder.add(TicksPressed);
        //pBuilder.add(SignalStrengh);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!(pHand == InteractionHand.MAIN_HAND)) return InteractionResult.PASS;
        if(pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            Boolean hasConfigItem;
            if(blockEntity instanceof ConfigurableCustomButtonEntity buttonEntity) {
                    hasConfigItem = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.REDSTONE);
                if (hasConfigItem) {
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openCopyCatButtonConfigurationScreen(pPos));
                    return InteractionResult.SUCCESS;
                } else {
                    this.signalOutputStrengh = buttonEntity.getSignalstrengh();
                    this.ticksToStayPressed = buttonEntity.getTicksdelayed();
                    this.usebtn(pState, pLevel, pPos, pPlayer, pHand, pHit);
                }
            }
        }
        return InteractionResult.PASS;
    }

    private void setStrengh(ConfigurableCustomButtonEntity configurableCustomButtonEntity, int strengh) {
        configurableCustomButtonEntity.setSignalstrengh(strengh);
        signalOutputStrengh = strengh;
    }

    private void setTicksStayPressed(ConfigurableCustomButtonEntity configurableCustomButtonEntity, int ticksstaypressed) {
        configurableCustomButtonEntity.setTicksdelayed(ticksstaypressed);
        ticksToStayPressed = ticksstaypressed;
    }
}
