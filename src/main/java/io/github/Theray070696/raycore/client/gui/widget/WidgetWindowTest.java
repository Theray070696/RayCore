package io.github.Theray070696.raycore.client.gui.widget;

import io.github.Theray070696.raycore.client.gui.widget.window.WindowButtonToggle;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WidgetWindowTest extends WidgetWindow
{
    public WidgetWindowTest()
    {
        super("Test Window", 80, 128);
    }

    @Override
    public void initializeFeatures()
    {
        this.addFeature(new WindowButtonToggle(50, 17, 78, 26));
    }
}
