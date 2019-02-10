package io.github.Theray070696.raycore.item;

import io.github.Theray070696.raycore.RayCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;

/**
 * Created by Theray070696 on 1/12/2017.
 */
public class ItemRayRecord extends ItemRecord implements ItemModelProvider
{
    private String modID;
    
    public int recordLength = -1;
    
    public ItemRayRecord(String modID, String recordName, int recordLength, SoundEvent sound)
    {
        super(modID + ":" + recordName, sound);

        this.modID = modID;
        this.setCreativeTab(CreativeTabs.MISC);

        this.recordLength = recordLength;
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

    @Override
    public void registerItemModel(Item item)
    {
        RayCore.proxy.registerItemRenderer(this, 0, modID, getUnwrappedUnlocalizedName(super.getTranslationKey()));
    }
}