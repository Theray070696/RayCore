package io.github.Theray070696.raycore;

import io.github.Theray070696.raycore.block.ModBlocks;
import io.github.Theray070696.raycore.configuration.ConfigHandler;
import io.github.Theray070696.raycore.core.GuiHandler;
import io.github.Theray070696.raycore.lib.ModInfo;
import io.github.Theray070696.raycore.network.PacketPlayMovingSound;
import io.github.Theray070696.raycore.network.PacketPlaySoundToAll;
import io.github.Theray070696.raycore.proxy.IProxy;
import io.github.Theray070696.raycore.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Theray070696 on 1/22/2017.
 */
@SuppressWarnings("unused")
@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.MOD_VERSION)
public class RayCore
{
    @Mod.Instance(ModInfo.MOD_ID)
    public static RayCore INSTANCE;
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static IProxy proxy;
    
    public static SimpleNetworkWrapper network;
    
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Pre-Init");

        ConfigHandler.loadConfig(event);

        if(ConfigHandler.developerModeEnabled)
        {
            ModBlocks.loadBlocks();
        }
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);
        network.registerMessage(PacketPlaySoundToAll.Handler.class, PacketPlaySoundToAll.class, 0, Side.CLIENT);
        network.registerMessage(PacketPlayMovingSound.Handler.class, PacketPlayMovingSound.class, 1, Side.CLIENT);

        LogHelper.info("Pre-Init Complete");
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Init");

        if(ConfigHandler.developerModeEnabled)
        {
            NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        }
        
        LogHelper.info("Init Complete");
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Init");
        
        LogHelper.info("Post-Init Complete");
    }
}