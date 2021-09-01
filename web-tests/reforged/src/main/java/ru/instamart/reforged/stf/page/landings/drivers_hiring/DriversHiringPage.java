package ru.instamart.reforged.stf.page.landings.drivers_hiring;

import ru.instamart.reforged.stf.page.StfPage;

public class DriversHiringPage implements StfPage, DriversHiringCheck {

    @Override
    public String pageUrl() {
        return "landings/driver-job";
    }
}
