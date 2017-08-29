package io.github.Theray070696.raycore.client.gui.widget.window;

import net.minecraft.client.gui.FontRenderer;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public abstract class WindowFeature
{
    public abstract void keyTyped(char typedChar, int keyCode);

    public abstract void drawScreen(int mouseX, int mouseY, float partialTicks, FontRenderer fontRenderer, int windowX, int windowY, int windowWidth, int windowHeight);

    public abstract void mouseClicked(int mouseX, int mouseY, int mouseButton, int windowX, int windowY);
}
