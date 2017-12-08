package io.github.Theray070696.raycore.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public abstract class TileInventory extends TileEntity implements IInventory
{
    protected ItemStack[] inventory;
    
    public TileInventory()
    {
        inventory = new ItemStack[this.getSizeInventory()];
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        
        NBTTagList list = tagCompound.getTagList("Items", 10);
        for(int i = 0; i < list.tagCount(); ++i)
        {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < this.getSizeInventory(); ++i)
        {
            if(this.getStackInSlot(i) != null)
            {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        tagCompound.setTag("Items", list);

        return tagCompound;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        if(slot < 0 || slot >= this.getSizeInventory())
        {
            return null;
        }
        
        return this.inventory[slot];
    }
    
    @Override
    public ItemStack decrStackSize(int slot, int count)
    {
        if(this.getStackInSlot(slot) != null)
        {
            ItemStack itemstack;

            if(this.getStackInSlot(slot).stackSize <= count)
            {
                itemstack = this.getStackInSlot(slot);
                this.setInventorySlotContents(slot, null);
                this.markDirty();
                return itemstack;
            } else
            {
                itemstack = this.getStackInSlot(slot).splitStack(count);

                if(this.getStackInSlot(slot).stackSize <= 0)
                {
                    this.setInventorySlotContents(slot, null);
                } else
                {
                    this.setInventorySlotContents(slot, this.getStackInSlot(slot));
                }

                this.markDirty();
                return itemstack;
            }
        } else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        if(slot < 0 || slot >= this.getSizeInventory())
        {
            return;
        }

        if(stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if(stack != null && stack.stackSize == 0)
        {
            stack = null;
        }

        this.inventory[slot] = stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return player.getDistanceSq(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f) <= 64;
    }

    @Override
    public void clear()
    {
        for(int i = 0; i < this.inventory.length; i++)
        {
            this.inventory[i] = null;
        }
    }

    @Override
    public int getField(int id) { return 0; }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() { return 0; }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }
}
