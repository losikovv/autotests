package ru.instamart.reforged.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class Container {

    @Getter
    private final WebElement container;
}
