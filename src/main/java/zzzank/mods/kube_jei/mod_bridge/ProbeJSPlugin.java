package zzzank.mods.kube_jei.mod_bridge;

import dev.latvian.kubejs.script.ScriptType;
import mezz.jei.api.ingredients.IIngredientType;
import zzzank.probejs.lang.typescript.ScriptDump;
import zzzank.probejs.lang.typescript.code.type.Types;

/**
 * @author ZZZank
 */
public class ProbeJSPlugin implements zzzank.probejs.plugin.ProbeJSPlugin {

    @Override
    public void assignType(ScriptDump scriptDump) {
        if (scriptDump.scriptType == ScriptType.CLIENT) {
            scriptDump.assignType(
                IIngredientType.class,
                Types.parameterized(Types.type(Class.class), Types.generic("T"))
            );
        }
    }
}
