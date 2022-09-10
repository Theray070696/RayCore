package io.github.Theray070696.raycore.proxy;

import net.minecraft.item.Item;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public interface IProxy
{
    public Side getSide();

    public void registerItemRenderer(Item item, int meta, String modID, String id);
}
