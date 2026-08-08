package zank.mods.kube_jei.bridge;

import com.mojang.brigadier.Command;
import dev.latvian.mods.kubejs.client.KubeJSClient;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import zank.mods.kube_jei.KubeJEI;

/**
 * @author ZZZank
 */
@EventBusSubscriber
public class KubeJEICommand {

    @SubscribeEvent
    public static void onEvent(RegisterCommandsEvent event) {
        var dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal(KubeJEI.MOD_ID)
            .then(Commands.literal("reload")
                .requires(stack -> stack.isPlayer() && stack.getServer().isSingleplayer())
                .executes(cx -> {
                    Minecraft.getInstance()
                        .submit(KubeJSClient::reloadClientScripts)
                        .thenRun(() -> Minecraft.getInstance().player.connection.sendCommand("reload"))
                        .exceptionally(ex -> {
                            cx.getSource().sendFailure(Component.literal(ex.toString()));
                            return null;
                        });
                    return Command.SINGLE_SUCCESS;
                })));
    }
}
