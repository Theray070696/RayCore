package io.github.Theray070696.raycore.core;

import io.github.Theray070696.raycore.client.gui.GuiModularTest;
import io.github.Theray070696.raycore.lib.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GuiIds.MODULAR_GUI_GUI_ID)
        {
            return null;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == GuiIds.MODULAR_GUI_GUI_ID)
        {
            return new GuiModularTest();
        }

        return null;
    }
}
