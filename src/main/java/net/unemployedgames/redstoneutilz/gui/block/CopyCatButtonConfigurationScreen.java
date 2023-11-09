package net.unemployedgames.redstoneutilz.gui.block;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.client.gui.widget.ScrollPanel;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.block.entities.ConfigurableCustomButtonEntity;
import net.unemployedgames.redstoneutilz.networking.PkgHandler;
import net.unemployedgames.redstoneutilz.networking.pkgs.SSetNbtCopyCatButtonPck;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.PanelUI;

public class CopyCatButtonConfigurationScreen extends Screen {

    public final BlockPos position;
    public final int imageWidth, imageHeight;
    private ConfigurableCustomButtonEntity entity;
    private int leftpos, topPos;
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(RedstoneMod.Mod_ID, "textures/gui/copycat_button_configuration_screen/background.png");

    //GUI ELEMENTS
    private ForgeSlider sliderButtonTickPressed;
    private ForgeSlider sliderButtonPowerStrengh;
    private Button buttonConfirm;

    public CopyCatButtonConfigurationScreen(BlockPos pos) {
        super(Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.title"));
        this.position = pos;

        this.imageWidth = 264 - 7;
        this.imageHeight = 165;
    }

    @Override
    public void tick() {
        this.sliderButtonTickPressed.setTooltip(Tooltip.create(Component.literal(ticksToString(this.sliderButtonTickPressed.getValueInt()))));
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
        if (be instanceof ConfigurableCustomButtonEntity buttonEntity) {
            this.entity = buttonEntity;
        } else {
            return;
        }

        this.buttonConfirm = addRenderableWidget(Button.builder(Component.translatable("ui.redstoneutilz.misc.done"), this::handleButtonConfirm).bounds(this.leftpos + ((imageWidth / 2) - 50) , this.topPos + 100, 100, 20).build()); //Button Done

        this.sliderButtonTickPressed = addRenderableWidget(new ForgeSlider(this.leftpos + ((imageWidth / 2) - (200 / 2)), this.topPos + 30, 200, 20, Component.literal(""), Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.components.slider_tickspressed.label"), 0, 3600, this.entity.getTicksdelayed(), true)); //Slider Ticks Delayed
        this.sliderButtonPowerStrengh = addRenderableWidget(new ForgeSlider(this.leftpos + ((imageWidth / 2) - (200 / 2)), this.topPos + 60, 200, 20, Component.literal(""), Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.components.slider_signalstrengh.label"), 1, 15, this.entity.getSignalstrengh(), true)); //Slider Signal Strengh
    }

    private void handleButtonConfirm(Button button) {
        try {
            PkgHandler.INSTANCE.sendToServer(new SSetNbtCopyCatButtonPck(this.sliderButtonTickPressed.getValueInt(), this.sliderButtonPowerStrengh.getValueInt(), this.position));
            //this.sliderButtonTickPressed.getValueInt(), this.sliderButtonPowerStrengh.getValueInt()

        } catch (Exception e) {
            this.minecraft.setScreen(new AlertScreen(() -> System.out.println("Error screen clicked away"),
                    Component.translatable("ui.redstoneutilz.misc.error"),
                    Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.error_setfailed")));
        }
        this.onClose();
    }

    public static String ticksToString(int ticks) {
        if (ticks < 20) {
            return "%n Ticks".replace("%n", String.valueOf(ticks));
        }

        int numSeconds = ticks / 20;
        if (numSeconds < 60) {
            return numSeconds + " Sec.";
        } else {
            int minutes = numSeconds / 60;
            int seconds = numSeconds % 60;
            return minutes + " Min. and " + seconds + " Sec.";
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND_TEXTURE, this.leftpos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("ui.redstoneutilz.copycatconfigurationscreen.title"), this.leftpos + (imageWidth / 2), this.topPos + 10, 0xFFFFFF);
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