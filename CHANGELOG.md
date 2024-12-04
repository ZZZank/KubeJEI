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
