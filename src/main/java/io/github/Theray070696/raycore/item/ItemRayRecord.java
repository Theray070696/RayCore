package io.github.Theray070696.raycore.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Theray on 1/12/2017.
 */
public class ItemRayRecord extends ItemRecord
{
    private String modID;
    
    public int recordLength = -1;
    
    public ItemRayRecord(String modID, String recordName)
    {
        super(recordName);

        this.modID = modID;
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(this, 0, 1, 1, 10));
        this.setCreativeTab(CreativeTabs.tabMisc);
    
        OreDictionary.registerOre("record", this);
    }
    
    public ItemRayRecord(String modID, String recordName, int recordLength)
    {
        this(modID, recordName);
        
        this.recordLength = recordLength;
    }

    @Override
    public String getRecordNameLocal()
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".desc");
    }

    @Override
    public ResourceLocation getRecordResource(String name)
    {
        return new ResourceLocation(this.modID, name);
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

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
}
