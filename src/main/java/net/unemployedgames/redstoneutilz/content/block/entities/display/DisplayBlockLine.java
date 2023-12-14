package net.unemployedgames.redstoneutilz.content.block.entities.display;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class DisplayBlockLine {

    private class DecodedData {
        int fontSize;
        String textColor;
        int row;
    }

    public class DislayBlockLineInfo {
        private int fontSize;
        private int line;
        private String fontColor;

        public DislayBlockLineInfo(int fontSize, int line, String fontColor) {
            this.fontSize = fontSize;
            this.line = line;
            this.fontColor = fontColor;
        }

        public DislayBlockLineInfo(String nbtDatas) {
            String[] parts = nbtDatas.split("\\|");

            for (String part : parts) {
                String[] keyValue = part.split(":");
                String key = keyValue[0];
                String value = keyValue[1];

                switch (key) {
                    case "fontsize":
                        this.fontSize = Integer.parseInt(value);
                        break;
                    case "textcolor":
                        this.fontColor = value;
                        break;
                    case "row":
                        line = Integer.parseInt(value);
                        break;
                    // Add more cases for additional keys if needed
                    default:
                        // Handle unknown keys or invalid input
                        break;
                }
            }
        }

        public int getFontSize() {
            return fontSize;
        }

        public int getLine() {
            return line;
        }

        public String getFontStringColor() {
            return fontColor;
        }

        public ChatFormatting getFontColor() {
            return ChatFormatting.getByName(fontColor);
        }
    }

    private DislayBlockLineInfo data;
    private String text;
    private String key;
    private String uuid;

    private boolean found;

    public DecodedData decodeData(String encodedString) {
        DecodedData decodedData = new DecodedData();
        String[] parts = encodedString.split("\\|");

        for (String part : parts) {
            String[] keyValue = part.split(":");
            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "fontsize":
                    decodedData.fontSize = Integer.parseInt(value);
                    break;
                case "textcolor":
                    decodedData.textColor = value;
                    break;
                case "row":
                    decodedData.row = Integer.parseInt(value);
                    break;
                // Add more cases for additional keys if needed
                default:
                    // Handle unknown keys or invalid input
                    break;
            }
        }

        return decodedData;
    }

    public DisplayBlockLine(HashMap<String, String> text, HashMap<String, String> data, HashMap<String, String> uuids, int line) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            try {
                DecodedData dd = decodeData(entry.getValue());
                if(dd.row == line) {
                    this.text = text.get(entry.getKey());
                    this.uuid = entry.getKey();
                    this.key = null;
                    this.data = new DislayBlockLineInfo(dd.fontSize, dd.row, dd.textColor);
                    this.found = true;
                }
            } catch (Exception e) {
                ToastComponent toastcomponent = Minecraft.getInstance().getToasts();
                LogUtils.getLogger().warn("============== REDSTONEUTILZ - DisplayBlockLine -> constructor ==============");
                LogUtils.getLogger().warn("Failed to decode data, if this warning is still present after a few tries, report at github(link is ingame)");
                LogUtils.getLogger().warn("Error Details: ");
                LogUtils.getLogger().warn(e.getMessage());
                LogUtils.getLogger().warn("============== REDSTONEUTILZ ==============");
                SystemToast.addOrUpdate(toastcomponent, SystemToast.SystemToastIds.PERIODIC_NOTIFICATION, Component.literal("[REDSTONEUTILZ]: Could not load decodedData(DISPLAY_BLOCK). See /logs/latest.log"), (Component)null);
            }
        }
    }

    public DislayBlockLineInfo getLineData() {
        return data;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }

    public String getUuid() {
        return uuid;
    }
    public boolean exists() {
        return found;
    }
}
