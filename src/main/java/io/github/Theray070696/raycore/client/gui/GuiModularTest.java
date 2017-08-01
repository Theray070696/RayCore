package io.github.Theray070696.raycore.client.gui;

import io.github.Theray070696.raycore.client.gui.widget.WidgetText;
import io.github.Theray070696.raycore.client.gui.widget.WidgetWindowTest;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class GuiModularTest extends GuiModular
{
    @Override
    public void initializeWidgets()
    {
        this.addWidget(new WidgetText(100, 100, "test", 0xffffff));
        this.addWidget(new WidgetWindowTest());
    }
}
