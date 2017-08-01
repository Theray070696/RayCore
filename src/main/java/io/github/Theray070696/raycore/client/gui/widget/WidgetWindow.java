package io.github.Theray070696.raycore.client.gui.widget;

import io.github.Theray070696.raycore.client.gui.widget.window.WindowFeature;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class WidgetWindow extends Widget
{
    private List<WindowFeature> windowFeatures = new ArrayList<>();

    private String windowTitle;

    private int windowLength;
    private int windowHeight;

    private int dragWindowX = 100;
    private int dragWindowY = 0;
    private boolean dragWindow = false;
    private boolean windowExpanded = false;

    public WidgetWindow(String windowTitle, int windowLength, int windowHeight)
    {
        this.windowTitle = windowTitle;
        this.initializeFeatures();
        this.windowLength = windowLength;
        this.windowHeight = windowHeight;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer)
    {
        mouseDraggedWindow(mouseX, mouseY);

        drawBorderedRect(dragWindowX, 2 + dragWindowY, windowLength + dragWindowX, 14 + dragWindowY, 1, 0xff3c3c3c, 0x804d4d4d);
        drawBorderedRect(windowLength - 10 + dragWindowX, 4 + dragWindowY, windowLength - 2 + dragWindowX, 12 + dragWindowY, 1, 0xff5e5e5e, 0x805e5e5e);
        fontRenderer.drawString(windowExpanded ? "x" : "+", windowLength - 8 + dragWindowX, 4 + dragWindowY, 0xffffff);
        fontRenderer.drawString(windowTitle, 4 + dragWindowX, 4 + dragWindowY, 0xffffff);
        if(windowExpanded)
        {
            drawBorderedRect(dragWindowX, 15 + dragWindowY, windowLength + dragWindowX, windowHeight + dragWindowY, 1, 0xff3c3c3c, 0x804d4d4d);

            for(WindowFeature feature : windowFeatures)
            {
                feature.drawScreen(mouseX, mouseY, partialTicks, fontRenderer, dragWindowX, dragWindowY, windowLength, windowHeight);
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
            dragWindowX = mouseX - 30;
            dragWindowY = mouseY - 5;
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if(dragWindowX < mouseX && windowLength - 8 + dragWindowX > mouseX && 2 + dragWindowY < mouseY && 14 + dragWindowY > mouseY)
        {
            dragWindow = true;
        }
        if(windowLength - 8 + dragWindowX < mouseX && windowLength + dragWindowX > mouseX && 4 + dragWindowY < mouseY && 12 + dragWindowY > mouseY)
        {
            windowExpanded = !windowExpanded;
        }

        for(WindowFeature feature : windowFeatures)
        {
            feature.mouseClicked(mouseX, mouseY, mouseButton, dragWindowX, dragWindowY);
        }
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC)
    {
        Gui.drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
        Gui.drawRect(x + size, y + size, x1, y, borderC);
        Gui.drawRect(x, y, x + size, y1, borderC);
        Gui.drawRect(x1, y1, x1 - size, y + size, borderC);
        Gui.drawRect(x, y1 - size, x1, y1, borderC);
    }

    public abstract void initializeFeatures();

    protected void addFeature(WindowFeature feature)
    {
        windowFeatures.add(feature);
    }
}
