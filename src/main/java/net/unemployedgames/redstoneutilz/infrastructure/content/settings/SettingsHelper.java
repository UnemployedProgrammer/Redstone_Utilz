package net.unemployedgames.redstoneutilz.infrastructure.content.settings;

import net.unemployedgames.redstoneutilz.content.gui.AllIcons;
import net.unemployedgames.redstoneutilz.content.gui.ingame_homepage.SideBarButton;
import net.unemployedgames.redstoneutilz.infrastructure.SettingsSystem;
import org.checkerframework.checker.units.qual.A;

public class SettingsHelper {
    public static void toggleSetting(AllSettings setting) {
        boolean currentState = SettingsSystem.getBooleanSettings(SettingsSystem.getKey(setting.getId()));
        boolean toggldBool;
        if(currentState == true)
            toggldBool = false;
        else
            toggldBool = true;
        SettingsSystem.saveKey(setting.getId(), SettingsSystem.getSettingsBoolean(toggldBool));
    }
    public static SideBarButton.SideBarImg iconForCurrentSetting(AllSettings setting) {
        if(SettingsSystem.getBooleanSettings(SettingsSystem.getKey(setting.getId())))
            return new SideBarButton.SideBarImg(AllIcons.TRUE, 16, 16);
        else
            return new SideBarButton.SideBarImg(AllIcons.FALSE, 16, 16);
    }
}
