package zzzank.mods.kube_jei;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import lombok.val;
import mezz.jei.Internal;
import mezz.jei.startup.JeiReloadListener;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import zzzank.mods.kube_jei.mixins.AccessJeiReloadListener;

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

        //merge into KubeJS commands
        dispatcher.register(Commands.literal("kubejs")
            .then(Commands.literal("reload")
                .then(Commands.literal("jei")
                    .requires(spOrOp)
                    .executes(alwaysSuccess(KubeJEICommands::reloadJEI)))
            )
        );
    }

    private static Command<CommandSourceStack> alwaysSuccess(AlwaysSuccessCommand command) {
        return command;
    }

    interface AlwaysSuccessCommand extends Command<CommandSourceStack> {
        void runImpl(CommandContext<CommandSourceStack> context) throws CommandSyntaxException;

        @Override
        default int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            runImpl(context);
            return Command.SINGLE_SUCCESS;
        }
    }

    private static void reloadJEI(CommandContext<CommandSourceStack> cx) throws CommandSyntaxException {
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
            return;
        }

        if (reloadListener == null) {
            player.sendMessage(new TextComponent("JEI has not been set up, skipping reload"), Util.NIL_UUID);
            return;
        }

        player.sendMessage(new TextComponent("Reloading JEI"), Util.NIL_UUID);
        try {
            ((AccessJeiReloadListener) (Object) reloadListener)
                .kJei$getHandlerRef()
                .get()
                .startJEI();
        } catch (Exception e) {
            player.sendMessage(
                new TextComponent("Exception when trying to reload JEI: " + e.getLocalizedMessage()),
                Util.NIL_UUID
            );
            KubeJEI.LOGGER.error("Exception when trying to reload JEI", e);
        }

        player.sendMessage(new TextComponent("JEI Reloaded"), Util.NIL_UUID);
    }
}
