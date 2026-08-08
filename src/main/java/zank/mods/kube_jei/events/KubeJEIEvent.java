package zank.mods.kube_jei.events;

import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.client.Minecraft;
import zank.mods.kube_jei.impl.helper.KubeJEIHelpers;

/**
 * @author ZZZank
 */
public interface KubeJEIEvent extends KubeEvent {

    default Minecraft getMc() {
        return Minecraft.getInstance();
    }

    default KubeJEIHelpers getKubeJEIHelpers() {
        return KubeJEIHelpers.INSTANCE;
    }
}
