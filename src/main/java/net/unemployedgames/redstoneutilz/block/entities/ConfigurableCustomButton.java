package net.unemployedgames.redstoneutilz.block.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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

    //public static final IntegerProperty SignalStrengh = IntegerProperty.create("signal_strengh", 1, 15);
    public static final EnumProperty<Wood_Types_Vanilla> TYPE = EnumProperty.create("wood_type", Wood_Types_Vanilla.class);
    public static enum Wood_Types_Vanilla implements StringRepresentable {
        NOT_SET("not_set"),
        OAK("oak"),
        SPRUCE("spruce"),
        BIRCH("birch"),
        JUNGLE("jungle"),
        ACACIA("acacia"),
        DARK_OAK("darkoak"),
        MANGROVE("mangrove"),
        CHERRY("cherry"),
        CRIMSON("crimson"),
        WARPED("warped")
        ;

        private final String name;

        @Override
        public String getSerializedName() {
            return this.name;
        }
        Wood_Types_Vanilla(String name) {
            this.name = name;
        }
    }


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
        pBuilder.add(TYPE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!(pHand == InteractionHand.MAIN_HAND)) return InteractionResult.PASS;
        if(pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            Boolean hasConfigItem;
            if(blockEntity instanceof ConfigurableCustomButtonEntity buttonEntity) {
                this.signalStrengh = buttonEntity.getSignalstrengh();
                this.ticksToStayPressed = buttonEntity.getTicksdelayed();
                hasConfigItem = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.REDSTONE);
                if (hasConfigItem) {
                    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openCopyCatButtonConfigurationScreen(pPos));
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            Boolean hasConfigItem;
            if(blockEntity instanceof ConfigurableCustomButtonEntity buttonEntity) {
                this.signalStrengh = buttonEntity.getSignalstrengh();
                this.ticksToStayPressed = buttonEntity.getTicksdelayed();
                hasConfigItem = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(Items.REDSTONE);
                if (!hasConfigItem && getWoodTypeFromPlayerHandString(pPlayer, pHand) == "_" && !pPlayer.isShiftKeyDown()) {
                    super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
                    System.out.println("Button Vals, S: " + this.signalStrengh + " | T: " + this.ticksToStayPressed);
                } else if(!(getWoodTypeFromPlayerHandString(pPlayer, pHand) == "_")) {
                    String strtype = getWoodTypeFromPlayerHandString(pPlayer, pHand);
                    if(!(getBlockStateType(pState, pPos, pLevel).name == Wood_Types_Vanilla.NOT_SET.name))
                        dropItemFromLast(pState, pPos, pLevel, getBlockStateType(pState, pPos, pLevel));

                    if(strtype == Wood_Types_Vanilla.OAK.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.OAK);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.SPRUCE.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.SPRUCE);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.BIRCH.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.BIRCH);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.JUNGLE.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.JUNGLE);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.ACACIA.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.ACACIA);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.DARK_OAK.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.DARK_OAK);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.MANGROVE.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.MANGROVE);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.CHERRY.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.CHERRY);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.CRIMSON.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.CRIMSON);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.WARPED.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.WARPED);
                        removeOneItemFromCurrentPlayerInHandItemStack(pPlayer, pHand);
                    }
                    if(strtype == Wood_Types_Vanilla.NOT_SET.name) {
                        updateBlockState(pState, pPos, pLevel, Wood_Types_Vanilla.NOT_SET);
                    }
                }
            }

        }
        return InteractionResult.SUCCESS;
    }

    public static void updateBlockState(BlockState blockState, BlockPos blockPos, Level world, Wood_Types_Vanilla val) {
        BlockState newblockstate = blockState.setValue(TYPE, val);
        world.setBlockAndUpdate(blockPos, newblockstate);
    }
    public static Wood_Types_Vanilla getBlockStateType(BlockState blockState, BlockPos blockPos, Level world) {
        Wood_Types_Vanilla newblockstate = blockState.getValue(TYPE);
        return newblockstate;
    }

    public static void dropItemFromLast(BlockState blockState, BlockPos blockPos, Level world, Wood_Types_Vanilla val) {
        ItemStack itemStack = null;
        ItemEntity item = null;

        if(val == Wood_Types_Vanilla.OAK)
            itemStack = new ItemStack(Blocks.OAK_LOG);
        if(val == Wood_Types_Vanilla.SPRUCE)
            itemStack = new ItemStack(Blocks.SPRUCE_LOG);
        if(val == Wood_Types_Vanilla.BIRCH)
            itemStack = new ItemStack(Blocks.BIRCH_LOG);
        if(val == Wood_Types_Vanilla.JUNGLE)
            itemStack = new ItemStack(Blocks.JUNGLE_LOG);
        if(val == Wood_Types_Vanilla.ACACIA)
            itemStack = new ItemStack(Blocks.ACACIA_LOG);
        if(val == Wood_Types_Vanilla.DARK_OAK)
            itemStack = new ItemStack(Blocks.DARK_OAK_LOG);
        if(val == Wood_Types_Vanilla.MANGROVE)
            itemStack = new ItemStack(Blocks.MANGROVE_LOG);
        if(val == Wood_Types_Vanilla.CHERRY)
            itemStack = new ItemStack(Blocks.CHERRY_LOG);
        if(val == Wood_Types_Vanilla.CRIMSON)
            itemStack = new ItemStack(Blocks.CRIMSON_STEM);
        if(val == Wood_Types_Vanilla.WARPED)
            itemStack = new ItemStack(Blocks.WARPED_STEM);

        if(itemStack != new ItemStack(Items.DIRT))
            item = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, itemStack);


        if(!(null == item)&&!(null == itemStack)){
            item = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, itemStack);
            world.addFreshEntity(item);

            item.setDeltaMovement(0.0, 0.2, 0.0);
        }
    }

    public static String getWoodTypeFromPlayerHandString(Player pPlayer, InteractionHand pHand) {
        String output = "_";

        if (pPlayer.getItemInHand(pHand).getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() == Blocks.OAK_LOG) {
                output = "oak";
            }
            if (blockItem.getBlock() == Blocks.SPRUCE_LOG) {
                output = "spruce";
            }
            if (blockItem.getBlock() == Blocks.BIRCH_LOG) {
                output = "birch";
            }
            if (blockItem.getBlock() == Blocks.JUNGLE_LOG) {
                output = "jungle";
            }
            if (blockItem.getBlock() == Blocks.ACACIA_LOG) {
                output = "acacia";
            }
            if (blockItem.getBlock() == Blocks.DARK_OAK_LOG) {
                output = "darkoak";
            }
            if (blockItem.getBlock() == Blocks.MANGROVE_LOG) {
                output = "mangrove";
            }
            if (blockItem.getBlock() == Blocks.CHERRY_LOG) {
                output = "cherry";
            }
            if (blockItem.getBlock() == Blocks.CRIMSON_STEM) {
                output = "crimson";
            }
            if (blockItem.getBlock() == Blocks.WARPED_STEM) {
                output = "warped";
            }
        }
        if (pPlayer.getItemInHand(pHand).isEmpty() && pPlayer.isShiftKeyDown()) {
            output = "not_set";
        }
        return output;
    }

    private void removeOneItemFromCurrentPlayerInHandItemStack(Player player, InteractionHand interactionHand) {
        if(player.isCreative())
            return;

        player.getItemInHand(interactionHand).setCount(player.getItemInHand(interactionHand).getCount() - 1);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);

        if(pLevel.isEmptyBlock(pPos))
            if(!(getBlockStateType(pState, pPos, pLevel).name == Wood_Types_Vanilla.NOT_SET.name))
                dropItemFromLast(pState, pPos, pLevel, getBlockStateType(pState, pPos, pLevel));

        //if(!(getBlockStateType(pState, pPos, pLevel).name == Wood_Types_Vanilla.NOT_SET.name))
            //dropItemFromLast(pState, pPos, pLevel, getBlockStateType(pState, pPos, pLevel));
    }

    private void setStrengh(ConfigurableCustomButtonEntity configurableCustomButtonEntity, int strengh) {
        configurableCustomButtonEntity.setSignalstrengh(strengh);
        signalStrengh = strengh;
    }

    private void setTicksStayPressed(ConfigurableCustomButtonEntity configurableCustomButtonEntity, int ticksstaypressed) {
        configurableCustomButtonEntity.setTicksdelayed(ticksstaypressed);
        ticksToStayPressed = ticksstaypressed;
    }
}
