package io.github.Theray070696.raycore.client.gui.widget;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WidgetText extends Widget
{
    private String text;
    private int color;
    private boolean dropShadow;

    public WidgetText(int xPos, int yPos, String text, int color)
    {
        this(xPos, yPos, text, color, false);
    }

    public WidgetText(int xPos, int yPos, String text, int color, boolean dropShadow)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.color = color;
        this.dropShadow = dropShadow;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer)
    {
        fontRenderer.drawString(text, xPos, yPos, color, dropShadow);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {}

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
}
