package io.github.Theray070696.raycore;

import io.github.Theray070696.raycore.network.PacketPlayMovingSound;
import io.github.Theray070696.raycore.network.PacketPlaySoundToAll;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Theray070696 on 1/22/2017.
 */
@SuppressWarnings("unused")
@Mod("raycore")
public class RayCore
{
    public static SimpleNetworkWrapper network;
    private static final Logger LOGGER = LogManager.getLogger();

    public RayCore()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
    }

    public void preInit(FMLCommonSetupEvent event)
    {
        network = NetworkRegistry.INSTANCE.newSimpleChannel("raycore");
        network.registerMessage(PacketPlaySoundToAll.Handler.class, PacketPlaySoundToAll.class, 0, Side.CLIENT);
        network.registerMessage(PacketPlayMovingSound.Handler.class, PacketPlayMovingSound.class, 1, Side.CLIENT);
    }
}