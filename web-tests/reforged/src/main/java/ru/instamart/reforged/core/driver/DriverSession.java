package ru.instamart.reforged.core.driver;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;

@Data
public final class DriverSession {

    private final WebDriver driver;
    private final DevTools devTools;
}
