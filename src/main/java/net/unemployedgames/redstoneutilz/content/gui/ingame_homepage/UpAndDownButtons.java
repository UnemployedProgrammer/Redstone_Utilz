package net.unemployedgames.redstoneutilz.content.gui.ingame_homepage;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;


public class UpAndDownButtons {
    public enum IS {
        DOWN,UP;
    }

    private AbstractWidget[] widgetsUp;
    private AbstractWidget[] widgetsDown;
    private Button up;
    private Button down;
    public IS isUpOrDown;


    public UpAndDownButtons(Handler.GuiVec2 btnUp, Handler.GuiVec2 btnDown, AbstractWidget[] widgetsUp, AbstractWidget[] widgetsDown) {
        this.widgetsUp = widgetsUp;
        this.widgetsDown = widgetsDown;
        this.up = new Button.Builder(Component.literal("UP"), pButton -> set(IS.UP))
                .bounds(btnUp.getX(), btnUp.getY(), 20, 20)
                .build()
        ;
        this.down = new Button.Builder(Component.literal("DOWN"), pButton -> set(IS.DOWN))
                .bounds(btnDown.getX(), btnDown.getY(), 20, 20)
                .build()
        ;
    }

    //btnUp.getX(), btnUp.getY(), 20, 20
    //btnDown.getX(), btnDown.getY(), 20, 20

    private void hideWidgets(AbstractWidget[] abstractWidgets) {
        for (int i = abstractWidgets.length; i < abstractWidgets.length; i++) {
            abstractWidgets[i].active = false;
            abstractWidgets[i].visible = false;
        }
    }

    private void showWidgets(AbstractWidget[] abstractWidgets) {
        for (int i = abstractWidgets.length; i < abstractWidgets.length; i++) {
            abstractWidgets[i].active = true;
            abstractWidgets[i].visible = true;
        }
    }

    public void set(IS isUpOrDown) {
        if(isUpOrDown == IS.UP) {
            hideWidgets(widgetsDown);
            showWidgets(widgetsUp);
        } else {
            hideWidgets(widgetsUp);
            showWidgets(widgetsDown);
        }
    }

    public Button getUpBtn() {
        return up;
    }

    public Button getDownBtn() {
        return down;
    }

    public IS is() {
        return isUpOrDown;
    }
}
