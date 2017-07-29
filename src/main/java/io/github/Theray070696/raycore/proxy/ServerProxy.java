package io.github.Theray070696.raycore.proxy;

import cpw.mods.fml.relauncher.Side;

/**
 * Created by Theray on 1/22/2017.
 */
public class ServerProxy extends CommonProxy
{
    @Override
    public Side getSide()
    {
        return Side.SERVER;
    }
}
