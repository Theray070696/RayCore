package io.github.Theray070696.raycore.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray on 8/27/2015.
 */
public class ItemRay extends Item
{
    private String modID;
    
    public ItemRay(String modID)
    {
        this(true, modID);
    }

    public ItemRay(boolean addToCreativeTab, String modID)
    {
        if(addToCreativeTab)
        {
            this.setCreativeTab(CreativeTabs.tabMisc);
        }
        
        this.modID = modID;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}