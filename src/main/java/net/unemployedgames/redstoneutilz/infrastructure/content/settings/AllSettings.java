package net.unemployedgames.redstoneutilz.infrastructure.content.settings;

import net.minecraft.util.StringRepresentable;

public enum AllSettings{
    CAN_SHOW_FOOL_LOGO("mainHomePage_canShowFoolLogo", "bool"),
    CAN_SHOW_FESTIVALS_LOGO("mainHomePage_canShowFestivalsLogo", "bool"),
    UI_CURSED_GAME_TEXTURES("ui_showsCursedGameTextures", "bool")
    ;

    String id;
    String type;
    AllSettings(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
