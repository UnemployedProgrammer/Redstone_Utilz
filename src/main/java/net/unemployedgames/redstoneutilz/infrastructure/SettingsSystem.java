package net.unemployedgames.redstoneutilz.infrastructure;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.logging.Log;

import java.io.*;

public class SettingsSystem {
    public static String getKey(String key) {
        File modFolder = FMLPaths.MODSDIR.get().toFile();
        File settingsFile = new File(modFolder.getParent(), "client.redstoneutilz_settings");
        String rstring = "err";
        try (BufferedReader br = new BufferedReader(new FileReader(settingsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Check if the line starts with the desired string
                if (line.startsWith(key)) {
                    String modifiedLine = line.substring(key.length() + 1);
                    rstring = modifiedLine;
                }
            }
        } catch (IOException e) {

        }
        System.out.println(rstring);
        return rstring;
    }

    public static void saveKey(String key, String value) {
        SettingsSystem.writeSettingsFile(SettingsSystem.getClientSettingsFileTextForValueKey(key, value));
    }

    public static void createSettings() {
        File modFolder = FMLPaths.MODSDIR.get().toFile();
        File settingsFile = new File(modFolder.getParent(), "client.redstoneutilz_settings");
        try {
            if (settingsFile.createNewFile()) {
                LogUtils.getLogger().info("Created Settings Client File for Redstone Utils: " + settingsFile.getName());
                resetSettingsFile();
            } else {
                LogUtils.getLogger().info("Found Settings Client File for Redstone Utils!");
            }
        } catch (IOException e) {
            LogUtils.getLogger().warn("Failed to create Settings Client File for Redstone Utilz! Settings will not be available! Try restarting the Game!");
            e.printStackTrace();
        }
    }

    public static String getSettingsBoolean(boolean bboolean) {
        return bboolean ? "yes" : "no";
    }

    public static boolean getBooleanSettings(String bboolean) {
        if(bboolean == "yes")
            return true;
        else
            return false;
    }

    public static void resetSettingsFile() {
        File modFolder = FMLPaths.MODSDIR.get().toFile();
        File settingsFile = new File(modFolder.getParent(), "client.redstoneutilz_settings");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile))) {
            writer.write("@Comment@ Settings file for Redstone Utils\n");
            writer.write("@Comment@ Booleans | Wrong: true/false ; Right: yes/no\n\n");
            String[] settings_lines = getClientSettingsFileTextForValues(true, true, false);
            for (String line : settings_lines) {
                writer.write(line+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LogUtils.getLogger().warn("Error writing into Client Settings File");
            e.printStackTrace();
        }
    }

    public static void writeSettingsFile(String[] settingsString) {
        File modFolder = FMLPaths.MODSDIR.get().toFile();
        File settingsFile = new File(modFolder.getParent(), "client.redstoneutilz_settings");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile))) {
            writer.write("@Comment@ Settings file for Redstone Utils\n");
            writer.write("@Comment@ Booleans | Wrong: true/false ; Right: yes/no\n\n");
            for (String line : settingsString) {
                writer.write(line+"\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LogUtils.getLogger().warn("Error writing into Client Settings File");
            e.printStackTrace();
        }
    }

    public static String[] getClientSettingsFileTextForValues(Boolean mainHomePage_canShowFoolLogo, Boolean mainHomePage_canShowFestivalsLogo, Boolean ui_showsCursedGameTextures) {
        int expected_length_ofsettings = 3;  //JAVA NUMBERS START AT 0; 2 WOULD BE 3, BECAUSE (MOST) HUMANS COUNT FROM 1
        String[] settings_lines = new String[expected_length_ofsettings];
        String v1 = mainHomePage_canShowFoolLogo ? "yes" : "no";
        String v2 = mainHomePage_canShowFestivalsLogo ? "yes" : "no";
        String v3 = ui_showsCursedGameTextures ? "yes" : "no";
        settings_lines[0] = "mainHomePage_canShowFoolLogo-"+v1;
        settings_lines[1] = "mainHomePage_canShowFestivalsLogo-"+v2;
        settings_lines[2] = "ui_showsCursedGameTextures-"+v3;
        return settings_lines;
    }

    public static String[] getClientSettingsFileTextForValueKey(String key, String value) {
        int expected_length_ofsettings = 3;  //JAVA NUMBERS START AT 0; 2 WOULD BE 3, BECAUSE (MOST) HUMANS COUNT FROM 1
        String[] settings_lines = new String[expected_length_ofsettings];
        String v1 = key = "mainHomePage_canShowFoolLogo" == key ? value : getKey("mainHomePage_canShowFoolLogo");
        String v2 = key = "mainHomePage_canShowFestivalsLogo" == key ? value : getKey("mainHomePage_canShowFestivalsLogo");
        String v3 = key = "ui_showsCursedGameTextures" == key ? value : getKey("ui_showsCursedGameTextures");
        settings_lines[0] = "mainHomePage_canShowFoolLogo-"+v1;
        settings_lines[1] = "mainHomePage_canShowFestivalsLogo-"+v2;
        settings_lines[2] = "ui_showsCursedGameTextures-"+v3;
        return settings_lines;
    }
}
