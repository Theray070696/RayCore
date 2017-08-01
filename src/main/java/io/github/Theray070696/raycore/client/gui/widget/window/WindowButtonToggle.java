package io.github.Theray070696.raycore.client.gui.widget.window;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WindowButtonToggle extends WindowButton
{
    protected boolean toggle = false;

    public WindowButtonToggle(int left, int top, int right, int bottom)
    {
        super(left, top, right, bottom);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, int windowX, int windowY, int windowWidth, int windowHeight)
    {
        super.drawScreen(mouseX, mouseY, partialTicks, fontRenderer, windowX, windowY, windowWidth, windowHeight);

        drawSmallString(fontRenderer, toggle ? "Enabled" : "Disabled", left + 3 + windowX, (bottom - 6) + windowY, 0xffffff);
    }

    @Override
    public void onButtonClicked()
    {
        toggle = !toggle;
    }
}
