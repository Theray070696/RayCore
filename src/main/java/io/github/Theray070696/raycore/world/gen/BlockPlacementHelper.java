package io.github.Theray070696.raycore.world.gen;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

/**
 * Mostly copied from the {@link World} and {@link Chunk} setBlockState functions.
 * Feel free to use these two functions in your own mod, make sure you credit it to me :)
 * Created by Theray070696 on 10/12/2017
 */
public class BlockPlacementHelper
{
    /**
     * Use this function for large structures that have a lot of air blocks, or when you don't want to updated lighting when a block is placed.
     * <p>
     * Example: You're placing a large structure, say 18x18x5 (length width height). Updating lighting after every block is placed will exponentially
     * increase the amount of time it will take to place the structure. Instead, you could pass false to updateLighting until you are about to hit
     * a chunk boundary, in which case you pass true.
     * </p>
     *
     * @param world          The {@link World} we are placing the block in
     * @param pos            The position of the block
     * @param newState       The new {@link IBlockState} we are placing
     * @param flags          Flags for placement. These are the same as {@link World}.setBlockState
     * @param updateLighting Whether or not lighting should be updated
     * @return True if the block was placed, false otherwise
     */
    public static boolean setBlockStateFast(World world, BlockPos pos, IBlockState newState, int flags, boolean updateLighting)
    {
        if(pos.getY() < 0 || pos.getY() >= 256)
        {
            return false;
        } else if(!world.isRemote && world.getWorldInfo().getTerrainType() == WorldType.DEBUG_ALL_BLOCK_STATES)
        {
            return false;
        } else
        {
            Chunk chunk = world.getChunk(pos);

            net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;
            if(world.captureBlockSnapshots && !world.isRemote)
            {
                blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, pos, flags);
                world.capturedBlockSnapshots.add(blockSnapshot);
            }
            IBlockState oldState = world.getBlockState(pos);

            if(newState.getBlock().equals(Blocks.AIR) && oldState.getBlock().equals(Blocks.AIR)) // Skip air blocks. Should speed up block
            // placement by A LOT. We don't use Block.isAirBlock because that only checks the material.
            {
                return true;
            }

            int oldLight = oldState.getLightValue(world, pos);
            int oldOpacity = oldState.getLightOpacity(world, pos);

            IBlockState blockState;

            if(!updateLighting)
            {
                blockState = setBlockStateInChunkFast(chunk, pos, newState);
            } else
            {
                blockState = chunk.setBlockState(pos, newState);
            }

            if(blockState == null)
            {
                if(blockSnapshot != null)
                {
                    world.capturedBlockSnapshots.remove(blockSnapshot);
                }
                return false;
            } else
            {
                if(updateLighting && newState.getLightOpacity(world, pos) != oldOpacity || newState.getLightValue(world, pos) != oldLight)
                {
                    world.profiler.startSection("checkLight");
                    world.checkLight(pos);
                    world.profiler.endSection();
                }

                if(blockSnapshot == null) // Don't notify clients or update physics while capturing blockstates
                {
                    world.markAndNotifyBlock(pos, chunk, blockState, newState, flags);
                }
                return true;
            }
        }
    }

    /**
     * This is a modified version of setBlockState from {@link Chunk}. This one does not update lighting, which can result in faster placement of
     * blocks at the cost of lighting glitches.
     *
     * @param chunk    The {@link Chunk} we are placing the block in
     * @param pos      Position of the block
     * @param newState {@link IBlockState} of the new block
     */
    public static IBlockState setBlockStateInChunkFast(Chunk chunk, BlockPos pos, IBlockState newState)
    {
        int chunkX = pos.getX() & 15;
        int chunkY = pos.getY();
        int chunkZ = pos.getZ() & 15;

        IBlockState existingIBlockState = chunk.getBlockState(pos);

        if(existingIBlockState == newState)
        {
            return null;
        } else
        {
            Block newBlock = newState.getBlock();
            Block existingBlock = existingIBlockState.getBlock();
            ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[chunkY >> 4];

            if(extendedblockstorage == null)
            {
                if(newBlock == Blocks.AIR)
                {
                    return null;
                }

                extendedblockstorage = chunk.getBlockStorageArray()[chunkY >> 4] = new ExtendedBlockStorage(chunkY >> 4 << 4, chunk.getWorld()
                        .provider.hasSkyLight());
            }

            extendedblockstorage.set(chunkX, chunkY & 15, chunkZ, newState);

            if(!chunk.getWorld().isRemote)
            {
                if(existingIBlockState.getBlock() != newState.getBlock()) // Only fire block breaks when the block changes.
                {
                    existingBlock.breakBlock(chunk.getWorld(), pos, existingIBlockState);
                }

                TileEntity te = chunk.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
                if(te != null && te.shouldRefresh(chunk.getWorld(), pos, existingIBlockState, newState))
                {
                    chunk.getWorld().removeTileEntity(pos);
                }
            } else if(existingBlock.hasTileEntity(existingIBlockState))
            {
                TileEntity te = chunk.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
                if(te != null && te.shouldRefresh(chunk.getWorld(), pos, existingIBlockState, newState))
                {
                    chunk.getWorld().removeTileEntity(pos);
                }
            }

            if(extendedblockstorage.get(chunkX, chunkY & 15, chunkZ).getBlock() != newBlock)
            {
                return null;
            } else
            {
                TileEntity tileentity;

                if(!chunk.getWorld().isRemote && existingBlock != newBlock)
                {
                    newBlock.onBlockAdded(chunk.getWorld(), pos, newState);
                }

                if(newBlock.hasTileEntity(newState))
                {
                    tileentity = chunk.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);

                    if(tileentity == null)
                    {
                        tileentity = newBlock.createTileEntity(chunk.getWorld(), newState);
                        chunk.getWorld().setTileEntity(pos, tileentity);
                    }

                    if(tileentity != null)
                    {
                        tileentity.updateContainingBlockInfo();
                    }
                }

                chunk.setModified(true);
                return existingIBlockState;
            }
        }
    }
}
