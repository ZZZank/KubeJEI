# KubeJS 1.8.0 -> 1.8.1

nothing special

- fix noFocusRecipeMatcher in CustomRecipeManagerPlugin sometimes not working

---

# KubeJS 1.7.0 -> 1.8.0

more layout / slot helpers

- helper for creating many slots in a grid
- helper for applying ingredients to slots
- builder-style registration for RegisterAdvancedEventJS
- some code documents are moved to make it (slightly) more accessible
- special helper methods for item and fluid group/slot builder
- support for fluid slot with custom maximum volume, overlay and some more
- and some internal changes

---
# KubeJS 1.6.0 -> 1.7.0

drawable / layout helpers

- helper method for creating TickTimer
- helper method for creating an arrow drawable, the same as those displayed in JEI cooking recipe. The arrow drawable
  can be both static or animated
- helper method for caching animated drawables
- fix ingredient type wrapping on JS side
- add type-specific ingredient drawable for items and fluids to make use of type wrapper
- the `IRecipeLayout` used in `recipeSetHandler` is now expanded into `WrappedLayout`, with builder-style methods to make
  working with slot layout easier

builder-style layout setup example:
```javascript
.handleLookup((layout, recipe, ingredients) => {
    const itemBuilder = layout.itemGroupBuilder;
    itemBuilder.addSlot(0, 0)
    itemBuilder.addSlot(20, 0).addIngredient(Item.of("minecraft:bedrock").itemStack)
    itemBuilder.addSlot(60, 0).setInput(false).setHeight(18)
    layout.getItemStacks().set(ingredients)
})
```

---

# KubeJS 1.5.1 -> 1.6.0

ProbeJS Legacy support

- Type wrapper(s) added by KubeJEI can now be dumped by ProbeJS Legacy
- fix an error in `mods.toml` that will make Forge behaves strangely when matching mod dependencies

---

# KubeJS 1.5.0 -> 1.5.1

better recipe denying & non-custom category denying

- make recipe filtering happens before some JEI filters to prevent JEI throwing exceptions even when this recipe
can be filter out by our filters
- add `DenyCategoryEventJS#denyNonCustom()`, allowing you to deny category while still allowing custom category with same
id to register itself, with this, you can replace recipe category with your own implementation easier
- remove ProbeJS Legacy version requirement, we don't need 4.0+ specific features yet
- (feature from 1.4.0) better code doc

---

# KubeJS 1.4.1 -> 1.5.0

category/recipe denying & dual drawable

- early denying for recipes/categories
    - you can now deny recipes/categories at the earliest point possible, even before they are accepted by JEI
    - by denying early instead of hiding them after loaded(KubeJS approach), almost all related computation for it can
be skipped, so it can actually improve JEI loading performance
    - use `kube_jei.deny.xxxxx` to listen to denying related events, use ProbeJS Legacy for more info
- dual drawables
    - Create style, please try it yourself
- some more shortcut for `RegisterRecipeCatalystsEvent` and `WrappedIngredients`

---

# KubeJS 1.4.0 -> 1.4.1

misc fix

- fix a wrong type wrapper causing JEI to crash
- adds `$` before class name in JSDoc to work better with ProbeJS 4

# KubeJEI 1.3.0 -> 1.4.0

many doc & ingredient helper

- MANY doc, including docs for:
    - almost ALL JEI events
    - builder for CustomRecipeManagerPlugin
    - builder for CustomRecipeCategory
    - JEIDrawableWrapper
    - JEI `IIngredients` and our ingredients helpers
- Note that KubeJEI now requires [Rhizo](https://www.curseforge.com/minecraft/mc-mods/rhizo) to work, because stupid
Rhino 1,16 is dead for a long time, and is missing typing annotations for our doc usage 
- default custom category title is now a bit shorter
- `IIngredients` expansion
    - the `IIngredients` passed to `RecipeSetHandler` and `FillIngredientsHandler` are now expanded to provide fluid
ingredient wrapping, and ingredient type helpers
- `IIngredientType<Something>` now can be wrapped form `Class<Something>`, aka `typeof Something`
- some helpers for Math related classes for you to construct them easier

---

# KubeJEI 1.2.0 -> 1.3.0

render helper

- render helper
    - with this, you can access text rendering without worrying about stupid Rhino getting confused about parameter types
    - it also provides an entity rendering preset, where you can easily render an entity on your JEI page
- FluidStackJS will be wrapped into Forge FluidStack instead of Arch FluidStack to fix JEI not recognizing this ingredient
- auto-wrapping for IDrawable is removed due to ambiguities caused by it

---

# KubeJEI 1.1.0 -> 1.2.0

recipe manager plugin

- many members are now exposed as direct field access instead of getters
- custom recipe manager plugin
- better fallback for IDrawable wrapping

---

# KubeJEI 1.0.0 -> 1.1.0

drawable wrapper++

- adds proxy methods for every known IDrawable creation methods, with a shorter name and clearer parameter name
- adds builder style category registering in category registry event

---

# KubeJEI 1.0.0

it just works

- 11 events covering every features a JEI plugin can do\
- wrapper for JEI IDrawables
- custom helper for easing the registration of custom JEI Category/Recipe/RecipeType