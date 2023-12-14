package net.unemployedgames.redstoneutilz.content.gui.block;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.block.entities.configurable_button.ConfigurableCustomButtonEntity;
import net.unemployedgames.redstoneutilz.content.block.entities.renamer.RenamerBlockEntity;
import net.unemployedgames.redstoneutilz.content.networking.PkgHandler;
import net.unemployedgames.redstoneutilz.content.networking.pkgs.SSetNbtCopyCatButtonPck;
import net.unemployedgames.redstoneutilz.content.networking.pkgs.SSetNbtRenamerBlockPkg;

@OnlyIn(Dist.CLIENT)
public class RenamerBlockConfigurationScreen extends Screen {

    public final BlockPos position;
    public final int imageWidth, imageHeight;
    private RenamerBlockEntity entity;
    private int leftpos, topPos;
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(RedstoneMod.Mod_ID, "textures/gui/copycat_button_configuration_screen/background.png");

    //GUI ELEMENTS
    private EditBox renameBox;
    private Button buttonConfirm;

    public RenamerBlockConfigurationScreen(BlockPos pos) {
        super(Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.title"));
        this.position = pos;

        this.imageWidth = 264 - 7;
        this.imageHeight = 165;
    }

    @Override
    public void tick() {
        //this.sliderButtonTickPressed.setTooltip(Tooltip.create(Component.literal(ticksToString(this.sliderButtonTickPressed.getValueInt()))));
        this.renameBox.tick();
    }

    @Override
    protected void init() {
        super.init();
        this.leftpos = (width - imageWidth) / 2;
        this.topPos = (height - imageHeight) / 2;
        if (this.minecraft == null) {
            System.out.println("Minecraft instance is null in init()");
            return;
        } //else System.out.println("Notnull minecraft instance in screen config copycat button");
        Level level = this.minecraft.level;
        if (level == null) return;

        BlockEntity be = level.getBlockEntity(this.position);
        if (be instanceof RenamerBlockEntity buttonEntity) {
            this.entity = buttonEntity;
        } else {
            return;
        }

        this.buttonConfirm = addRenderableWidget(Button.builder(Component.translatable("ui.redstoneutilz.misc.done"), this::handleButtonConfirm).bounds(this.leftpos + ((imageWidth / 2) - 50) , this.topPos + 100, 100, 20).build()); //Button Done

        this.renameBox = new EditBox(this.font, this.leftpos + ((imageWidth / 2) - 100) , this.topPos + 40, 200, 20, this.renameBox, Component.translatable("selectWorld.search"));
        this.renameBox.setCanLoseFocus(false);
        this.renameBox.setMaxLength(50);
        this.renameBox.setFocused(true);
    }

    private void handleButtonConfirm(Button button) {
        try {
            PkgHandler.INSTANCE.sendToServer(new SSetNbtRenamerBlockPkg(renameBox.getValue(), this.position));
            //this.sliderButtonTickPressed.getValueInt(), this.sliderButtonPowerStrengh.getValueInt()

        } catch (Exception e) {
            this.minecraft.setScreen(new AlertScreen(() -> System.out.println("Error screen clicked away"),
                    Component.translatable("ui.redstoneutilz.misc.error"),
                    Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.error_setfailed")));
        }
        this.onClose();
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256) {
            this.minecraft.player.closeContainer();
        }

        return !this.renameBox.keyPressed(pKeyCode, pScanCode, pModifiers) && !this.renameBox.canConsumeInput() ? super.keyPressed(pKeyCode, pScanCode, pModifiers) : true;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_TEXTURE, this.leftpos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renameBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.title"), this.leftpos + (imageWidth / 2), this.topPos + 10, 0xFFFFFF);
    }

    public boolean charTyped(char pCodePoint, int pModifiers) {
        return this.renameBox.charTyped(pCodePoint, pModifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.player.closeContainer();
    }
}