package io.github.Theray070696.raycore.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Theray070696 on 11/1/2017
 */
public class NBTHelper
{
    public static NBTTagCompound writeBlockPos(NBTTagCompound compound, String key, BlockPos pos)
    {
        compound.setInteger(key + "X", pos.getX());
        compound.setInteger(key + "Y", pos.getY());
        compound.setInteger(key + "Z", pos.getZ());

        return compound;
    }

    public static BlockPos readBlockPos(NBTTagCompound compound, String key)
    {
        if(compound != null)
        {
            return new BlockPos(compound.getInteger(key + "X"), compound.getInteger(key + "Y"), compound.getInteger(key + "Z"));
        }

        return BlockPos.ORIGIN;
    }
}
