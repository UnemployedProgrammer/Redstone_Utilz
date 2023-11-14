package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage;

import com.mojang.text2speech.NarratorWindows;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SideBarButton extends Button {
    private SideBarImg img;
    public static CreateNarration DEF_NARR = DEFAULT_NARRATION;

    public static class SideBarImg {
        private ResourceLocation img;
        private int Width;
        private int Height;
        public SideBarImg(ResourceLocation image, int width, int height) {
            this.img = image;
            this.Width = width;
            this.Height = height;
        }

        public int getImgHeight() {
            return Height;
        }

        public int getImgWidth() {
            return Width;
        }

        public ResourceLocation getImg() {
            return img;
        }
    }

    public SideBarButton(int pX, int pY, int pWidth, int pHeight, Component pMessage, SideBarImg image, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, pMessage, pOnPress, DEFAULT_NARRATION);
        this.img = image;
    }

    protected SideBarButton(Builder builder) {
        super(builder);
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.blit(img.getImg(), this.getX() + 5, this.getY() + (this.getHeight() / 2 - this.img.getImgHeight() / 2), 0, 0, img.getImgWidth(), img.getImgHeight(), img.getImgWidth(), img.getImgHeight());
    }
}
