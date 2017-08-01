package io.github.Theray070696.raycore.block;

import io.github.Theray070696.raycore.api.block.RayBlockRegistry;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class ModBlocks
{
    public static BlockRay blockGUITest;

    public static void loadBlocks()
    {
        blockGUITest = RayBlockRegistry.register(new BlockGUITest());
    }
}
