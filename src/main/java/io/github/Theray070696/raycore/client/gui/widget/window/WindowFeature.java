package io.github.Theray070696.raycore.client.gui.widget.window;

import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class WindowFeature
{
    public abstract void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, int windowX, int windowY, int windowWidth, int windowHeight);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton, int windowX, int windowY);

    public void drawSmallString(FontRenderer fontRenderer, String string, int x, int y, int color)
    {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        x *= 2;
        y *= 2;
        fontRenderer.drawString(string, x, y, color);
        GL11.glScalef(2F, 2F, 2F);
    }
}
