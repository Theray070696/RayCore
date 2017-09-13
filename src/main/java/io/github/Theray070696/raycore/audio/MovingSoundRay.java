package io.github.Theray070696.raycore.audio;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

/**
 * Created by Theray070696 on 8/21/2017
 */
public class MovingSoundRay extends MovingSound
{
    private final Entity entity;

    public MovingSoundRay(Entity entity, SoundEvent sound, SoundCategory category)
    {
        super(sound, category);

        this.entity = entity;
    }

    public void update()
    {
        if(this.entity.isDead)
        {
            this.donePlaying = true;
        } else
        {
            this.xPosF = (float) this.entity.posX;
            this.yPosF = (float) this.entity.posY;
            this.zPosF = (float) this.entity.posZ;
        }
    }
}
