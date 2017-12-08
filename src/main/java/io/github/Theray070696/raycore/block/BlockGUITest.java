package io.github.Theray070696.raycore.block;

import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.lib.GuiIds;
import io.github.Theray070696.raycore.lib.ModInfo;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class BlockGUITest extends BlockRay
{
    public BlockGUITest()
    {
        this(true);
    }

    public BlockGUITest(boolean addToCreativeTab)
    {
        this(Material.ROCK, addToCreativeTab);
    }

    public BlockGUITest(Material material)
    {
        this(material, true);
    }

    public BlockGUITest(Material material, boolean addToCreativeTab)
    {
        super(material, addToCreativeTab, ModInfo.MOD_ID);

        this.setUnlocalizedName("guiTest");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, EnumHand hand, EnumFacing side,
                                    float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
        {
            player.openGui(RayCore.INSTANCE, GuiIds.MODULAR_GUI_GUI_ID, world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            return true;
        }

        return false;
    }
}
