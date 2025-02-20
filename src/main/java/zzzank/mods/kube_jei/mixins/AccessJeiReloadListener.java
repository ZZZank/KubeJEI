package zzzank.mods.kube_jei.mixins;

import mezz.jei.startup.ClientLifecycleHandler;
import mezz.jei.startup.JeiReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.lang.ref.WeakReference;

/**
 * @author ZZZank
 */
@Mixin(value = JeiReloadListener.class, remap = false)
public interface AccessJeiReloadListener {

    @Accessor("handler")
    WeakReference<ClientLifecycleHandler> kJei$getHandlerRef();
}
