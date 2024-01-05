package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.pages;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.Handler;
import net.unemployedgames.redstoneutilz.infrastructure.content.DownloadPlayerHead;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class Error404Screen extends Screen {

    File outputFile = new File(FMLPaths.MODSDIR.get().toFile().getParent(), "instanceplayerhead.png");
    private static ImageWidget playerHead;
    private static ImageWidget notfoundtext;
    private static Button back;
    private static ResourceLocation playerHeadLocation;

    private boolean headShake = false;

    public Error404Screen() {
        super(Component.literal("Page not Found Screen of Redstone Utils"));

        try {
            InputStream imageStream = Files.newInputStream(Path.of(outputFile.getPath()));
            NativeImage nativeImage = NativeImage.read(imageStream);
            DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            this.playerHeadLocation = textureManager.register(RedstoneMod.Mod_ID + ".player_head_image", dynamicTexture);

        } catch (Exception e) {
            this.playerHeadLocation = new ResourceLocation(RedstoneMod.Mod_ID, "textures/errhead.png");
        }
    }



    @Override
    public void init() {
        super.init();

        this.playerHead = addRenderableWidget(new ImageWidget(this.width / 2 - 50,this.height / 2 - 100 ,100,100, this.playerHeadLocation));
        this.notfoundtext = addRenderableWidget(new ImageWidget(this.width / 2 - 70, this.height / 2 + 15, 140, 30, new ResourceLocation(RedstoneMod.Mod_ID, "textures/gui/homepage/notfound.png")));
        this.back = addRenderableWidget(Button.builder(Component.translatable("ui.redstoneutilz.misc.back"), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.HOME, this.minecraft)).bounds(this.width / 2 - 50, this.height / 2 + 50, 100, 20).build());
    }

    int tickcounteruptotwenty = 0;

    @Override
    public void tick() {
        if(headShake) {
            doReturnToNormalPos();
            moveHeadToRandomPosInBounds();
        } else {
            if(!isReturnToNormalPos()) {
                doReturnToNormalPos();
            }
        }

        tickcounteruptotwenty++;
        if(tickcounteruptotwenty >= 100) {
            tickcounteruptotwenty = 0;
            if(!DownloadPlayerHead.downloaded())
                DownloadPlayerHead.downloadWithEvent(new Runnable() {
                    @Override
                    public void run() {
                        Error404Screen.this.reload();
                    }
                });
        }
        super.tick();
    }

    public void reload() {
        this.minecraft.setScreen(new Error404Screen());
    }

    private void moveHead(int x, int y) {
        this.playerHead.setX(x);
        this.playerHead.setY(y);
    }

    private Handler.GuiVec2 getHeadPos() {
        return new Handler.GuiVec2(this.playerHead.getX(), this.playerHead.getY());
    }

    private void moveHeadToRandomPosInBounds() {
        int x_max = 5;
        int x_min = -5;
        int y_max = 5;
        int y_min = -5;

        Random random = new Random();
        Handler.GuiVec2 pos = getHeadPos();

        int x = random.nextInt(x_max - x_min + 1) + x_min;
        int y = random.nextInt(y_max - y_min + 1) + y_min;

        moveHead(pos.getX() + x, pos.getY() + y);
    }

    private void doReturnToNormalPos() {
        moveHead(this.width / 2 - 50,this.height / 2 - 100);
    }

    private Handler.GuiVec2 getHeadNormalPos() {
        return new Handler.GuiVec2(this.width / 2 - 50,this.height / 2 - 100);
    }

    private boolean isReturnToNormalPos() {
        Handler.GuiVec2 pos = getHeadPos();
        int x = this.width / 2 - 50;
        int y = this.height / 2 - 100;
        return pos.getX() == x && pos.getY() == y;
    }


    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        headShake = playerHead.isMouseOver(pMouseX, pMouseY);

        // IMPORTANT: AT LAST
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void onClose() {
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.release(this.playerHeadLocation);
        super.onClose();
    }
}
