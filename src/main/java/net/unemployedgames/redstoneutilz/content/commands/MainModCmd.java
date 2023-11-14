package net.unemployedgames.redstoneutilz.content.commands;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.unemployedgames.redstoneutilz.content.util.ClientHooks;

import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class MainModCmd {
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("redstoneutils")

                .executes(arguments -> {
                    ServerLevel world = arguments.getSource().getLevel();

                    Entity entity = arguments.getSource().getEntity();
                    if (entity == null)
                        entity = FakePlayerFactory.getMinecraft(world);
                    if (entity instanceof ServerPlayer serverPlayer) {
                        Supplier<ServerPlayer> plr = () -> serverPlayer;
                        serverPlayer.sendSystemMessage(Component.literal("Works! Maybe...").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
                        serverPlayer.sendSystemMessage(Component.literal("Click Here to try Again").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN).withBold(true).withUnderlined(true).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/redstoneutils"))));
                        try {
                            if (entity.level().isClientSide())
                                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientHooks::openHomePageScreen);
                        } catch (Exception e) {
                            serverPlayer.sendSystemMessage(Component.literal("Java Error:").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                            serverPlayer.sendSystemMessage(Component.literal("=========================").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                            serverPlayer.sendSystemMessage(Component.literal(e.getMessage()).setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
                        }

                        //PkgHandler.INSTANCE.send(PacketDistributor.PLAYER.with(plr), new COpenModScreen(1));
                    }
                    return 1;
                }));
    }


}
