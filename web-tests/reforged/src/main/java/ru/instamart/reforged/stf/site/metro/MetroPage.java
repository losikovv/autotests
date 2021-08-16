package ru.instamart.reforged.stf.site.metro;

import io.qameta.allure.Step;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.site.metro.block.footer.Footer;
import ru.instamart.reforged.stf.site.metro.block.header.Header;
import ru.instamart.reforged.stf.page.StfPage;

public final class MetroPage implements StfPage, MetroCheck {

    @Override
    @Step("Открыть страницу тенанта метро")
    public String pageUrl() {
        return "";
    }

    @Override
    public void goToPage() {
        Kraken.open(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth());
    }

    public Header interactHeader() {
        return header;
    }

    public Footer interactFooter() {
        return footer;
    }
}