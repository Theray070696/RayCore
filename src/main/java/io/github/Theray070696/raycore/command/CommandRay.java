package io.github.Theray070696.raycore.command;

import io.github.Theray070696.raycore.util.LogHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Theray070696 on 7/6/2017.
 */
public class CommandRay extends CommandBase
{
    // TODO: Implement "/silentkill".
    // TODO: Will not output who runs the command, or on who. Or anything for that matter.
    // TODO: Anybody can use it. Even non-OPs.
    // TODO: I will be immune to the command.
    // TODO: Use it on Puff.

    public String getCommandName()
    {
        return "silentkill";
    }

    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.kill.usage";
    }

    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length == 0)
        {
            EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
            entityplayer.onKillCommand();
        } else
        {
            if(args[0].equalsIgnoreCase("Theray070696"))
            {
                sender.addChatMessage(new TextComponentString("<Terry Crews> BLOCK!"));

                if(Loader.isModLoaded("mario2"))
                {
                    Random rand = new Random();
                    int soundID = rand.nextInt(12) + 1;

                    SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation("mario2:player.spicemanBlock" + soundID));

                    if(sound != null)
                    {
                        sender.getEntityWorld().playSound(null, sender.getPosition(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }
                }

                return;
            }

            Entity entity = getEntity(server, sender, args[0]);
            entity.onKillCommand();
        }
    }

    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }

    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
    }
}
