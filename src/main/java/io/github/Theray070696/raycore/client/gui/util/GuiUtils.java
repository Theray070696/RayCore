package io.github.Theray070696.raycore.client.gui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 8/29/2017
 */
public class GuiUtils
{
    public static void drawSmallString(int x, int y, String string, int color)
    {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        x *= 2;
        y *= 2;
        Minecraft.getMinecraft().fontRenderer.drawString(string, x, y, color);
        GL11.glScalef(2F, 2F, 2F);
    }

    public static void drawSmallSplitString(int x, int y, String text, int color, int splitLength)
    {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        x *= 2;
        y *= 2;
        Minecraft.getMinecraft().fontRenderer.drawSplitString(text, x, y, splitLength, color);
        GL11.glScalef(2F, 2F, 2F);
    }

    public static void drawBorderedRect(int left, int top, int right, int bottom, int borderSize, int borderColor, int insideColor)
    {
        Gui.drawRect(left + borderSize, top + borderSize, right - borderSize, bottom - borderSize, insideColor);
        Gui.drawRect(left + borderSize, top + borderSize, right, top, borderColor);
        Gui.drawRect(left, top, left + borderSize, bottom, borderColor);
        Gui.drawRect(right, bottom, right - borderSize, top + borderSize, borderColor);
        Gui.drawRect(left, bottom - borderSize, right, bottom, borderColor);
    }
}
