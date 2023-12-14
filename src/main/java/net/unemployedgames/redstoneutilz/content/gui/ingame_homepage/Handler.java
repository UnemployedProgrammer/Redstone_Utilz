package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.pages.PlattformsAndSocialMedia;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.pages.Versions;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.pages.settings.SettingsScreen;

public class Handler {

    public static enum SidebarButtonType {
        HOME,WIKI,SETTINGS,VERSIONS,REPORT_ISSUES,YOUTUBE,TIKTOK,GITHUB,CURSEFORGE,MODRINTH,SM_P;
    }

    public static class GuiVec2 {
        private int x;
        private int y;
        public GuiVec2(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public static void reactToSidebarButtonClick(SidebarButtonType sidebarButtonType, Minecraft minecraft) {
        if(sidebarButtonType==SidebarButtonType.HOME) {
            minecraft.setScreen(new MainHomePage(false));
        }
        if(sidebarButtonType==SidebarButtonType.SM_P) {
            minecraft.setScreen(new PlattformsAndSocialMedia());
        }
        if(sidebarButtonType==SidebarButtonType.WIKI) {
            openURL("https://gist.github.com/sebastianmueller1306/8532ac7c323f32ba49eae9d9e6273697", minecraft);
        }
        if(sidebarButtonType==SidebarButtonType.SETTINGS) {
            //minecraft.setScreen(new MainHomePage());
            //minecraft.setScreen(new SettingsScreen());
            openURL("https://gist.github.com/sebastianmueller1306/8532ac7c323f32ba49eae9d9e6273697", minecraft);
        }
        if(sidebarButtonType==SidebarButtonType.VERSIONS) {
            //minecraft.setScreen(new MainHomePage());
            minecraft.setScreen(new Versions());
        }
        if(sidebarButtonType==SidebarButtonType.REPORT_ISSUES) {
            openURL("https://github.com/sebastianmueller1306/Redstone_Utilz/issues", minecraft);
            //minecraft.setScreen(new MainHomePage());
        }
        if(sidebarButtonType==SidebarButtonType.YOUTUBE) {
            openURL("https://www.youtube.com/channel/UCZYslrcn9KWEuRIEiQhSWfA", minecraft);
        }
        if(sidebarButtonType==SidebarButtonType.TIKTOK) {
            openURL("https://www.tiktok.com/@unemployedgamesofficial", minecraft);
        }
        if(sidebarButtonType==SidebarButtonType.GITHUB) {
            openURL("https://github.com/sebastianmueller1306/Redstone_Utilz", minecraft);
        }
        if(sidebarButtonType==SidebarButtonType.CURSEFORGE) {
            openURL("https://gist.github.com/sebastianmueller1306/8532ac7c323f32ba49eae9d9e6273697", minecraft);
        }
        if(sidebarButtonType==SidebarButtonType.MODRINTH) {
            openURL("https://gist.github.com/sebastianmueller1306/8532ac7c323f32ba49eae9d9e6273697", minecraft);
        }
    }

    public static void openURL(String url_with_http_s, Minecraft minecraft) {
        minecraft.setScreen(new ConfirmLinkScreen((p_213069_2_) -> {
            if (p_213069_2_)
                Util.getPlatform()
                        .openUri(url_with_http_s);
            minecraft.setScreen(new MainHomePage(false));
        }, url_with_http_s, true));
    }

    public static GuiVec2 getScaledDims(SideBarButton.SideBarImg sideBarImg, int w, int h) {
        int originalWidth = sideBarImg.getImgWidth();
        int originalHeight = sideBarImg.getImgHeight();

        // Determine the scaling factor
        double scaleFactor = Math.min((double) w / originalWidth, (double) h / originalHeight);

        // Calculate the scaled width and height
        int scaledWidth = (int) (originalWidth * scaleFactor);
        int scaledHeight = (int) (originalHeight * scaleFactor);
        return new GuiVec2(scaledWidth, scaledHeight);
    }
}