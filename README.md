# KubeJEI
provides advanced JEI support for KubeJS, allowing devs to do basically anything a `JEIPlugin` can do.

Features:
- Everything in a `JEIPlugin` redirected to KubeJS via events
- Custom JEI category
- Events for "denying" categories and recipes. By filtering them early, instead of hiding them after everything is loaded, almost all related computations can be eliminated.
- Helpers for creating compound `IDrawables`, entity rendering, and custom tick timer
- `/kube_jei reload` command shortcut for reloading KubeJS & JEI

### Examples (2.0.0 for 1.21.1 neoforge)

Deny a category, together with all recipes for it:

```javascript
KubeJEIEvents.denyCategory(event => {
    event.deny("minecraft:campfire_cooking")
})

KubeJEIEvents.denyRecipe(event => {
    event.denyAllInCategory("minecraft:campfire_cooking")
})
```

Make a custom category for custom recipes:

```javascript
/** @type {{input: Internal.$ItemStack_, catalyst: Internal.$FluidStack_, output: Internal.$ItemStack_}[]} */
const recipes = [
    {
        input: "ae2:1k_crafting_storage",
        catalyst: "minecraft:milk",
        output: "ae2:16k_crafting_storage"
    }
]

KubeJEIEvents.registerCategories(event => {
    const helpers = event.registration.jeiHelpers

    event.custom("my:example_category", builder => {
        builder.icon = helpers.guiHelper.createDrawableItemStack("minecraft:gold_ingot")
        builder.background = helpers.guiHelper.createBlankDrawable(20 * 4, 18)
        builder.title = Component.literal("Random Conversion")

        // actually you can simply use (...) => {...}, instead of { setRecipe(...) {...} }
        // but unfortunately you wont get type hints when using lambda, as of ProbeJS 8.0.3
        builder.recipeSetHandler = {
            setRecipe(builder, recipe, focuses) {
                /** @type {typeof recipes[0]} */
                const data = recipe.data
                const catalyst = Fluid.of(data.catalyst)

                builder.addInputSlot(0, 0).addItemStack(data.input)
                builder.addInputSlot(20, 0).addFluidStack(catalyst.fluid, catalyst.getAmount(), catalyst.componentsPatch)
                builder.addOutputSlot(20 * 3, 0).addItemStack(data.output)
            }
        }

        builder.drawHandler = {
            draw(recipe, graphics, mouseX, mouseY) {
                helpers.guiHelper.recipeFlameFilled.draw(graphics, 40, 1)
            }
        }
    })
})

KubeJEIEvents.registerRecipes(event => {
    event.custom("my:example_category").addAll(recipes)
})
```

### 1.16 only

Note that all 1.4.0+ version requires Rhizo instead of Rhino to run, it's because Rhino for mc 1.16 has been EOL for a long time, missing features for our jsdoc generation

events:
- `kube_jei.on_runtime_available`
- `kube_jei.register_advanced`
- `kube_jei.register_categories`
- `kube_jei.register_fluid_subtypes`
- `kube_jei.register_gui_handlers`
- `kube_jei.register_ingredients`
- `kube_jei.register_item_subtypes`
- `kube_jei.register_recipe_catalysts`
- `kube_jei.register_recipes`
- `kube_jei.register_recipe_transfer_handlers`
- `kube_jei.register_vanilla_category_extensions`
- `kube_jei.deny.recipes`
- `kube_jei.deny.categories`