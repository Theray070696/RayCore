package io.github.Theray070696.raycore.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ServerProxy extends CommonProxy
{
    @Override
    public Side getSide()
    {
        return Side.SERVER;
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String modID, String id) {}
}
