package ru.instamart.reforged.stf.page.landings.food;

import ru.instamart.reforged.core.config.BasicProperties;
import ru.instamart.reforged.stf.page.StfPage;

public final class FoodPage implements StfPage {

    @Override
    public String pageUrl() {
        return BasicProperties.FOOD_LANDING_URL;
    }
}
