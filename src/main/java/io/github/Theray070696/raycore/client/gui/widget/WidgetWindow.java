package io.github.Theray070696.raycore.client.gui.widget;

import io.github.Theray070696.raycore.client.gui.util.GuiUtils;
import io.github.Theray070696.raycore.client.gui.widget.window.WindowFeature;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class WidgetWindow extends Widget
{
    private List<WindowFeature> windowFeatures = new ArrayList<>();

    private String windowTitle;

    private int windowWidth;
    private int windowHeight;

    private boolean dragWindow = false;
    private boolean windowExpanded = true;

    public WidgetWindow(String windowTitle, int windowWidth, int windowHeight, int windowStartX, int windowStartY)
    {
        this.windowTitle = windowTitle;
        this.initializeFeatures();
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.xPos = windowStartX;
        this.yPos = windowStartY;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer)
    {
        mouseDraggedWindow(mouseX, mouseY);

        GuiUtils.drawBorderedRect(xPos, 2 + yPos, windowWidth + xPos, 14 + yPos, 1, 0xff3c3c3c, 0x804d4d4d);
        GuiUtils.drawBorderedRect(windowWidth - 10 + xPos, 4 + yPos, windowWidth - 2 + xPos, 12 + yPos, 1, 0xff5e5e5e, 0x805e5e5e);
        fontRenderer.drawString(windowExpanded ? "x" : "+", windowWidth - 8 + xPos, 4 + yPos, 0xffffff);
        fontRenderer.drawString(windowTitle, 4 + xPos, 4 + yPos, 0xffffff);
        if(windowExpanded)
        {
            GuiUtils.drawBorderedRect(xPos, 15 + yPos, windowWidth + xPos, windowHeight + yPos, 1, 0xff3c3c3c, 0x804d4d4d);

            for(WindowFeature feature : windowFeatures)
            {
                feature.drawScreen(mouseX, mouseY, partialTicks, fontRenderer, xPos, yPos, windowWidth, windowHeight);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        if(state == 0)
        {
            dragWindow = false;
        }
    }

    private void mouseDraggedWindow(int mouseX, int mouseY)
    {
        if(dragWindow)
        {
            xPos = mouseX - 30;
            yPos = mouseY - 5;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if(xPos < mouseX && windowWidth - 8 + xPos > mouseX && 2 + yPos < mouseY && 14 + yPos > mouseY)
        {
            dragWindow = true;
        }
        if(windowWidth - 8 + xPos < mouseX && windowWidth + xPos > mouseX && 4 + yPos < mouseY && 12 + yPos > mouseY)
        {
            windowExpanded = !windowExpanded;
        }

        for(WindowFeature feature : windowFeatures)
        {
            feature.mouseClicked(mouseX, mouseY, mouseButton, xPos, yPos);
        }
    }

    public abstract void initializeFeatures();

    protected void addFeature(WindowFeature feature)
    {
        windowFeatures.add(feature);
    }
}
