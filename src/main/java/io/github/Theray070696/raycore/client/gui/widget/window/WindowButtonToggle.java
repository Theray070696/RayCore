package io.github.Theray070696.raycore.client.gui.widget.window;

import io.github.Theray070696.raycore.client.gui.widget.WidgetWindow;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WindowButtonToggle extends WindowFeature
{
    private boolean toggle = false;

    private int left;
    private int top;
    private int right;
    private int bottom;

    public WindowButtonToggle(int left, int top, int right, int bottom)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, int windowX, int windowY, int windowLength, int windowHeight)
    {
        button(left + windowX, top + windowY, right + windowX, bottom + windowY);
        drawSmallString(fontRenderer, toggle ? "Enabled" : "Disabled", left + 4 + windowX, (bottom - 6) + windowY, 0xffffff);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, int windowX, int windowY)
    {
        if(left + windowX < mouseX && right + windowX > mouseX && top + windowY < mouseY && bottom + windowY > mouseY)
        {
            toggle = !toggle;
        }
    }

    public void button(int x, int y, int x1, int y1)
    {
        WidgetWindow.drawBorderedRect(x, y, x1, y1, 1, 0xff5e5e5e, 0xff525252);
    }

    public void drawSmallString(FontRenderer fontRenderer, String string, int x, int y, int color)
    {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        x *= 2;
        y *= 2;
        fontRenderer.drawString(string, x, y, color);
        GL11.glScalef(2F, 2F, 2F);
    }
}
