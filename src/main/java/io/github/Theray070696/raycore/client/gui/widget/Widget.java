package io.github.Theray070696.raycore.client.gui.widget;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class Widget
{
    public abstract void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer);

    public abstract void mouseReleased(int mouseX, int mouseY, int state);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton);
}
