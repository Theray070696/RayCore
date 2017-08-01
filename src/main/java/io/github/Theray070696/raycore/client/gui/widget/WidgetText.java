package io.github.Theray070696.raycore.client.gui.widget;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WidgetText extends Widget
{
    private int xPos;
    private int yPos;
    private String text;
    private int color;

    public WidgetText(int xPos, int yPos, String text, int color)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.text = text;
        this.color = color;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer)
    {
        fontRenderer.drawString(text, xPos, yPos, color);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {}

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {}
}
