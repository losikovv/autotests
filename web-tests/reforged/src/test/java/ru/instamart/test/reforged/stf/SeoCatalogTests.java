package ru.instamart.test.reforged.stf;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.stf.page.StfRouter.seo;

@Epic("STF UI")
@Feature("Seo Каталог")
public final class SeoCatalogTests extends BaseTest {

    @CaseId(1802)
    @Test(  description = "Тест доступности страницы SEO-каталога",
            groups = {"sbermarket-acceptance","sbermarket-regression"}
    )
    public void successCheckSeoPage() {
        seo().goToPage();
        seo().checkPageIsAvailable();
    }

    @CaseId(1803)
    @Test(  description = "Тест доступности товаров на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successCheckProductsOnSeoCatalog() {
        seo().goToPage();
        seo().checkProductGridVisible();
    }

    @CaseId(1804)
    @Test(  description = "Тест открытия карточки товара на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successOpenItemCardOnSeoCatalog() {
        seo().goToPage();
        //TODO: Костыль из-за бейсик авторизации
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + seo().pageUrl());
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1805)
    @Test(  description = "Тест на ввод адреса в модалке после добавления товара из карточки на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successSetShippingAddressAfterAddingProductFromItemCardOnSeoCatalog() {
        seo().goToPage();
        //TODO: Костыль из-за бейсик авторизации
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + seo().pageUrl());
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();

        seo().interactHeader().interactAddress().fillAddress(Addresses.Moscow.defaultAddress());
        seo().interactHeader().interactAddress().selectFirstAddress();
        seo().interactHeader().interactAddress().clickOnSave();
        seo().interactProductCard().checkProductCardVisible();
    }

    @CaseId(1806)
    @Test(  description = "Тест авторизации при попытке добавления товара в корзину на странице SEO-каталога",
            groups = {"sbermarket-regression"}
    )
    public void successAuthFromItemCardOnSeoCatalog() {
        seo().goToPage();
        //TODO: Костыль из-за бейсик авторизации
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL + CoreProperties.DEFAULT_RETAILER + seo().pageUrl());
        seo().openFirstProductCardOnTaxon();
        seo().interactProductCard().clickOnBuy();

        seo().interactHeader().interactAddress().clickToLogin();
        seo().interactAuthModal().authViaPhone(UserManager.getUser());
        seo().interactHeader().checkProfileButtonVisible();
    }
}
