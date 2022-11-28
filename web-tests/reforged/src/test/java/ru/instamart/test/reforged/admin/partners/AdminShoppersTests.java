package ru.instamart.test.reforged.admin.partners;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.api.model.shopper.admin.ShopperV1;
import ru.instamart.kraken.data.ShoppersData;
import ru.instamart.kraken.data.Vehicles;
import ru.instamart.kraken.data.VehiclesData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.data_provider.CarTypeProvider;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.Group.*;
import java.util.Set;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Партнеры")
@Story("Страница 'Список партнеров' admin/spa/shoppers")
public final class AdminShoppersTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(2042)
    @Test(description = "Создание партнера",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void shoppersCreateTest() {
        final var shopper = ShoppersData.shoppers();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppers().goToPage();
        shoppers().clickToCreateShoppers();

        shoppersCreate().save();
        shoppersCreate().checkRequiredFieldValidation("Имя и фамилия");
        shoppersCreate().checkRequiredFieldValidation("Телефон");
        shoppersCreate().checkRequiredFieldValidation("Логин");
        shoppersCreate().checkRequiredFieldValidation("Текущий магазин");
        shoppersCreate().checkRequiredFieldValidation("Роли сотрудника");

        shoppersCreate().setName(shopper.getName());
        shoppersCreate().setPhone(shopper.getPhone());
        shoppersCreate().setLogin(shopper.getLogin());
        shoppersCreate().setCurrentShop(shopper.getCurrentShop());
        shoppersCreate().setShoppersRoles(shopper.getRoles());
        shoppersCreate().save();

        shoppersCreate().checkRequiredPasswordValidation();

        shoppersCreate().setPassword(shopper.getPassword());
        shoppersCreate().setInn(shopper.getInn());
        shoppersCreate().save();

        shoppersEdit().checkInformationFor("Имя и фамилия", shopper.getName());
        shoppersEdit().checkInformationFor("Телефон", shopper.getPhone());
        shoppersEdit().checkInformationFor("Логин", shopper.getLogin());
        shoppersEdit().checkInformationFor("Текущий магазин", shopper.getCurrentShop());
        shoppersEdit().checkInformationFor("Роли сотрудника", shopper.getRolesName());
        shoppersEdit().checkInformationFor("ИНН", shopper.getInn());
        shoppersEdit().assertAll();
    }

    @CaseId(2043)
    @Test(description = "Редактирование партнера",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void shoppersEditTest() {
        final var shopperData = ShoppersData.shoppers();
        final var courier = ShoppersData.courier();
        final var shopper = helper.createShopper(shopperData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppersEdit().goToPage(shopper.getId());
        shoppersEdit().waitPageLoad();
        shoppersEdit().checkName(shopperData.getName());
        shoppersEdit().checkLogin(shopperData.getLogin());
        shoppersEdit().checkPhone(shopperData.getPhone());
        shoppersEdit().assertAll();

        shoppersEdit().setName(courier.getName());
        shoppersEdit().setPhone(courier.getPhone());
        shoppersEdit().setLogin(courier.getLogin());
        shoppersEdit().save();
        shoppersEdit().interactAlert().checkSuccessFlashVisible();

        shoppersEdit().clickToTab("Занятость");
        shoppersEdit().waitPageLoad();
        shoppersEdit().checkCurrentShop(shopperData.getCurrentShop());
        shoppersEdit().checkRoles(shopperData.getRolesName());
        shoppersEdit().checkInn(shopperData.getInn());
        shoppersEdit().assertAll();

        shoppersEdit().clearCurrentShop();
        shoppersEdit().setCurrentShop(courier.getCurrentShop());
        shoppersEdit().clearShoppersRole();
        shoppersEdit().setShoppersRoles(courier.getRoles());
        shoppersEdit().save();
        shoppersEdit().interactAlert().checkSuccessFlashVisible();

        shoppersEdit().clickToTab("Транспорт");
        shoppersEdit().waitPageLoad();
        shoppersEdit().checkEmptyCar();

        shoppersEdit().clickToTab("Оборудование");
        shoppersEdit().waitPageLoad();
        shoppersEdit().checkAddEquipmentButtonVisible();

        shoppersEdit().clickToTab("Униформа");
        shoppersEdit().waitPageLoad();
        shoppersEdit().checkAddUniformButtonVisible();
    }

    @CaseId(2045)
    @Test(description = "Редактирование транспорта в карточке партнера",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS},
            dataProviderClass = CarTypeProvider.class,
            dataProvider = "carType"
    )
    public void shoppersEditVehicleTest(final ShopperV1 shopper, final Vehicles vehicles) {
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppersEdit().goToPage(shopper.getId());
        shoppersEdit().waitPageLoad();

        shoppersEdit().clickToTab("Транспорт");
        shoppersEdit().waitPageLoad();

        shoppersEdit().clickToAddVehicle();
        shoppersEdit().interactCarTypeModal().checkModalVisible();
        shoppersEdit().interactCarTypeModal().selectCarType(vehicles.getKind().getName());
        shoppersEdit().interactCarTypeModal().clickOnSelect();

        shoppersEdit().fillNewModel(vehicles.getModel());
        shoppersEdit().fillNewNumber(vehicles.getNumber());
        shoppersEdit().fillNewVolume(vehicles.getVolume());
        shoppersEdit().save();
        shoppersEdit().interactAlert().checkSuccessFlashVisible();
    }

    @Test(description = "Добавление транспортного средства в избранное",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void addVehicleToFavorite() {
        final var shopperData = ShoppersData.courier();
        final var shopper = helper.createShopper(shopperData);
        final var vehicles = VehiclesData.car();
        helper.addVehicle(shopper.getId(), vehicles);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppersEdit().goToPage(shopper.getId());
        shoppersEdit().waitPageLoad();

        shoppersEdit().clickToTab("Транспорт");
        shoppersEdit().waitPageLoad();
        shoppersEdit().clickOnFirstUnselectedHeart();
        shoppersEdit().interactPopover().checkPopoverVisible("Сделать основным транспортом?");
        shoppersEdit().interactPopover().clickToCancel();
        shoppersEdit().checkButtonSaveInactive();

        shoppersEdit().clickOnFirstUnselectedHeart();
        shoppersEdit().interactPopover().checkPopoverVisible("Сделать основным транспортом?");
        shoppersEdit().interactPopover().clickToOk();
        shoppersEdit().save();
        shoppersEdit().interactAlert().checkSuccessFlashVisible();
    }

    @Test(description = "Удаление транспортного средства",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void deleteVehicle() {
        final var shopperData = ShoppersData.courier();
        final var shopper = helper.createShopper(shopperData);
        final var vehicles = VehiclesData.car();
        helper.addVehicle(shopper.getId(), vehicles);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppersEdit().goToPage(shopper.getId());
        shoppersEdit().waitPageLoad();

        shoppersEdit().clickToTab("Транспорт");
        shoppersEdit().waitPageLoad();

        shoppersEdit().clickToDeleteOnFirstVehicles();
        shoppersEdit().interactPopover().checkPopoverVisible("Удалить транспорт?");
        shoppersEdit().interactPopover().clickToCancel();
        shoppersEdit().checkButtonSaveInactive();

        shoppersEdit().clickToDeleteOnFirstVehicles();
        shoppersEdit().interactPopover().checkPopoverVisible("Удалить транспорт?");
        shoppersEdit().interactPopover().clickToOk();
        shoppersEdit().save();
        shoppersEdit().interactAlert().checkSuccessFlashVisible();
    }

    @CaseId(1870)
    @Test(description = "Добавление тегов к партнеру",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void addTagsToShoppersTest() {
        final var shopperData = ShoppersData.shoppers();
        helper.createShopper(shopperData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppers().goToPage();
        shoppers().waitPageLoad();

        shoppers().fillName(shopperData.getName());

        shoppers().waitPageLoad();

        shoppers().checkShopperWasFound(shopperData.getName());
        shoppers().clickOnFirstAddTagButton();

        shoppers().interactAddTagModal().checkModalVisible();
        shoppers().interactAddTagModal().checkAddTagsButtonInactive();

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownVisible();
        var firstTagName = shoppers().interactAddTagModal().getTagNameFromList(1);
        shoppers().interactAddTagModal().clickOnTagInList(1);
        shoppers().interactAddTagModal().checkTagSelected(firstTagName);

        var secondTagName = shoppers().interactAddTagModal().getTagNameFromList(2);
        shoppers().interactAddTagModal().clickOnTagInList(2);
        shoppers().interactAddTagModal().checkTagSelected(secondTagName);

        var thirdTagName = shoppers().interactAddTagModal().getTagNameFromList(3);
        var tags = Set.of(firstTagName, secondTagName, thirdTagName);

        shoppers().interactAddTagModal().clickOnTagInList(3);
        shoppers().interactAddTagModal().checkTagSelected(thirdTagName);

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownInvisible();

        shoppers().interactAddTagModal().compareSelectedTagsWithActual(tags);
        shoppers().interactAddTagModal().checkTagsInFieldHaveRemoveButtons();
        shoppers().interactAddTagModal().checkAddTagsButtonActive();
        shoppers().interactAddTagModal().clickOnAddTagsButton();
        shoppers().interactAddTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().clickOnCollapseTagListButton();
        shoppers().compareSelectedTagsWithActual(tags);

        shoppers().clickOnFirstAddTagButton();
        shoppers().interactAddTagModal().checkModalVisible();
        shoppers().interactAddTagModal().checkSelectedTagsInFieldEmpty();
        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownVisible();

        shoppers().interactAddTagModal().checkTagSelectedExcludeInTagsList(tags);
    }

    @CaseId(1871)
    @Test(description = "Удаление связки партнер/тег",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void deleteTagsFromShoppersTest() {
        final var shopperData = ShoppersData.shoppers();
        helper.createShopper(shopperData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppers().goToPage();
        shoppers().waitPageLoad();

        shoppers().fillName(shopperData.getName());

        shoppers().waitPageLoad();

        shoppers().checkShopperWasFound(shopperData.getName());
        shoppers().clickOnFirstAddTagButton();

        shoppers().interactAddTagModal().checkModalVisible();
        shoppers().interactAddTagModal().checkAddTagsButtonInactive();

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownVisible();
        var firstTagName = shoppers().interactAddTagModal().getTagNameFromList(1);
        shoppers().interactAddTagModal().clickOnTagInList(1);
        shoppers().interactAddTagModal().checkTagSelected(firstTagName);

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownInvisible();

        shoppers().interactAddTagModal().checkAddTagsButtonActive();
        shoppers().interactAddTagModal().clickOnAddTagsButton();
        shoppers().interactAddTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().clickToDeleteTag(1);
        shoppers().interactDeleteTagModal().checkModalVisible();
        shoppers().interactDeleteTagModal().clickOnCancelButton();
        shoppers().interactDeleteTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().checkTagWithNameVisible(firstTagName);

        shoppers().clickToDeleteTag(1);
        shoppers().interactDeleteTagModal().checkModalVisible();
        shoppers().interactDeleteTagModal().clickOnApproveButton();
        shoppers().interactDeleteTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().checkTagWithNameNotVisible(firstTagName);
    }

    @CaseId(1872)
    @Test(description = "Отображение 3+ тегов",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void addMultipleTagsToShoppersTest() {
        final var shopperData = ShoppersData.shoppers();
        helper.createShopper(shopperData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppers().goToPage();
        shoppers().waitPageLoad();

        shoppers().fillName(shopperData.getName());

        shoppers().waitPageLoad();
        shoppers().checkSpinnerNotVisible();

        shoppers().checkShopperWasFound(shopperData.getName());
        shoppers().clickOnFirstAddTagButton();

        shoppers().interactAddTagModal().checkModalVisible();
        shoppers().interactAddTagModal().checkAddTagsButtonInactive();

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownVisible();
        var firstTagName = shoppers().interactAddTagModal().getTagNameFromList(1);
        shoppers().interactAddTagModal().clickOnTagInList(1);
        shoppers().interactAddTagModal().checkTagSelected(firstTagName);

        var secondTagName = shoppers().interactAddTagModal().getTagNameFromList(2);
        shoppers().interactAddTagModal().clickOnTagInList(2);
        shoppers().interactAddTagModal().checkTagSelected(secondTagName);

        var thirdTagName = shoppers().interactAddTagModal().getTagNameFromList(3);
        shoppers().interactAddTagModal().clickOnTagInList(3);
        shoppers().interactAddTagModal().checkTagSelected(thirdTagName);

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownInvisible();

        shoppers().interactAddTagModal().checkAddTagsButtonActive();
        shoppers().interactAddTagModal().clickOnAddTagsButton();
        shoppers().interactAddTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().compareSelectedTagsQuantityWithActual(2);
        shoppers().checkAddTagButtonVisible();
        shoppers().checkCollapseTagListButtonText("Еще 1");

        shoppers().clickOnFirstAddTagButton();

        shoppers().interactAddTagModal().checkModalVisible();
        shoppers().interactAddTagModal().checkAddTagsButtonInactive();

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownVisible();

        var fourthTagName = shoppers().interactAddTagModal().getTagNameFromList(1);
        shoppers().interactAddTagModal().clickOnTagInList(1);
        shoppers().interactAddTagModal().checkTagSelected(fourthTagName);

        shoppers().interactAddTagModal().clickOnTagsSelector();
        shoppers().interactAddTagModal().checkTagsDropdownInvisible();

        shoppers().interactAddTagModal().checkAddTagsButtonActive();
        shoppers().interactAddTagModal().clickOnAddTagsButton();
        shoppers().interactAddTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().compareSelectedTagsQuantityWithActual(2);
        shoppers().checkAddTagButtonVisible();
        shoppers().checkCollapseTagListButtonText("Еще 2");

        shoppers().clickToDeleteTag(1);
        shoppers().interactDeleteTagModal().checkModalVisible();
        shoppers().interactDeleteTagModal().clickOnApproveButton();
        shoppers().interactDeleteTagModal().checkModalNotVisible();

        shoppers().waitPageLoad();

        shoppers().compareSelectedTagsQuantityWithActual(2);
        shoppers().checkAddTagButtonVisible();
        shoppers().checkCollapseTagListButtonText("Еще 1");
    }

    @CaseId(1873)
    @Test(description = "Фильтрация по тегам",
            groups = {OD_SHOPPERS_REGRESS, OD_SHOPPERS_SMOKE, OD_SMOKE, OD_REGRESS})
    public void filterShoppersOnTagsTest() {
        final var shopperData = ShoppersData.shoppers();
        helper.createShopper(shopperData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        shoppers().goToPage();
        shoppers().waitPageLoad();

        shoppers().clickOnTagsFilterSelector();

        shoppers().checkTagsDropdownVisible();
        var firstTagName = shoppers().getTagNameFromList(1);
        shoppers().clickOnTagInList(1);
        shoppers().checkTagSelected(firstTagName);

        shoppers().clickOnTagsFilterSelector();
        shoppers().checkTagsDropdownInvisible();

        shoppers().clickOnTagsFilterSelector();

        shoppers().checkTagsDropdownVisible();
        var secondTagName = shoppers().getTagNameFromList(2);
        shoppers().clickOnTagInList(2);
        shoppers().checkTagSelected(secondTagName);

        var tags = Set.of(firstTagName, secondTagName);

        shoppers().compareSelectedTagsInFieldWithActual(tags);

        shoppers().clickOnTagsFilterSelector();
        shoppers().checkTagsDropdownInvisible();

        shoppers().waitPageLoad();

        shoppers().checkTagsInFieldHaveRemoveButtons();

        shoppers().expandAllTags();

        shoppers().checkTagSelectedInFilterVisibleOnAllPartners(firstTagName);
        shoppers().checkTagSelectedInFilterVisibleOnAllPartners(secondTagName);
    }
}
