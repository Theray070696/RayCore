package io.github.Theray070696.raycore.api.item;

import io.github.Theray070696.raycore.item.ItemModelProvider;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Theray070696 on 3/14/2017.
 */
public class RayItemRegistry
{
    public static <T extends Item> T registerItem(T item)
    {
        GameRegistry.register(item);

        if(item instanceof ItemModelProvider)
        {
            ((ItemModelProvider) item).registerItemModel(item);
        }

        return item;
    }
}