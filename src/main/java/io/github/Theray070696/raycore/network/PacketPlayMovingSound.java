package io.github.Theray070696.raycore.network;

import io.github.Theray070696.raycore.RayCore;
import io.github.Theray070696.raycore.audio.MovingSoundRay;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Theray070696 on 8/21/2017
 */
public class PacketPlayMovingSound implements IMessage
{
    private String soundName;
    private String category;
    private int entityID;

    public PacketPlayMovingSound()
    {
    }

    public PacketPlayMovingSound(String soundName, String category, int entityID)
    {
        this.soundName = soundName;
        this.category = category;
        this.entityID = entityID;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.soundName = ByteBufUtils.readUTF8String(buf);
        this.category = ByteBufUtils.readUTF8String(buf);
        this.entityID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, this.soundName);
        ByteBufUtils.writeUTF8String(buf, this.category);
        buf.writeInt(this.entityID);
    }

    public static class Handler implements IMessageHandler<PacketPlayMovingSound, IMessage>
    {
        @Override
        public IMessage onMessage(PacketPlayMovingSound message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(new Runnable()
            {
                @Override
                public void run()
                {
                    if(RayCore.proxy.getSide().isClient())
                    {
                        if(SoundEvent.REGISTRY.containsKey(new ResourceLocation(message.soundName)) && SoundEvent.REGISTRY.getObject(new
                                ResourceLocation(message.soundName)) != null)
                        {
                            Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundRay(Minecraft.getMinecraft().thePlayer.worldObj
                                    .getEntityByID(message.entityID), SoundEvent.REGISTRY.getObject(new ResourceLocation(message.soundName)),
                                    SoundCategory.getByName(message.category)));
                        }
                    }
                }
            });

            return null;
        }
    }
}
