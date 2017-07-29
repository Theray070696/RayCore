package io.github.Theray070696.raycore.api;

import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.network.PacketPlaySoundToAll;

/**
 * Created by Theray on 1/22/2017.
 */
public class RayCoreAPI
{
    public static void playSoundToAll(String modID, String soundName)
    {
        RayCore.network.sendToAll(new PacketPlaySoundToAll(modID, soundName));
    }
    
    public static void playSoundToAll(String soundName)
    {
        RayCore.network.sendToAll(new PacketPlaySoundToAll(soundName));
    }
}
