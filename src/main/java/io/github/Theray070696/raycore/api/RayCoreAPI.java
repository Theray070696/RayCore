package io.github.Theray070696.raycore.api;

import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.network.PacketPlayMovingSound;
import io.github.Theray070696.raycore.network.PacketPlaySoundToAll;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * Created by Theray070696 on 1/22/2017.
 */
public class RayCoreAPI
{
    public static void playSoundToAll(String modID, String soundName)
    {
        playSoundToAll(modID + ":" + soundName);
    }

    public static void playSoundToAll(String soundName)
    {
        RayCore.network.sendToAll(new PacketPlaySoundToAll(soundName));
    }

    public static void playMovingSound(String modID, String soundName, SoundCategory category, World world, Entity entity)
    {
        playMovingSound(modID, soundName, category.getName(), world, entity.getEntityId());
    }

    public static void playMovingSound(String modID, String soundName, String category, World world, Entity entity)
    {
        playMovingSound(modID, soundName, category, world, entity.getEntityId());
    }

    public static void playMovingSound(String modID, String soundName, String category, World world, int entityID)
    {
        playMovingSound(modID + ":" + soundName, category, world, entityID);
    }

    public static void playMovingSound(String soundName, String category, World world, int entityID)
    {
        RayCore.network.sendToDimension(new PacketPlayMovingSound(soundName, category, entityID), world.provider.getDimension());
    }
}
