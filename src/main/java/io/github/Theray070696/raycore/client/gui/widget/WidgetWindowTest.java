package io.github.Theray070696.raycore.client.gui.widget;

import io.github.Theray070696.raycore.client.gui.widget.window.WindowButtonToggle;
import io.github.Theray070696.raycore.client.gui.widget.window.WindowText;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class WidgetWindowTest extends WidgetWindow
{
    public WidgetWindowTest()
    {
        super("Test Window", 80, 128, 50, 50);
    }

    @Override
    public void initializeFeatures()
    {
        this.addFeature(new WindowButtonToggle(50, 17, 78, 26));
        this.addFeature(new WindowText(4, 17, "Test Button", 0x00bf00));
    }
}
