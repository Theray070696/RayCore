package io.github.Theray070696.raycore.block;

import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.item.ItemModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public abstract class BlockRayContainer extends BlockContainer implements ItemModelProvider
{
    private String modID;
    
    public BlockRayContainer(String modID)
    {
        this(true, modID);
    }

    public BlockRayContainer(boolean addToCreativeTab, String modID)
    {
        this(Material.ROCK, addToCreativeTab, modID);
    }
    
    public BlockRayContainer(Material material, boolean addToCreativeTab, String modID)
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

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Block setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        this.setRegistryName(modID + ":" + name);
        return this;
    }
    
    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
    
    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
    
    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState)
    {
        dropInventory(world, blockPos);
        super.breakBlock(world, blockPos, blockState);
    }
    
    protected void dropInventory(World world, BlockPos blockPos)
    {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        
        if(!(tileEntity instanceof IInventory))
        {
            return;
        }
        
        IInventory inventory = (IInventory) tileEntity;
        
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);
            
            if(itemStack != null && itemStack.getCount() > 0)
            {
                Random rand = new Random();
                
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;
                
                EntityItem entityItem = new EntityItem(world, blockPos.getX() + dX, blockPos.getY() + dY, blockPos.getZ() + dZ, itemStack.copy());
                
                if(itemStack.hasTagCompound())
                {
                    entityItem.getItem().setTagCompound(itemStack.getTagCompound().copy());
                }
                
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntity(entityItem);
                itemStack.setCount(0);
            }
        }
    }

    @Override
    public void registerItemModel(Item itemBlock)
    {
        RayCore.proxy.registerItemRenderer(itemBlock, 0, modID, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
}
