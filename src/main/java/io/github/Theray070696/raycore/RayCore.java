package io.github.Theray070696.raycore;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import io.github.Theray070696.raycore.lib.ModInfo;
import io.github.Theray070696.raycore.network.PacketPlaySoundToAll;
import io.github.Theray070696.raycore.proxy.IProxy;
import io.github.Theray070696.raycore.util.LogHelper;

/**
 * Created by Theray on 1/22/2017.
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
        
        network = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);
        network.registerMessage(PacketPlaySoundToAll.Handler.class, PacketPlaySoundToAll.class, 0, Side.CLIENT);
        
        LogHelper.info("Pre-Init Complete");
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        LogHelper.info("Init");
        
        LogHelper.info("Init Complete");
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        LogHelper.info("Post-Init");
        
        LogHelper.info("Post-Init Complete");
    }
}