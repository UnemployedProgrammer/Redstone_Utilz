package net.unemployedgames.redstoneutilz.content.gui;

import net.minecraft.resources.ResourceLocation;
import net.unemployedgames.redstoneutilz.RedstoneMod;

public class AllIcons {

    public static ResourceLocation texture(String address) {
        return new ResourceLocation(RedstoneMod.Mod_ID, "textures/"+address+".png");
    }
    public static ResourceLocation HOME = texture("gui/icons/main");
    public static ResourceLocation WIKI = texture("gui/icons/wiki");
    public static ResourceLocation SETTINGS = texture("gui/icons/settings");
    public static ResourceLocation VERSIONS = texture("gui/icons/versions");
    public static ResourceLocation REPORT_ISSUES = texture("gui/icons/report_issues");
    public static ResourceLocation YOUTUBE = texture("gui/icons/youtube");
    public static ResourceLocation TIKTOK = texture("gui/icons/tiktok");
    public static ResourceLocation GITHUB = texture("gui/icons/github");
    public static ResourceLocation CURSEFORGE = texture("gui/icons/curseforge");
    public static ResourceLocation MODRINTH = texture("gui/icons/modrinth");
    public static ResourceLocation TRUE = texture("gui/icons/boolean/true");
    public static ResourceLocation FALSE = texture("gui/icons/boolean/false");

}
