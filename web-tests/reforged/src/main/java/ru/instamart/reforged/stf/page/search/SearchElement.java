package ru.instamart.reforged.stf.page.search;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.stf.block.header.Header;

public interface SearchElement {
    Header header = new Header();

    Button firstAddToCartButton = new Button(By.xpath("//button[@title='Добавить в корзину']"));
}
