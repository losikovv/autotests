package ru.instamart.reforged.next.page.landings.drivers_hiring;

import ru.instamart.reforged.next.page.StfPage;

public class DriversHiringPage implements StfPage, DriversHiringCheck {

    @Override
    public String pageUrl() {
        return "landings/driver-job";
    }
}
