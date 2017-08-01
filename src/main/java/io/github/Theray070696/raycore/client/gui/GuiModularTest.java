package io.github.Theray070696.raycore.client.gui;

import io.github.Theray070696.raycore.client.gui.widget.WidgetText;
import io.github.Theray070696.raycore.client.gui.widget.WidgetTextButton;
import io.github.Theray070696.raycore.client.gui.widget.WidgetWindowTest;
import io.github.Theray070696.raycore.util.LogHelper;

/**
 * Created by Theray070696 on 8/1/2017.
 */
public class GuiModularTest extends GuiModular
{
    @Override
    public void initializeWidgets()
    {
        this.addWidget(new WidgetText(100, 100, "This is just text. Nothing more, nothing less", 0xffffff));
        this.addWidget(new WidgetWindowTest());
        this.addWidget(new WidgetTextButton(150, 150, "Text Button Test", 0xffffff)
        {
            @Override
            public void onButtonClicked()
            {
                LogHelper.info("Text Button Clicked!");
            }
        });
    }
}
