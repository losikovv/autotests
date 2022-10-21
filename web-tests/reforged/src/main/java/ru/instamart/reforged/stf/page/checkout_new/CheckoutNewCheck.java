package ru.instamart.reforged.stf.page.checkout_new;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.kraken.util.StringUtil.convertDigitsStringToPhoneNumber;
import static ru.instamart.kraken.util.StringUtil.convertDigitsStringToPhoneNumberWithBracketsNoDash;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CheckoutNewCheck extends Check, CheckoutNewElement {

    @Step("Проверяем что страница прогрузилась")
    default void checkSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(loadingSpinner);
    }

    @Step("Проверяем что страница появился спиннер")
    default void checkSpinnerVisible() {
        waitAction().shouldBeVisible(loadingSpinner);
    }

    @Step("Проверяем, что открыта вкладка 'Самовывоз'")
    default void checkPickupTabOpened() {
        Kraken.waitAction().shouldBeVisible(pickupTabInfo);
    }

    @Step("Проверяем, что указан адрес доставки: '{expectedAddress}'")
    default void checkDeliveryAddress(final String expectedAddress) {
        Assert.assertEquals(addressTitle.getText(), expectedAddress, "Адрес доставки отличается от ожидаемого");
    }

    @Step("Проверяем, что указан адрес магазина самовывоза: '{expectedStoreAddress}'")
    default void checkPickupStoreAddress(final String expectedStoreAddress) {
        Assert.assertEquals(pickupStoreAddress.getText(), expectedStoreAddress, "Адрес магазина самовывоза отличается от ожидаемого");
    }

    @Step("Проверяем, что в поле 'Кв, офис' значение: {expectedApartmentValue}")
    default void checkApartmentValue(final String expectedApartmentValue) {
        Assert.assertEquals(apartment.getValue(), expectedApartmentValue, "Значение в поле 'Кв. офис' отличается от ожидаемого");
    }

    @Step("Проверяем, что в поле 'Подъезд' значение: {expectedEntranceValue}")
    default void checkEntranceValue(final String expectedEntranceValue) {
        Assert.assertEquals(entrance.getValue(), expectedEntranceValue, "Значение в поле 'Подъезд' отличается от ожидаемого");
    }

    @Step("Проверяем, что в поле 'Этаж' значение: {expectedFloorValue}")
    default void checkFloorValue(final String expectedFloorValue) {
        Assert.assertEquals(floor.getValue(), expectedFloorValue, "Значение в поле 'Этаж' отличается от ожидаемого");
    }

    @Step("Проверяем, что в поле 'Домофон' значение: {expectedDoorPhoneValue}")
    default void checkDoorPhoneValue(final String expectedDoorPhoneValue) {
        Assert.assertEquals(doorPhone.getValue(), expectedDoorPhoneValue, "Значение в поле 'Домофон' отличается от ожидаемого");
    }

    @Step("Проверяем, что заголовок блока слотов доставки содержит текст: '{expectedText}'")
    default void checkDeliveryTitleContains(final String expectedText) {
        Assert.assertEquals(openDeliverySlotsModalInTitle.getText().toLowerCase(), expectedText.toLowerCase(),
                String.format("Заголовок блока слотов: '%s' не содержит текст: '%s'", openDeliverySlotsModalInTitle.getText(), expectedText));
    }

    @Step("Проверяем, что отображаются слоты доставки")
    default void checkDeliverySlotsVisible() {
        Kraken.waitAction().shouldBeVisible(allDeliverySlots);
    }

    @Step("Проверяем, что отображаются слоты доставки OnDemand")
    default void checkOnDemandDeliverySlotsVisible() {
        Kraken.waitAction().shouldBeVisible(onDemandDeliverySlots);
    }

    @Step("Проверяем, что отображается выбранный слот доставки")
    default void checkActiveDeliverySlotsVisible() {
        Kraken.waitAction().shouldBeVisible(activeDeliverySlots);
    }

    @Step("Проверяем, что отображается ошибка в блоке 'Столы доставки' (блок в красной рамке)")
    default void checkDeliverySlotInvalidBorderVisible() {
        Kraken.waitAction().shouldBeVisible(deliverySlotBlockInvalidMark);
    }

    @Step("Проверяем, что не отображаются выбранные слоты доставки")
    default void checkActiveDeliverySlotsNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(activeDeliverySlots);
    }

    @Step("Проверяем, проверяем количество выбранных слотов доставки")
    default void checkSelectedDeliverySlotsCount(final int expectedActiveSlotsCount) {
        Assert.assertEquals(activeDeliverySlots.elementCount(), expectedActiveSlotsCount, "Количество выбранных слотов отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается дата в слоте доставки")
    default void checkDeliverySlotDateVisible() {
        Kraken.waitAction().shouldBeVisible(deliverySlotDate);
    }

    @Step("Проверяем, что дата в выбранном слоте доставки: '{expectedDeliverySlotDate}'")
    default void checkActiveDeliverySlotDate(final String expectedDeliverySlotDate) {
        Assert.assertEquals(activeDeliverySlotDate.getText(), expectedDeliverySlotDate, "Дата в выбранном слоте доставки отличается от ожидаемой");
    }

    @Step("Проверяем, что отображается время в слоте доставки")
    default void checkDeliverySlotDeliveryTimeVisible() {
        Kraken.waitAction().shouldBeVisible(deliverySlotTime);
    }

    @Step("Проверяем, что время в выбранном слоте доставки: '{expectedDeliverySlotTime}'")
    default void checkActiveDeliverySlotTime(final String expectedDeliverySlotTime) {
        Assert.assertEquals(activeDeliverySlotTime.getText(), expectedDeliverySlotTime, "Время в выбранном слоте доставки отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается цена в слоте доставки")
    default void checkDeliverySlotPriceVisible() {
        Kraken.waitAction().shouldBeVisible(deliverySlotPrice);
    }

    @Step("Проверяем, что стоимость в выбранном слоте доставки: '{expectedDeliverySlotPrice}'")
    default void checkActiveDeliverySlotCost(final String expectedDeliverySlotPrice) {
        Assert.assertEquals(activeDeliverySlotCost.getText(), expectedDeliverySlotPrice, "Стоимость в выбранном слоте доставки отличается от ожидаемой");
    }

    @Step("Проверяем, что активен слот в {activeSlotPosition}-й позиции")
    default void checkSlotActive(final int activeSlotPosition) {
        Kraken.waitAction().shouldBeVisible(activeSlotByPosition, activeSlotPosition);
    }

    @Step("Проверяем, что название дня выбранного слота содержит: {expectedText}")
    default void checkSelectedSlotDayNameContainsText(final String expectedText) {
        Assert.assertTrue(activeDeliverySlotDate.getText().toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Название дня выбранного слота: '%s' не содержит '%s'", activeDeliverySlotDate.getText(), expectedText));
    }

    @Step("Проверяем, что временной интервал выбранного слота содержит: {expectedText}")
    default void checkSelectedSlotTimeContainsText(final String expectedText) {
        Assert.assertTrue(activeDeliverySlotTime.getText().toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Интервал времени выбранного слота: '%s' не содержит '%s'", activeDeliverySlotTime.getText(), expectedText));
    }

    @Step("Проверяем, что стоимость выбранного слота содержит: {expectedText}")
    default void checkSelectedSlotCostContainsText(final String expectedText) {
        Assert.assertTrue(activeDeliverySlotCost.getText().toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Стоимость выбранного слота: '%s' не содержит '%s'", activeDeliverySlotCost.getText(), expectedText));
    }

    @Step("Проверяем, что метод оплаты не выбран")
    default void checkPaymentMethodEmpty() {
        Kraken.waitAction().shouldNotBeVisible(currentPaymentMethod);
    }

    @Step("Проверяем, что текущий выбранный способ оплаты содержит текст: '{expectedPaymentMethodText}'")
    default void checkSelectedPaymentMethodContains(final String expectedPaymentMethodText) {
        Assert.assertTrue(currentPaymentMethod.getText().contains(expectedPaymentMethodText),
                String.format("Текущий выбранный способ оплаты: '%s' не содержит '%s'", currentPaymentMethod.getText(), expectedPaymentMethodText));
    }

    @Step("Проверяем, что виджет оплаты со СберСпасибо содержит кол-во бонусов:{bonuses}")
    default void checkSberSpasiboWidgetContainsBonusesAmount(final String bonuses) {
        Assert.assertTrue(sberSpasiboWidget.getText().contains(" " + bonuses + " бонус"),
                String.format("Виджет СберСпасибо не содержит суммы списываемых бонусов: '%s'", bonuses));
    }

    @Step("Проверяем, что сайдбар содержит кол-во бонусов:{bonuses}")
    default void checkSidebarContainsBonusesAmount(final String bonuses) {
        Assert.assertTrue(sidebarSberSpasiboAmount.getText().contains("Бонусы от СберСпасибо -" + bonuses + " ₽"),
                String.format("Сайдбар не содержит суммы списываемых бонусов: '%s'", bonuses));
    }

    @Step("Проверяем, что виджет оплаты со СберСпасибо содержит  последние 4 символа карты: {cardLastDigits}")
    default void checkSberSpasiboWidgetContainsCardNumber(final String cardLastDigits) {
        Assert.assertTrue(sberSpasiboWidget.getText().contains(cardLastDigits),
                String.format("Виджет СберСпасибо не содержит последних 4 символов номера карты: '%s'", cardLastDigits));
    }

    @Step("Проверяем, что отображается кнопка 'Выбрать компанию'")
    default void checkSelectCompanyButtonDisplayed() {
        Kraken.waitAction().shouldBeVisible(selectCompany);
    }

    @Step("Проверяем сводную информацию о контактах")
    default void checkContactsSummary(final String expectedPhone, final String expectedEmail) {
        Assert.assertEquals(contactsSummary.getText(), convertDigitsStringToPhoneNumber(expectedPhone) + " • " + expectedEmail, "Сводная информация о контактах отличается от ожидаемой");
    }

    @Step("Проверяем, что в поле 'Телефон' указано: {expectedPhone}")
    default void checkContactsPhone(final String expectedPhone) {
        Assert.assertEquals(contactsPhone.getValue(), convertDigitsStringToPhoneNumberWithBracketsNoDash(expectedPhone), "Значение в поле 'Телефон' отличается от ожидаемого");
    }

    @Step("Проверяем, что в поле 'Email' указано: {expectedEmail}")
    default void checkContactsEmail(final String expectedEmail) {
        Assert.assertEquals(contactsEmail.getValue(), expectedEmail, "Значение в поле 'Email' отличается от ожидаемого");
    }

    @Step("Проверяем, что текущая выбранная политика замен (блок свернут): '{expectedReplacementPolicy}'")
    default void checkSelectedReplacementPolicyInCollapsedBlock(final String expectedReplacementPolicy) {
        Assert.assertTrue(replacementPolicySummary.getText().contains(expectedReplacementPolicy),
                String.format("Текущая выбранная политика замен: '%s' не содержит '%s'", replacementPolicySummary.getText(), expectedReplacementPolicy));    }

    @Step("Проверяем, что текущая выбранная политика замен: '{expectedReplacementPolicy}'")
    default void checkSelectedReplacementPolicy(final String expectedReplacementPolicy) {
        Assert.assertTrue(replacementPolicy.getText().contains(expectedReplacementPolicy),
                String.format("Текущая выбранная политика замен: '%s' не содержит '%s'", replacementPolicy.getText(), expectedReplacementPolicy));
    }

    @Step("Проверяем, что в 'Телефон' отображается ошибка")
    default void checkPhoneInvalid() {
        Kraken.waitAction().shouldBeVisible(contactsPhoneInvalid);
    }

    @Step("Проверяем, что отображается описание ошибки поля ввода 'Телефон'")
    default void checkPhoneErrorDescriptionVisible() {
        Kraken.waitAction().shouldBeVisible(contactsPhoneErrorDescription);
    }

    @Step("Проверяем, что описание ошибки поля ввода 'Телефон' содержит текст: '{expectedErrorText}'")
    default void checkPhoneErrorDescriptionContains(final String expectedErrorText) {
        Assert.assertTrue(contactsPhoneErrorDescription.getText().contains(expectedErrorText),
                String.format("Текущее описание ошибки поля ввода 'Телефон': '%s' не содержит '%s'", contactsPhoneErrorDescription.getText(), expectedErrorText));
    }

    @Step("Проверяем, что в 'Email' отображается ошибка")
    default void checkEmailInvalid() {
        Kraken.waitAction().shouldBeVisible(contactsPhoneInvalid);
    }

    @Step("Проверяем, что отображается описание ошибки поля ввода 'Email'")
    default void checkEmailErrorDescriptionVisible() {
        Kraken.waitAction().shouldBeVisible(contactsEmailErrorDescription);
    }

    @Step("Проверяем, что описание ошибки поля ввода 'Email' содержит текст: '{expectedErrorText}'")
    default void checkEmailErrorDescriptionContains(final String expectedErrorText) {
        Assert.assertTrue(contactsEmailErrorDescription.getText().contains(expectedErrorText),
                String.format("Текущее описание ошибки поля ввода 'Телефон': '%s' не содержит '%s'", contactsEmailErrorDescription.getText(), expectedErrorText));
    }

    @Step("Проверяем, что в поле политики замены товара отображается ошибка")
    default void checkSelectedReplacementPolicyInvalid() {
        Kraken.waitAction().shouldBeVisible(selectedReplacementPolicyInvalid);
    }

    @Step("Проверяем, что отображается описание ошибки выбора политики замены")
    default void checkReplacementPolicyErrorDescriptionVisible() {
        Kraken.waitAction().shouldBeVisible(replacementPolicyErrorDescription);
    }

    @Step("Проверяем, что описание ошибки выбора политики замен содержит текст: '{expectedReplacementPolicyErrorText}'")
    default void checkReplacementPolicyErrorDescriptionContains(final String expectedReplacementPolicyErrorText) {
        Assert.assertTrue(replacementPolicyErrorDescription.getText().contains(expectedReplacementPolicyErrorText),
                String.format("Текущее описание ошибки выбора политики замен: '%s' не содержит '%s'", replacementPolicyErrorDescription.getText(), expectedReplacementPolicyErrorText));
    }

    @Step("Проверяем, что отображается кнопка 'Применить' промокод")
    default void checkPromoApplyButtonVisible() {
        Kraken.waitAction().shouldBeVisible(promoCodeApply);
    }

    @Step("Проверяем, что отображается кнопка 'Отменить' промокод")
    default void checkPromoCancelButtonVisible() {
        Kraken.waitAction().shouldBeVisible(promoCodeCancel);
    }

    @Step("Проверяем, что отображается информация о примененном промокоде")
    default void checkPromoAppliedLabelVisible() {
        Kraken.waitAction().shouldBeVisible(promoAppliedLabel);
    }

    @Step("Проверяем, что стоимость доставки и сборки - бесплатная")
    default void checkDeliveryIsFree() {
        Assert.assertEquals(deliveryPrice.getText(), "бесплатно", "Доставка не бесплатная");
    }

    @Step("Проверяем, что стоимость доставки и сборки не бесплатная")
    default void checkDeliveryIsNotFree() {
        Assert.assertTrue(StringUtil.stringToDouble(deliveryPrice.getText()) > 0, "Доставка бесплатная");
    }

    @Step("Проверяем, что отображается сумма скидки по промокоду")
    default void checkPromoAppliedDiscountVisible() {
        Kraken.waitAction().shouldBeVisible(promoAppliedDiscount);
    }

    @Step("Проверяем, что отображается сообщение об ошибке поля ввода промокода")
    default void checkPromoCodeErrorVisible() {
        Kraken.waitAction().shouldBeVisible(promoCodeError);
    }

    @Step("Проверяем, что текст ошибки в поле ввода промокода: '{expectedErrorText}'")
    default void checkPromoCodeInputErrorContains(final String expectedErrorText) {
        Assert.assertEquals(promoCodeError.getText(), expectedErrorText, "Текст ошибки в поле ввода промокода отличается от ожидаемого");
    }

    @Step("Проверяем, что кнопка 'Заказать' отображается")
    default void checkConfirmOrderVisible() {
        Kraken.waitAction().shouldBeVisible(confirmOrder);
    }

    @Step("Проверяем, что кнопка 'Заказать' активна")
    default void checkConfirmOrderActive() {
        Kraken.waitAction().shouldNotBeVisible(confirmOrderDisabled);
    }

    @Step("Проверяем, что кнопка 'Оплатить' отображается")
    default void checkConfirmPayVisible() {
        Kraken.waitAction().shouldBeVisible(confirmPay);
    }

    @Step("Проверяем, что кнопка 'Оплатить' активна")
    default void checkConfirmPayActive() {
        Kraken.waitAction().shouldNotBeVisible(confirmPayDisabled);
    }

    @Step("Проверяем, что появилось всплывающее сообщение")
    default void checkNotificationVisible() {
        Kraken.waitAction().shouldBeVisible(notificationBanner);
    }

    @Step("Проверяем, что заголовок всплывающего сообщения об ошибке '{expectedErrorTitle}'")
    default void checkNotificationTitle(final String expectedErrorTitle) {
        Assert.assertEquals(notificationBannerTitle.getText(), expectedErrorTitle, "Заголовок всплывающего сообщения отличается от ожидаемого");
    }

    @Step("Проверяем, что текст всплывающего сообщения об ошибке '{expectedErrorText}'")
    default void checkNotificationText(final String expectedErrorText) {
        Assert.assertEquals(notificationBannerText.getText(), expectedErrorText, "Текст всплывающего сообщения отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается баннер о правилах продажи алкоголя")
    default void checkAlcoholBannerVisible() {
        Kraken.waitAction().shouldBeVisible(alcoholBanner);
    }

    @Step("Проверяем, что вкладка 'Доставка' недоступна")
    default void checkDeliveryTabNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(deliveryTab);
    }

    @Step("Проверяем, что отображается алерт невведенного номера квартиры/офиса")
    default void checkApartmentNumberAlertVisible() {
        apartmentAlert.should().visible();
    }

    @Step("Проверяем, что отображается алерт недоступности оплаты картой курьеру при бесконтактной доставке")
    default void checkCardToCourierBubbleVisible() {
        cardToCourierBubble.should().visible();
    }

    @Step("Проверяем, что кнопка перехода на Б2Б витрину не отображается")
    default void checkB2BTransitionButtonNotVisible() {
        b2bLink.should().invisible();
    }
}
