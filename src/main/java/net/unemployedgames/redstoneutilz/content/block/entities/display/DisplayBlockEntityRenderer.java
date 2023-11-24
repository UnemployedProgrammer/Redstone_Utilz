package net.unemployedgames.redstoneutilz.content.block.entities.display;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignText;

import java.util.List;

public class DisplayBlockEntityRenderer implements BlockEntityRenderer<DisplayBlockEntity> {
    private final Font font;

    public DisplayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(DisplayBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Font fontRenderer = Minecraft.getInstance().font;

        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 1f, 0.5f);
        pPoseStack.scale(0.5f, 0.5f, 0.5f);

        //renderTextLine(pBlockEntity.getBlockPos(), pBlockEntity.getLevel(), Direction.NORTH, Component.literal("Test"), pPoseStack, 100, 100, pBuffer, 0);
        Minecraft.getInstance().getItemRenderer().renderStatic(new ItemStack(Items.REDSTONE), ItemDisplayContext.FIXED, getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    private void renderTextLine(BlockPos pPos, Level lvl, Direction direction, Component pText, PoseStack pPoseStack, int pMaxWidth, int pMaxHeight, MultiBufferSource pBuffer, int pLineHeight) {
        float f = (float)(-this.font.width(pText) / 2);
        this.font.drawInBatch(pText, f, pLineHeight, 0xFFFFFF, false, pPoseStack.last().pose(), pBuffer, Font.DisplayMode.POLYGON_OFFSET, 0xFFFFFF, getLightLevel(lvl, pPos));
    }
}
