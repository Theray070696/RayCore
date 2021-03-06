package io.github.Theray070696.raycore.block;

import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Theray070696 on 8/27/2015.
 */
public class BlockRay extends Block implements ItemModelProvider
{
    private String modID;
    
    public BlockRay(String modID)
    {
        this(true, modID);
    }

    public BlockRay(boolean addToCreativeTab, String modID)
    {
        this(Material.ROCK, addToCreativeTab, modID);
    }

    public BlockRay(Material material, boolean addToCreativeTab, String modID)
    {
        super(material);

        if(addToCreativeTab)
        {
            this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        }
        
        this.modID = modID;

        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.METAL);
    }

    @Override
    public Block setTranslationKey(String name)
    {
        super.setTranslationKey(name);
        this.setRegistryName(this.modID + ":" + name.toLowerCase());
        return this;
    }

    @Override
    public String getTranslationKey()
    {
        return String.format("tile.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getTranslationKey()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    protected void dropInventory(World world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(!(tileEntity instanceof IInventory))
        {
            return;
        }

        IInventory inventory = (IInventory) tileEntity;

        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);

            if(itemStack != ItemStack.EMPTY && itemStack.getCount() > 0)
            {
                Random rand = new Random();

                float dX = rand.nextFloat() * 0.8f + 0.1f;
                float dY = rand.nextFloat() * 0.8f + 0.1f;
                float dZ = rand.nextFloat() * 0.8f + 0.1f;

                EntityItem entityItem = new EntityItem(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, itemStack.copy());

                if(itemStack.hasTagCompound())
                {
                    entityItem.getItem().setTagCompound(itemStack.getTagCompound().copy());
                }

                float factor = 0.05f;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2f;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntity(entityItem);
                itemStack.setCount(0);
            }
        }
    }

    public BlockRay setSoundType(SoundType soundType)
    {
        super.setSoundType(soundType);

        return this;
    }

    @Override
    public void registerItemModel(Item itemBlock)
    {
        RayCore.proxy.registerItemRenderer(itemBlock, 0, modID, getUnwrappedUnlocalizedName(super.getTranslationKey()));
    }
}