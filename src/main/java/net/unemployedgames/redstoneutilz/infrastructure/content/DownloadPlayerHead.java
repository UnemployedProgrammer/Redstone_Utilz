package net.unemployedgames.redstoneutilz.infrastructure.content;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.loading.FMLPaths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class DownloadPlayerHead {
    public static void download() {
        String playerCharImageURL = "https://mc-heads.net/avatar/"+ Minecraft.getInstance().getUser().getName()+".png";
        File outputFile = new File(FMLPaths.MODSDIR.get().toFile().getParent(), "instanceplayerhead.png");
        try {

            BufferedImage headimg = ImageIO.read(new URL(playerCharImageURL));
            if (headimg != null) {
                ImageIO.write(headimg, "png", outputFile);
            } else {
                nilHead();
            }

        } catch (Exception e) {
            nilHead();
        }
    }

    public static void downloadWithEvent(Runnable onDownloaded) {
        String playerCharImageURL = "https://mc-heads.net/avatar/"+ Minecraft.getInstance().getUser().getName()+".png";
        File outputFile = new File(FMLPaths.MODSDIR.get().toFile().getParent(), "instanceplayerhead.png");
        try {

            BufferedImage headimg = ImageIO.read(new URL(playerCharImageURL));
            if (headimg != null) {
                ImageIO.write(headimg, "png", outputFile);
            } else {
                nilHead();
            }

            onDownloaded.run();

        } catch (Exception e) {
            nilHead();
        }
    }

    public static boolean downloaded() {
        File outputFile = new File(FMLPaths.MODSDIR.get().toFile().getParent(), "instanceplayerhead.png");
        return outputFile.exists();
    }

    public static void nilHead() {

    }
}
