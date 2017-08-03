package io.github.Theray070696.raycore.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class WidgetTextButton extends WidgetText
{
    public WidgetTextButton(int xPos, int yPos, String text, int color)
    {
        this(xPos, yPos, text, color, false);
    }

    public WidgetTextButton(int xPos, int yPos, String text, int color, boolean dropShadow)
    {
        super(xPos, yPos, text, color, dropShadow);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(this.text);

        if(xPos - 2 < mouseX && stringWidth + 2 + xPos > mouseX && yPos - 2 < mouseY && yPos + 10 > mouseY)
        {
            onButtonClicked();
        }
    }

    public abstract void onButtonClicked();
}
