package io.github.Theray070696.raycore.api.block;

import io.github.Theray070696.raycore.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.RegistryManager;

/**
 * Created by Theray070696 on 3/21/2017.
 */
public class RayBlockRegistry
{
    public static <T extends Block> T register(T block, ItemBlock itemBlock)
    {
        ForgeRegistries.BLOCKS.register(block);
        if(itemBlock != null)
        {
            ForgeRegistries.ITEMS.register(itemBlock);
        }

        if(block instanceof ItemModelProvider)
        {
            ((ItemModelProvider) block).registerItemModel(itemBlock);
        }

        return block;
    }

    public static <T extends Block> T register(T block)
    {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        return register(block, itemBlock);
    }
}