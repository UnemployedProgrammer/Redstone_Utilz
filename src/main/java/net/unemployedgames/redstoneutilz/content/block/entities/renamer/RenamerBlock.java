package net.unemployedgames.redstoneutilz.content.block.entities.renamer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;
import net.unemployedgames.redstoneutilz.content.block.entities.RegisterBlockEntities;
import net.unemployedgames.redstoneutilz.content.block.entities.configurable_button.ConfigurableCustomButton;
import net.unemployedgames.redstoneutilz.content.util.ClientHooks;
import net.unemployedgames.redstoneutilz.infrastructure.content.misc.Identifier;
import org.jetbrains.annotations.Nullable;

public class RenamerBlock extends Block implements EntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty CREATE_CHUTE_CONNECTED = BooleanProperty.create("chute");
    public static final BooleanProperty CREATE_BELT_CONNECTED = BooleanProperty.create("belt");
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");

    private static final VoxelShape BASE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape X_LEG1 = Block.box(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    private static final VoxelShape X_LEG2 = Block.box(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape X_TOP = Block.box(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape Z_LEG1 = Block.box(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    private static final VoxelShape Z_LEG2 = Block.box(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    private static final VoxelShape Z_TOP = Block.box(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape X_AXIS_AABB = Shapes.or(BASE, X_LEG1, X_LEG2, X_TOP);
    private static final VoxelShape Z_AXIS_AABB = Shapes.or(BASE, Z_LEG1, Z_LEG2, Z_TOP);

    public RenamerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return RegisterBlockEntities.RENAMER_BLOCK_ENTITY.get().create(pPos, pState);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        updateConnectionProperties(pState, pPos, pLevel);
    }

    public static void updateConnectionProperties(BlockState state, BlockPos pos, Level level) {
        boolean isChute = isSpecificBlockAbove(level, pos, new Identifier("create", "chute")) || isSpecificBlockAbove((Level) level, pos, new Identifier("create", "smart_chute"));
        boolean isBelt = isSpecificBlockAbove(level, pos, new Identifier("create", "belt"));
        boolean isPowered = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.above());

        updateBooleanBlockStateProperty(state, pos, level, CREATE_CHUTE_CONNECTED, isChute);
        updateBooleanBlockStateProperty(state, pos, level, CREATE_BELT_CONNECTED, isBelt);
        updateBooleanBlockStateProperty(state, pos, level, POWERED, isPowered);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHooks.openRenamerBlockConfigurationScreen(pPos));
        return InteractionResult.SUCCESS;
    }

    public static void updateBooleanBlockStateProperty(BlockState blockState, BlockPos blockPos, Level world, BooleanProperty property, boolean val) {
        BlockState newblockstate = blockState.setValue(property, val);
        world.setBlockAndUpdate(blockPos, newblockstate);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return direction == Direction.NORTH;
    }

    public static boolean isSpecificBlockAbove(Level world, BlockPos pos, Identifier blockRegistryName) {
        Block blockAbove = world.getBlockState(pos).getBlock();
        Block blockToCheck = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blockRegistryName.getNamespace(), blockRegistryName.getObject()));
        return blockAbove == blockToCheck;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(POWERED);
        pBuilder.add(CREATE_CHUTE_CONNECTED);
        pBuilder.add(CREATE_BELT_CONNECTED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getClockWise());
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
}
