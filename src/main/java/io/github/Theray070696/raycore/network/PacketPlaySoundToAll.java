package io.github.Theray070696.raycore.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.util.LogHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

/**
 * Created by Theray on 1/5/2017.
 */
public class PacketPlaySoundToAll implements IMessage
{
    private String soundName;

    public PacketPlaySoundToAll() {}
    
    public PacketPlaySoundToAll(String modid, String soundName)
    {
        this(modid + ":" + soundName);
    }

    public PacketPlaySoundToAll(String soundName)
    {
        this.soundName = soundName;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.soundName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.soundName);
    }

    public static class Handler implements IMessageHandler<PacketPlaySoundToAll, IMessage>
    {
        @Override
        public IMessage onMessage(PacketPlaySoundToAll message, MessageContext ctx)
        {
            if(RayCore.proxy.getSide().isClient())
            {
                if(Minecraft.getMinecraft() != null && Minecraft.getMinecraft().thePlayer != null) // Minecraft.getMinecraft() should NEVER be null, but better to be safe than sorry.
                {
                    Minecraft.getMinecraft().thePlayer.playSound(message.soundName, 1.0f, 1.0f);
                }
            }

            return null;
        }
    }
}
