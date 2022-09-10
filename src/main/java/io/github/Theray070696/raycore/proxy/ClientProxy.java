package io.github.Theray070696.raycore.proxy;

import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public Side getSide()
    {
        return Side.CLIENT;
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String modID, String id)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(modID.toLowerCase() + ":" + id, "inventory"));
    }
}
