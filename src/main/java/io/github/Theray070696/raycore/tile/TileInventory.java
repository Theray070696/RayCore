package io.github.Theray070696.raycore.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

/**
 * Created by Theray070696 on 2/3/2017.
 */
public abstract class TileInventory extends TileEntity implements IInventory
{
    protected NonNullList<ItemStack> inventoryContents;

    public TileInventory()
    {
        inventoryContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.inventoryContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        ItemStackHelper.loadAllItems(tagCompound, this.inventoryContents);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        ItemStackHelper.saveAllItems(tagCompound, this.inventoryContents);

        return tagCompound;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return (ItemStack) this.getItems().get(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int count)
    {
        ItemStack itemstack = ItemStackHelper.getAndSplit(this.getItems(), slot, count);

        if(!itemstack.isEmpty())
        {
            this.markDirty();
        }

        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.getItems().set(slot, stack);

        if(stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return player.getDistanceSq(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f) <= 64;
    }

    @Override
    public void clear()
    {
        this.getItems().clear();
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {
    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.getItems(), index);
    }

    protected NonNullList<ItemStack> getItems()
    {
        return this.inventoryContents;
    }
}
