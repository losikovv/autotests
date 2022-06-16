package ru.instamart.reforged.core.cdp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.openqa.selenium.devtools.v102.emulation.model.DisplayFeature;
import org.openqa.selenium.devtools.v102.emulation.model.ScreenOrientation;
import org.openqa.selenium.devtools.v102.page.model.Viewport;

@Getter
@Builder(setterPrefix = "with")
@ToString
public final class DeviceMetrics {

    private int width;
    private int height;
    private int scaleFactor;
    private boolean mobile;
    private int scale;
    private int screenWight;
    private int screenHeight;
    private int positionX;
    private int positionY;
    private boolean dontSetVisibleSize;
    private ScreenOrientation screenOrientation;
    private Viewport viewport;
    private DisplayFeature displayFeature;
}
