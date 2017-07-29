package io.github.Theray070696.raycore.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Theray on 1/22/2017.
 */
public abstract class BlockRayContainer extends BlockContainer
{
    private String modID;
    
    public BlockRayContainer(String modID)
    {
        this(true, modID);
    }
    
    public BlockRayContainer(boolean addToCreativeTab, String modID)
    {
        this(Material.rock);
        
        if(addToCreativeTab)
        {
            this.setCreativeTab(CreativeTabs.tabBlock);
        }
        
        this.modID = modID;
        
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(Block.soundTypeMetal);
    }
    
    public BlockRayContainer(Material material)
    {
        super(material);
        
        this.setHardness(1.5F);
        this.setResistance(10.0F);
    }
    
    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", this.modID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }
    
    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }
    
    protected void dropInventory(World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        
        if(!(tileEntity instanceof IInventory))
        {
            return;
        }
        
        IInventory inventory = (IInventory) tileEntity;
        
        for(int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);
            
            if(itemStack != null && itemStack.stackSize > 0)
            {
                Random rand = new Random();
                
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;
                
                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());
                
                if(itemStack.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }
                
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}
