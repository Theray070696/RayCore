package io.github.Theray070696.raycore.client.gui;

import io.github.Theray070696.raycore.client.gui.widget.Widget;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class GuiModular extends GuiScreen
{
    private List<Widget> widgets = new ArrayList<>();

    public GuiModular()
    {
        this.initializeWidgets();
    }

    protected void keyTyped(char c, int i)
    {
        if(i == 1)
        {
            mc.displayGuiScreen(null);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        for(Widget widget : widgets)
        {
            widget.drawScreen(mouseX, mouseY, partialTicks, fontRendererObj);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        for(Widget widget : widgets)
        {
            widget.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        for(Widget widget : widgets)
        {
            widget.mouseReleased(mouseX, mouseY, state);
        }

        super.mouseReleased(mouseX, mouseY, state);
    }

    protected void addWidget(Widget widget)
    {
        widgets.add(widget);
    }

    /**
     * Initialize widgets here.
     */
    public abstract void initializeWidgets();
}
