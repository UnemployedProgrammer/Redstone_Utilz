package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.pages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.gui.AllIcons;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.Handler;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.SideBarButton;

public class Versions extends Screen {
    public static ImageWidget sideBarBackground;
    public static ResourceLocation sideBarBackgroundImg = new ResourceLocation(RedstoneMod.Mod_ID, "textures/gui/homepage/sidebar.png"); //DETAILS: <->: 106
    //public static ScrollPanel scrollPanel;
    public AbstractWidget[] widgets = new AbstractWidget[]{
            sidebarBtnHomeMain,
            sidebarBtnWiki,
            sidebarBtnSettings,
            sidebarBtnVersions,
            sidebarBtnReport_Isuues,
            sidebarBtnGithub,
            sidebarBtnSocialMediaAndModDownloadPlattforms
    };
    //public UpAndDownButtons upAndDownButtons = new UpAndDownButtons(new Handler.GuiVec2(90, 10), new Handler.GuiVec2(90, 210), up, down);
    public static SideBarButton sidebarBtnHomeMain;
    public static SideBarButton sidebarBtnWiki;
    public static SideBarButton sidebarBtnSettings;
    public static SideBarButton sidebarBtnVersions;
    public static SideBarButton sidebarBtnReport_Isuues;
    public static SideBarButton sidebarBtnGithub;
    public static SideBarButton sidebarBtnSocialMediaAndModDownloadPlattforms;

    //Content
    //EMPTY


    //Animate
    private int animationTick_FadeIn;
    private int animationX_FadeIn;
    private boolean animating_FadeIn;

    //PageContents

    public Versions() {
        super(Component.literal("Versions of Redstone Utils"));
    }


    @Override
    public void init() {
        super.init();

        //SideBar
        this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 30, 86, 22, Component.translatable("ui.redstoneutilz.homepage.home"), new SideBarButton.SideBarImg(AllIcons.HOME, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.HOME, Minecraft.getInstance())));
        this.sidebarBtnWiki = addRenderableWidget(new SideBarButton(7, 57, 86, 22, Component.translatable("ui.redstoneutilz.homepage.wiki"), new SideBarButton.SideBarImg(AllIcons.WIKI, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.WIKI, Minecraft.getInstance())));
        this.sidebarBtnSettings = addRenderableWidget(new SideBarButton(7, 84, 86, 22, Component.translatable("ui.redstoneutilz.homepage.settings"), new SideBarButton.SideBarImg(AllIcons.SETTINGS, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.SETTINGS, Minecraft.getInstance())));
        this.sidebarBtnVersions = addRenderableWidget(new SideBarButton(7, 111, 89, 22, Component.translatable("ui.redstoneutilz.homepage.versions"), new SideBarButton.SideBarImg(AllIcons.VERSIONS, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.VERSIONS, Minecraft.getInstance())));
        this.sidebarBtnReport_Isuues = addRenderableWidget(new SideBarButton(7, 138, 86, 22, Component.translatable("ui.redstoneutilz.homepage.issues"), new SideBarButton.SideBarImg(AllIcons.REPORT_ISSUES, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.REPORT_ISSUES, Minecraft.getInstance())));
        this.sidebarBtnGithub = addRenderableWidget(new SideBarButton(7, 165, 86, 22, Component.literal("   Github"), new SideBarButton.SideBarImg(AllIcons.GITHUB, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.GITHUB, Minecraft.getInstance())));
        this.sidebarBtnSocialMediaAndModDownloadPlattforms = addRenderableWidget(new SideBarButton(7, 192, 86, 22, Component.translatable("ui.redstoneutilz.homepage.smap"), new SideBarButton.SideBarImg(AllIcons.YOUTUBE, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.YOUTUBE, Minecraft.getInstance())));
        this.sidebarBtnSocialMediaAndModDownloadPlattforms.setTooltip(Tooltip.create(Component.translatable("ui.redstoneutilz.homepage.smap.full"), Component.translatable("ui.redstoneutilz.homepage.smap.full")));
        //this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 219, 86, 22, Component.literal("   Github"), new SideBarButton.SideBarImg(AllIcons.GITHUB, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.GITHUB, Minecraft.getInstance())));
        //this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 219, 86, 22, Component.literal("     Curseforge"), new SideBarButton.SideBarImg(AllIcons.CURSEFORGE, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.CURSEFORGE, Minecraft.getInstance())));
        //this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 219, 86, 22, Component.literal("   Modrinth"), new SideBarButton.SideBarImg(AllIcons.MODRINTH, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.MODRINTH, Minecraft.getInstance())));

        // Content
        //EMPTY
        // Animate
        //animationTick_FadeIn = 0;
        //animating_FadeIn = true;
    }

    @Override
    public void tick() {
        super.tick();
        animateFadeINTick();
    }

    public void animateFadeINTick() {
        if(animating_FadeIn && animationTick_FadeIn < 22) {
            animationTick_FadeIn++;
            setXSideBar(animationTick_FadeIn);
            if(animationTick_FadeIn == 1) {
                animationX_FadeIn = -89;
            }
            animationX_FadeIn++;
            animationX_FadeIn++;
            animationX_FadeIn++;
            animationX_FadeIn++;
            setXSideBar(animationX_FadeIn);
        } else {
            animationX_FadeIn = 0;
            animationTick_FadeIn = 0;
            animating_FadeIn = false;
        }
    }

    public void setXSideBar(int x) {

        sidebarBtnHomeMain.setX(x + 7);
        sidebarBtnWiki.setX(x + 7);
        sidebarBtnSettings.setX(x + 7);
        sidebarBtnVersions.setX(x + 7);
        sidebarBtnReport_Isuues.setX(x + 7);
        sidebarBtnGithub.setX(x + 7);
        sidebarBtnSocialMediaAndModDownloadPlattforms.setX(x + 7);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        Handler.GuiVec2 scaledDims = Handler.getScaledDims(new SideBarButton.SideBarImg(sideBarBackgroundImg, 106, 240), width, height);
        pGuiGraphics.blit(sideBarBackgroundImg, 0, 0, 0, 0, scaledDims.getX(), scaledDims.getY(),106 ,240);
        pGuiGraphics.drawString(this.font, Component.literal("Redstone Utils"), 10, 10, 0X404040, false);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("ui.redstoneutilz.homepage.versions.text_no_ver1"), width / 2 + 106, 10, 0xFFFFFF);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("ui.redstoneutilz.homepage.versions.text_no_ver2"), width / 2 + 106, 25, 0xFFFFFF);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("ui.redstoneutilz.homepage.versions.text_no_ver3"), width / 2 + 106, 50, 0xFFFFFF);
        pGuiGraphics.drawCenteredString(this.font, Component.translatable("ui.redstoneutilz.homepage.versions.text_no_ver4"), width / 2 + 106, 75, 0xFFFFFF);
        // IMPORTANT: AT LAST
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public void handleEmpty() {
        System.out.println("EMPTY");
    }
}
