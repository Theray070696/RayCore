package io.github.Theray070696.raycore.world.gen;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.BlockRotationProcessor;
import net.minecraft.world.gen.structure.template.ITemplateProcessor;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Almost all of this class is copied from {@link Template}. I only changed a couple things, which will be marked with CHANGE START and CHANGE END
 * I also renamed most of the variables so everything should be slightly easier to comprehend.
 */
public class FastTemplatePlacer
{
    private final List<Template.BlockInfo> blocks = Lists.newArrayList();
    private final List<Template.EntityInfo> entities = Lists.newArrayList();
    // CHANGE START
    private final Template template;
    private BlockPos size = BlockPos.ORIGIN;

    public FastTemplatePlacer(Template template)
    {
        this.template = template;
    }
    // CHANGE END

    public static BlockPos transformedBlockPos(PlacementSettings settings, BlockPos pos)
    {
        return transformedBlockPos(pos, settings.getMirror(), settings.getRotation());
    }

    private static BlockPos transformedBlockPos(BlockPos pos, Mirror mirror, Rotation rotation)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        boolean flag = true;

        switch(mirror)
        {
            case LEFT_RIGHT:
                z = -z;
                break;
            case FRONT_BACK:
                x = -x;
                break;
            default:
                flag = false;
        }

        switch(rotation)
        {
            case COUNTERCLOCKWISE_90:
                return new BlockPos(z, y, -x);
            case CLOCKWISE_90:
                return new BlockPos(-z, y, x);
            case CLOCKWISE_180:
                return new BlockPos(-x, y, -z);
            default:
                return flag ? new BlockPos(x, y, z) : pos;
        }
    }

    private static Vec3d transformedVec3d(Vec3d vec, Mirror mirror, Rotation rotation)
    {
        double x = vec.xCoord;
        double y = vec.yCoord;
        double z = vec.zCoord;
        boolean flag = true;

        switch(mirror)
        {
            case LEFT_RIGHT:
                z = 1.0D - z;
                break;
            case FRONT_BACK:
                x = 1.0D - x;
                break;
            default:
                flag = false;
        }

        switch(rotation)
        {
            case COUNTERCLOCKWISE_90:
                return new Vec3d(z, y, 1.0D - x);
            case CLOCKWISE_90:
                return new Vec3d(1.0D - z, y, x);
            case CLOCKWISE_180:
                return new Vec3d(1.0D - x, y, 1.0D - z);
            default:
                return flag ? new Vec3d(x, y, z) : vec;
        }
    }

    public void addBlocksToWorld(World world, BlockPos pos, PlacementSettings settings)
    {
        this.addBlocksToWorld(world, pos, new BlockRotationProcessor(pos, settings), settings, 2);
    }

    public void addBlocksToWorld(World world, BlockPos pos, PlacementSettings settings, int flags)
    {
        this.addBlocksToWorld(world, pos, new BlockRotationProcessor(pos, settings), settings, flags);
    }

    public void addBlocksToWorld(World world, BlockPos pos, @Nullable ITemplateProcessor processor, PlacementSettings settings, int flags)
    {
        this.read(template.writeToNBT(new NBTTagCompound()));

        if(!this.blocks.isEmpty() && this.size.getX() >= 1 && this.size.getY() >= 1 && this.size.getZ() >= 1)
        {
            Block block = settings.getReplacedBlock();
            StructureBoundingBox structureBoundingBox = settings.getBoundingBox();

            for(Template.BlockInfo blockInfo : this.blocks)
            {
                BlockPos transformedPos = transformedBlockPos(settings, blockInfo.pos).add(pos);
                Template.BlockInfo blockInfo1 = processor != null ? processor.processBlock(world, transformedPos, blockInfo) : blockInfo;

                if(blockInfo1 != null)
                {
                    Block block1 = blockInfo1.blockState.getBlock();

                    if((block == null || block != block1) && (!settings.getIgnoreStructureBlock() || block1 != Blocks.STRUCTURE_BLOCK) &&
                            (structureBoundingBox == null || structureBoundingBox.isVecInside(transformedPos)))
                    {
                        IBlockState blockState = blockInfo1.blockState.withMirror(settings.getMirror());
                        IBlockState blockState1 = blockState.withRotation(settings.getRotation());

                        if(blockInfo1.tileentityData != null)
                        {
                            TileEntity tile = world.getTileEntity(transformedPos);

                            if(tile != null)
                            {
                                if(tile instanceof IInventory)
                                {
                                    ((IInventory) tile).clear();
                                }

                                world.setBlockState(transformedPos, Blocks.BARRIER.getDefaultState(), 4); // We don't need to replace this
                                // setBlockState, as this only places Barriers. We'd only want to replace it if we don't want to update lighting
                            }
                        }

                        // CHANGE START
                        boolean flag;

                        if(!blockState1.getBlock().equals(Blocks.AIR) && (transformedPos.getX() == pos.getX() || transformedPos.getX() == pos.getX
                                () + this.size.getX() - 1 || transformedPos.getY() == pos.getY() || transformedPos.getY() == pos.getY() + this.size
                                .getY() - 1 || transformedPos.getZ() == pos.getZ() || transformedPos.getZ() == pos.getZ() + this.size.getZ() - 1)
                                || blockState1.getBlock().getLightValue(blockState1) > 0) // We are at a chunk boundary and at the ceiling or the
                        // block emits light
                        {
                            flag = BlockPlacementHelper.setBlockStateFast(world, transformedPos, blockState1, flags, true);
                        } else
                        {
                            flag = BlockPlacementHelper.setBlockStateFast(world, transformedPos, blockState1, flags, false);
                        }

                        if(flag && blockInfo1.tileentityData != null)
                        // CHANGE END
                        {
                            TileEntity tile = world.getTileEntity(transformedPos);

                            if(tile != null)
                            {
                                blockInfo1.tileentityData.setInteger("x", transformedPos.getX());
                                blockInfo1.tileentityData.setInteger("y", transformedPos.getY());
                                blockInfo1.tileentityData.setInteger("z", transformedPos.getZ());
                                tile.readFromNBT(blockInfo1.tileentityData);
                                tile.mirror(settings.getMirror());
                                tile.rotate(settings.getRotation());
                            }
                        }
                    }
                }
            }

            for(Template.BlockInfo blockInfo : this.blocks)
            {
                if(block == null || block != blockInfo.blockState.getBlock())
                {
                    BlockPos blockPos1 = transformedBlockPos(settings, blockInfo.pos).add(pos);

                    if(structureBoundingBox == null || structureBoundingBox.isVecInside(blockPos1))
                    {
                        world.notifyNeighborsRespectDebug(blockPos1, blockInfo.blockState.getBlock(), false);

                        if(blockInfo.tileentityData != null)
                        {
                            TileEntity tile = world.getTileEntity(blockPos1);

                            if(tile != null)
                            {
                                tile.markDirty();
                            }
                        }
                    }
                }
            }

            if(!settings.getIgnoreEntities())
            {
                this.addEntitiesToWorld(world, pos, settings.getMirror(), settings.getRotation(), structureBoundingBox);
            }
        }
    }

    private void addEntitiesToWorld(World world, BlockPos pos, Mirror mirror, Rotation rotation, @Nullable StructureBoundingBox boundingBox)
    {
        for(Template.EntityInfo entityInfo : this.entities)
        {
            BlockPos blockPos = transformedBlockPos(entityInfo.blockPos, mirror, rotation).add(pos);

            if(boundingBox == null || boundingBox.isVecInside(blockPos))
            {
                NBTTagCompound compound = entityInfo.entityData;
                Vec3d vec3D = transformedVec3d(entityInfo.pos, mirror, rotation);
                Vec3d vec3D1 = vec3D.addVector((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
                NBTTagList tagList = new NBTTagList();
                tagList.appendTag(new NBTTagDouble(vec3D1.xCoord));
                tagList.appendTag(new NBTTagDouble(vec3D1.yCoord));
                tagList.appendTag(new NBTTagDouble(vec3D1.zCoord));
                compound.setTag("Pos", tagList);
                compound.setUniqueId("UUID", UUID.randomUUID());
                Entity entity;

                try
                {
                    entity = EntityList.createEntityFromNBT(compound, world);
                } catch(Exception e)
                {
                    entity = null;
                }

                if(entity != null)
                {
                    float yaw = entity.getMirroredYaw(mirror);
                    yaw = yaw + (entity.rotationYaw - entity.getRotatedYaw(rotation));
                    entity.setLocationAndAngles(vec3D1.xCoord, vec3D1.yCoord, vec3D1.zCoord, yaw, entity.rotationPitch);
                    world.spawnEntity(entity);
                }
            }
        }
    }

    private void read(NBTTagCompound compound)
    {
        this.blocks.clear();
        this.entities.clear();
        NBTTagList tagList = compound.getTagList("size", 3);
        this.size = new BlockPos(tagList.getIntAt(0), tagList.getIntAt(1), tagList.getIntAt(2));
        BasicPalette palette = new BasicPalette();
        NBTTagList tagList1 = compound.getTagList("palette", 10);

        for(int i = 0; i < tagList1.tagCount(); ++i)
        {
            palette.addMapping(NBTUtil.readBlockState(tagList1.getCompoundTagAt(i)), i);
        }

        NBTTagList tagList2 = compound.getTagList("blocks", 10);

        for(int j = 0; j < tagList2.tagCount(); ++j)
        {
            NBTTagCompound compound1 = tagList2.getCompoundTagAt(j);
            NBTTagList tagList3 = compound1.getTagList("pos", 3);
            BlockPos blockPos = new BlockPos(tagList3.getIntAt(0), tagList3.getIntAt(1), tagList3.getIntAt(2));
            IBlockState blockState = palette.stateFor(compound1.getInteger("state"));
            NBTTagCompound compound2;

            if(compound1.hasKey("nbt"))
            {
                compound2 = compound1.getCompoundTag("nbt");
            } else
            {
                compound2 = null;
            }

            this.blocks.add(new Template.BlockInfo(blockPos, blockState, compound2));
        }

        NBTTagList tagList3 = compound.getTagList("entities", 10);

        for(int k = 0; k < tagList3.tagCount(); ++k)
        {
            NBTTagCompound compound1 = tagList3.getCompoundTagAt(k);
            NBTTagList tagList4 = compound1.getTagList("pos", 6);
            Vec3d vec3D = new Vec3d(tagList4.getDoubleAt(0), tagList4.getDoubleAt(1), tagList4.getDoubleAt(2));
            NBTTagList tagList5 = compound1.getTagList("blockPos", 3);
            BlockPos blockPos = new BlockPos(tagList5.getIntAt(0), tagList5.getIntAt(1), tagList5.getIntAt(2));

            if(compound1.hasKey("nbt"))
            {
                NBTTagCompound compound2 = compound1.getCompoundTag("nbt");
                this.entities.add(new Template.EntityInfo(vec3D, blockPos, compound2));
            }
        }
    }

    static class BasicPalette implements Iterable<IBlockState>
    {
        static final IBlockState DEFAULT_BLOCK_STATE = Blocks.AIR.getDefaultState();
        final ObjectIntIdentityMap<IBlockState> ids;
        private int lastId;

        private BasicPalette()
        {
            this.ids = new ObjectIntIdentityMap(16);
        }

        public int idFor(IBlockState state)
        {
            int id = this.ids.get(state);

            if(id == -1)
            {
                id = this.lastId++;
                this.ids.put(state, id);
            }

            return id;
        }

        @Nullable
        IBlockState stateFor(int id)
        {
            IBlockState iblockstate = this.ids.getByValue(id);
            return iblockstate == null ? DEFAULT_BLOCK_STATE : iblockstate;
        }

        public Iterator<IBlockState> iterator()
        {
            return this.ids.iterator();
        }

        void addMapping(IBlockState state, int id)
        {
            this.ids.put(state, id);
        }
    }
}
