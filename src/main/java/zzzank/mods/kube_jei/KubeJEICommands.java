package zzzank.mods.kube_jei;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import mezz.jei.Internal;
import mezz.jei.startup.JeiReloadListener;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Predicate;

/**
 * @author ZZZank
 */
@Mod.EventBusSubscriber(modid = KubeJEI.MOD_ID, value = Dist.CLIENT)
public class KubeJEICommands {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        val dispatcher = event.getDispatcher();
        val environment = event.getEnvironment();

        val spOrOp = (Predicate<CommandSourceStack>)
            (source) -> source.hasPermission(2) || source.getServer().isSingleplayer();

        dispatcher.register(Commands.literal("kubejei")
            .requires(spOrOp)
            .then(Commands.literal("reload")
                .then(Commands.literal("jei")
                    .executes(KubeJEICommands::reloadJEI))
            )
        );
    }

    private static int reloadJEI(CommandContext<CommandSourceStack> cx) throws CommandSyntaxException {
        val source = cx.getSource();
        val player = source.getPlayerOrException();

        JeiReloadListener reloadListener;
        try {
            reloadListener = Internal.getReloadListener();
        } catch (Exception e) {
            player.sendMessage(
                new TextComponent("Exception when trying to get JEI reload listener: " + e.getLocalizedMessage()),
                Util.NIL_UUID
            );
            KubeJEI.LOGGER.error("Exception when trying to get JEI reload listener", e);
            return Command.SINGLE_SUCCESS;
        }

        if (reloadListener == null) {
            player.sendMessage(new TextComponent("JEI has not been set up, skipping reload"), Util.NIL_UUID);
            return Command.SINGLE_SUCCESS;
        }

        player.sendMessage(new TextComponent("Reloading JEI"), Util.NIL_UUID);
        try {
            reloadListener.onResourceManagerReload(Minecraft.getInstance().getResourceManager());
        } catch (Exception e) {
            player.sendMessage(
                new TextComponent("Exception when trying to reload JEI: " + e.getLocalizedMessage()),
                Util.NIL_UUID
            );
            KubeJEI.LOGGER.error("Exception when trying to reload JEI", e);
        }

        player.sendMessage(new TextComponent("JEI Reloaded"), Util.NIL_UUID);
        return Command.SINGLE_SUCCESS;
    }
}
