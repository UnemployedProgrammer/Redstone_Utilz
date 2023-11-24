package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.unemployedgames.redstoneutilz.RedstoneMod;
import net.unemployedgames.redstoneutilz.content.gui.AllIcons;

import java.time.LocalDate;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class MainHomePage extends Screen {


    //SideBar
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

    public static ImageWidget modLogo;
    public static ImageWidget modTitleAndDev;

    public enum ModTitleAndDevTexture implements StringRepresentable {
        NORMAL("mc_font_texts/nameanddevs"),
        PSST_HIHI("mc_font_texts/do_not_tell_anyone_hihi/psst"),
        HALLOWEEN("mc_font_texts/halloween"),
        CHRISTMAS("mc_font_texts/christmas"),
        BEFORE_NEWYEAR("mc_font_texts/before_new_year"),
        AFTER_NEWYEAR("mc_font_texts/new_year");

        private final String texture;

        @Override
        public String getSerializedName() {
            return this.texture;
        }
        ModTitleAndDevTexture(String texture) {
            this.texture = texture;
        }
    }


    //Animate
    private int animationTick_FadeIn;
    private int animationX_FadeIn;
    private boolean animating_FadeIn;

    //PageContents

    public static ImageWidget ModLogo;
    public Boolean anim;

    public MainHomePage(Boolean anm) {
        super(Component.literal("Home Page of Redstone Utils mod - Narrator not translated"));
        this.anim = anm;
    }


    @Override
    public void init() {
        super.init();

        //SideBar
        this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 30, 89, 22, Component.translatable("ui.redstoneutilz.homepage.home"), new SideBarButton.SideBarImg(AllIcons.HOME, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.HOME, Minecraft.getInstance())));
        this.sidebarBtnWiki = addRenderableWidget(new SideBarButton(7, 57, 86, 22, Component.translatable("ui.redstoneutilz.homepage.wiki"), new SideBarButton.SideBarImg(AllIcons.WIKI, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.WIKI, Minecraft.getInstance())));
        this.sidebarBtnSettings = addRenderableWidget(new SideBarButton(7, 84, 86, 22, Component.translatable("ui.redstoneutilz.homepage.settings"), new SideBarButton.SideBarImg(AllIcons.SETTINGS, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.SETTINGS, Minecraft.getInstance())));
        this.sidebarBtnVersions = addRenderableWidget(new SideBarButton(7, 111, 86, 22, Component.translatable("ui.redstoneutilz.homepage.versions"), new SideBarButton.SideBarImg(AllIcons.VERSIONS, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.VERSIONS, Minecraft.getInstance())));
        this.sidebarBtnReport_Isuues = addRenderableWidget(new SideBarButton(7, 138, 86, 22, Component.translatable("ui.redstoneutilz.homepage.issues"), new SideBarButton.SideBarImg(AllIcons.REPORT_ISSUES, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.REPORT_ISSUES, Minecraft.getInstance())));
        this.sidebarBtnGithub = addRenderableWidget(new SideBarButton(7, 165, 86, 22, Component.literal("Github"), new SideBarButton.SideBarImg(AllIcons.GITHUB, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.GITHUB, Minecraft.getInstance())));
        this.sidebarBtnSocialMediaAndModDownloadPlattforms = addRenderableWidget(new SideBarButton(7, 192, 86, 22, Component.translatable("ui.redstoneutilz.homepage.smap"), new SideBarButton.SideBarImg(AllIcons.YOUTUBE, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.SM_P, Minecraft.getInstance()))); //Social Media and Plattforms
        this.sidebarBtnSocialMediaAndModDownloadPlattforms.setTooltip(Tooltip.create(Component.translatable("ui.redstoneutilz.homepage.smap.full"), Component.translatable("ui.redstoneutilz.homepage.smap.full")));
        //this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 219, 86, 22, Component.literal("   Github"), new SideBarButton.SideBarImg(AllIcons.GITHUB, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.GITHUB, Minecraft.getInstance())));
        //this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 219, 86, 22, Component.literal("     Curseforge"), new SideBarButton.SideBarImg(AllIcons.CURSEFORGE, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.CURSEFORGE, Minecraft.getInstance())));
        //this.sidebarBtnHomeMain = addRenderableWidget(new SideBarButton(7, 219, 86, 22, Component.literal("   Modrinth"), new SideBarButton.SideBarImg(AllIcons.MODRINTH, 16, 16), pButton -> Handler.reactToSidebarButtonClick(Handler.SidebarButtonType.MODRINTH, Minecraft.getInstance())));

        // Content

        this.modLogo = addRenderableWidget(new ImageWidget(width - 150, 10, 140, 140, new ResourceLocation(RedstoneMod.Mod_ID, "logo.png")));

        ModTitleAndDevTexture t = getTypeLogo();
        this.modTitleAndDev = addRenderableWidget(new ImageWidget(width - 310, 10, 150, 35, AllIcons.texture(t.texture)));
        if(t == ModTitleAndDevTexture.PSST_HIHI) {

        }

        // Animate
        animationTick_FadeIn = 0;
        animating_FadeIn = anim;
    }

    @Override
    public void tick() {
        super.tick();
        animateFadeINTick();
    }

    public ModTitleAndDevTexture getTypeLogo() {
        ModTitleAndDevTexture modTitleAndDevTexture = ModTitleAndDevTexture.NORMAL;
        LocalDate currentDate = LocalDate.now();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        int maxDaysInDecember = currentDate.withMonth(12).lengthOfMonth();
        int maxDaysInOctober = currentDate.withMonth(10).lengthOfMonth();
        Random random = new Random();
        int randomNumber = random.nextInt(200); //BOUNDS=200/WHEN:RELEASE
        if (randomNumber == 0) {
            modTitleAndDevTexture = ModTitleAndDevTexture.PSST_HIHI;
        }
        Boolean isChristmas = month == 12 && (day >= 24 && day <= 26);
        Boolean isHalloween = month == 10 && (day == maxDaysInOctober);
        Boolean isNewYear = month == 1 && day == 1;
        Boolean isBeforeNewYear = month == 12 && day == maxDaysInDecember;

        if (isChristmas) {
            modTitleAndDevTexture = ModTitleAndDevTexture.CHRISTMAS;
        } else if (isHalloween) {
            modTitleAndDevTexture = ModTitleAndDevTexture.HALLOWEEN;
        } else if (isBeforeNewYear) {
            modTitleAndDevTexture = ModTitleAndDevTexture.BEFORE_NEWYEAR;
        } else if (isNewYear) {
            modTitleAndDevTexture = ModTitleAndDevTexture.AFTER_NEWYEAR;
        }

        return modTitleAndDevTexture;
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

        // IMPORTANT: AT LAST
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public void handleEmpty() {
        System.out.println("EMPTY");
    }
}
