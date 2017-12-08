package io.github.Theray070696.raycore.network;

import io.github.Theray070696.raycore.RayCore;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 1/5/2017.
 */
public class PacketPlaySoundToAll implements IMessage
{
    private String soundName;

    public PacketPlaySoundToAll() {}

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
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    if(RayCore.proxy.getSide().isClient())
                    {
                        if(Minecraft.getMinecraft().player != null)
                        {
                            if(SoundEvent.REGISTRY.containsKey(new ResourceLocation(message.soundName)) && SoundEvent.REGISTRY.getObject(new
                                    ResourceLocation(message.soundName)) != null)

                            {
                                Minecraft.getMinecraft().player.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation(message.soundName)),
                                        1.0f, 1.0f);
                            }
                        }
                    }
                }
            });

            return null;
        }
    }
}
