package io.github.Theray070696.raycore.client.gui.widget.window;

import io.github.Theray070696.raycore.client.gui.util.GuiUtils;
import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class WindowButton extends WindowFeature
{
    protected int left;
    protected int top;
    protected int right;
    protected int bottom;

    public WindowButton(int left, int top, int right, int bottom)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, int windowX, int windowY, int windowWidth, int windowHeight)
    {
        button(left + windowX, top + windowY, right + windowX, bottom + windowY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, int windowX, int windowY)
    {
        if(left + windowX < mouseX && right + windowX > mouseX && top + windowY < mouseY && bottom + windowY > mouseY)
        {
            onButtonClicked();
        }
    }

    public void button(int x, int y, int x1, int y1)
    {
        GuiUtils.drawBorderedRect(x, y, x1, y1, 1, 0xff5e5e5e, 0xff525252);
    }

    public abstract void onButtonClicked();
}
