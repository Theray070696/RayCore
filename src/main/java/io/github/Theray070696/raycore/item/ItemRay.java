package io.github.Theray070696.raycore.item;

import io.github.Theray070696.raycore.RayCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class ItemRay extends Item implements ItemModelProvider
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
            this.setCreativeTab(CreativeTabs.MISC);
        }
        
        this.modID = modID;
    }

    @Override
    public Item setTranslationKey(String name)
    {
        super.setTranslationKey(name);
        this.setRegistryName(modID + ":" + name.toLowerCase());
        return this;
    }

    @Override
    public String getTranslationKey()
    {
        return String.format("item.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getTranslationKey()));
    }

    @Override
    public String getTranslationKey(ItemStack itemStack)
    {
        return String.format("item.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getTranslationKey()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    protected String getUnwrappedUnlocalizedName()
    {
        return super.getTranslationKey().substring(super.getTranslationKey().indexOf(".") + 1);
    }

    @Override
    public void registerItemModel(Item item)
    {
        RayCore.proxy.registerItemRenderer(this, 0, modID, getUnwrappedUnlocalizedName(super.getTranslationKey()));
    }

    public int getMaxMetadata()
    {
        return 0;
    }
}