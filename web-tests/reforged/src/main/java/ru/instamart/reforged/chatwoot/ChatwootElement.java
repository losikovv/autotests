package ru.instamart.reforged.chatwoot;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface ChatwootElement {
    Element snackbar = new Element(By.xpath("//div[@class='ui-snackbar']"), "Всплывающее сообщение");
}
