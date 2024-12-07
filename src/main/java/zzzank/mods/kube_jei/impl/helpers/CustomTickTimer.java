package zzzank.mods.kube_jei.impl.helpers;

import mezz.jei.api.gui.ITickTimer;

import java.util.function.IntSupplier;

/**
 * @author ZZZank
 */
public class CustomTickTimer implements ITickTimer {
    private final IntSupplier currentTick;
    private final int maxTick;

    public CustomTickTimer(IntSupplier currentTick, int maxTick) {
        this.currentTick = currentTick;
        this.maxTick = maxTick;
    }

    @Override
    public int getValue() {
        return currentTick.getAsInt();
    }

    @Override
    public int getMaxValue() {
        return maxTick;
    }
}
