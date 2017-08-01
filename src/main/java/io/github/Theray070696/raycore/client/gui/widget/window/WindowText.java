package io.github.Theray070696.raycore.client.gui.widget.window;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WindowText extends WindowFeature
{
    private int xPos;
    private int yPos;
    private String text;
    private int color;
    private boolean dropShadow;

    public WindowText(int xPos, int yPos, String text, int color)
    {
        this(xPos, yPos, text, color, false);
    }

    public WindowText(int xPos, int yPos, String text, int color, boolean dropShadow)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, int windowX, int windowY, int windowLength, int windowHeight)
    {
        fontRenderer.drawString(text, xPos, yPos, color, dropShadow);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton, int windowX, int windowY) {}
}
