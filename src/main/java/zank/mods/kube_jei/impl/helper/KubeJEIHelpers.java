package zank.mods.kube_jei.impl.helper;

import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;

import java.util.function.IntSupplier;

/**
 * @author ZZZank
 */
public class KubeJEIHelpers {
    public static final KubeJEIHelpers INSTANCE = new KubeJEIHelpers();

    public DualDrawable dualDrawable(IDrawable primary, IDrawable secondary) {
        return new DualDrawable(primary, secondary);
    }

    public ITickTimer customTickTimer(IntSupplier currentValue, int maxValue) {
        return new CustomTickTimer(currentValue, maxValue);
    }
}
