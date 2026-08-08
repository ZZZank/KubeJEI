package zank.mods.kube_jei.events;

import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.client.Minecraft;

/**
 * @author ZZZank
 */
public interface KubeJEIEvent extends KubeEvent {

    default Minecraft getMc() {
        return Minecraft.getInstance();
    }
}
