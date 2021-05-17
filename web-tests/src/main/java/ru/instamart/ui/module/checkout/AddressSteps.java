package ru.instamart.ui.module.checkout;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.ui.data.lib.CheckoutSteps;
import ru.instamart.ui.data.pagesdata.*;
import ru.instamart.ui.helper.PaymentHelper;
import ru.instamart.ui.module.Base;
import ru.instamart.ui.module.shop.ShippingAddressModal;
import ru.instamart.ui.Elements;
import ru.instamart.ui.helper.DeliveryHelper;

import static io.qameta.allure.Allure.step;
import static ru.instamart.core.testdata.TestVariables.testOrderDetails;

public final class AddressSteps extends Base {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressModal.class);
    public AddressSteps(final AppManager kraken) {
        super(kraken);
    }

    @Step("Заполняем адрес доставки дефолтными тестовыми значениями из конфига")
    public static void fill() {
        log.info("> заполняем адрес доставки дефолтными тестовыми значениями из конфига");
        fill(testOrderDetails().getAddressDetails());
    }

    public static void fill(AddressDetailsData addressDetails) {
        log.info("> заполняем адрес доставки");
        setType(addressDetails.getType());
        fillApartment(addressDetails.getApartment());
        fillFloor(addressDetails.getFloor());
        setElevator(addressDetails.isElevatorAvailable());
        fillEntrance(addressDetails.getEntrance());
        fillDomofon(addressDetails.getDomofon());
        fillCommentaries(addressDetails.getCommentaries());
    }

    @Step("Очищаем все доступные поля адреса доставки и возвращаем настройки в дефолтное состояние")
    public static void clear() {
        log.info("> очищаем все доступные поля адреса доставки и возвращаем настройки в дефолтное состояние");
        setTypeHome();
        fillApartment("");
        fillFloor("");
        setElevator(false);
        fillEntrance("");
        fillDomofon("");
        fillCommentaries("");
    }


    public static void next(){
        Checkout.hitNext(CheckoutSteps.addressStep());
    }

    public static void change(){
        Checkout.hitChange(CheckoutSteps.addressStep());
    }

    @Step("Сохранение шага")
    public static void save(){
        Checkout.hitSave(CheckoutSteps.addressStep());
    }

    @Step("Устанавливаем радиокнопку Тип дефолтным тестовым значением из конфига")
    public static void setType(){
        log.info("> устанавливаем радиокнопку Тип дефолтным тестовым значением из конфига");
        setType(testOrderDetails().getAddressDetails().getType());
    }

    public static void setType(String type){
        switch (type){
            case "home":
                setTypeHome();
                break;
            case "office":
                setTypeOffice();
                break;
            default:
                log.warn("> не удалось выбрать тип квартира / офис");
                break;
        }
    }

    @Step("Выбор доставки домой")
    public static void setTypeHome(){
        log.info("> тип: квартира");
        kraken.perform().click(Elements.Checkout.AddressStep.homeRadioButton());
    }

    @Step("Выбор доставки в офис")
    public static void setTypeOffice(){
        log.info("> тип: офис");
        kraken.perform().click(Elements.Checkout.AddressStep.officeRadioButton());
    }

    @Step("Заполняем поле Номер квартиры/офиса: {0}")
    public static void fillApartment(String number){
        if(kraken.detect().isFieldEmpty(Elements.Checkout.AddressStep.apartmentInputField())){
            log.info("> номер: {}", number);
            kraken.perform().fillFieldAction(Elements.Checkout.AddressStep.apartmentInputField(), number);
        }else{
            log.info("> поле номер квартиры/офиса уже заполнено: {}",
                    kraken.grab().value(Elements.Checkout.AddressStep.apartmentInputField()));
        }
    }

    @Step("Заполняем поле Этаж: {0}")
    public static void fillFloor(String number){
        if (kraken.detect().isFieldEmpty(Elements.Checkout.AddressStep.floorInputField())){
            log.info("> этаж: {}", number);
            kraken.perform().fillFieldAction(Elements.Checkout.AddressStep.floorInputField(), number);
        }else{
            log.info("> поле этаж уже заполнено: {}",
                    kraken.grab().value(Elements.Checkout.AddressStep.floorInputField()));
        }
    }

    @Step("Устанавливаем чекбокс лифт: {0}")
    public static void setElevator(boolean value){
        if (value) log.info("> лифт: есть");
        else log.info("> лифт: нет");
        kraken.perform().setCheckbox(Elements.Checkout.AddressStep.elevatorCheckbox(), value);
    }

    @Step("Заполняем поле Подъезд: {0}")
    public static void fillEntrance(String number){
        if (kraken.detect().isFieldEmpty(Elements.Checkout.AddressStep.entranceInputField())) {
            log.info("> подъезд: {}", number);
            kraken.perform().fillFieldAction(Elements.Checkout.AddressStep.entranceInputField(), number);
        }else {
            log.info("> поле этаж уже заполнено: {}",
                    kraken.grab().value(Elements.Checkout.AddressStep.entranceInputField()));
        }
    }

    @Step("Заполняем поле Домофон: {0}")
    public static void fillDomofon(String number){
        if (kraken.detect().isFieldEmpty(Elements.Checkout.AddressStep.domofonInputField())) {
            log.info("> домофон: {}", number);
            kraken.perform().fillFieldAction(Elements.Checkout.AddressStep.domofonInputField(), number);
        }else {
            log.info("> поле домофон уже заполнено: {}",
                    kraken.grab().value(Elements.Checkout.AddressStep.domofonInputField()));
        }
    }

    @Step("Заполняем поле Комментарии: {0}")
    public static void fillCommentaries(String text){
        if (kraken.detect().isFieldEmpty(Elements.Checkout.AddressStep.commentariesInputField())) {
            log.info("> комментарии по доставке: {}", text);
            kraken.perform().fillFieldAction(Elements.Checkout.AddressStep.commentariesInputField(), text);
        }else {
            log.info("> поле комментарий уже заполнено: {}",
                    kraken.grab().value(Elements.Checkout.AddressStep.commentariesInputField()));
        }
    }

    public static void fillStep(int position, OrderDetailsData orderDetails) {
        CheckoutStepData step = CheckoutSteps.getStepDetails(position);
        assert step != null;
        if (initStep(step)) {
            log.info("> шаг {} - {}", step.getPosition(), step.getName());
        } else {
            hitChangeButton(step.getPosition());
        }
        switch (step.getName()) {
            case "Адрес" :
                step("Заполнение адреса", ()-> {
                    fill(orderDetails.getAddressDetails());
                    next();
                });
                break;
            case "Контакты" :
                step("Заполнение контактов", ()->{
                    ContactsSteps.fill(orderDetails.getContactsDetails());
                    ContactsSteps.next();
                });
                break;
            case "Замены" :
                step("Выбора действия для замены", ()->{
                    DeliveryHelper.choosePolicy(orderDetails.getReplacementPolicy());
                    Checkout.hitNext(CheckoutSteps.replacementsStep());
                });
                break;
            case "Доставка" :
                step("Выбор слота доставки", ()-> DeliveryHelper.chooseDeliveryTime(orderDetails.getDeliveryTime()));
                break;
            case "Оплата" :
                PaymentHelper.choosePaymentMethod(orderDetails.getPaymentDetails());
                break;
        }
    }

    @Step("Нажимаем кнопки \"Изменить\" в шагах чекаута")
    private static void hitChangeButton(int step) {
        switch (step) {
            case 1 :
            case 2 :
            case 3 :
            case 4 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 5 :
                kraken.perform().click(Elements.Checkout.changeStep5Button());
                break;
        }
        log.info("> изменяем шаг {}", step);
        kraken.await().implicitly(1); // Ожидание разворачивания шага в чекауте
    }

    @Step("Проверяем готовность шага чекаута перед заполнением")
    private static boolean initStep(CheckoutStepData step) {
        log.info("> проверка готовности шага чекаута перед заполнением");
        if (step.getPosition() != 4) { // костыль на случай если слот доставки остался выбраным в предыдущих тестах
            if (kraken.detect().isCheckoutStepActive(step)) {
                //message("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                kraken.await().implicitly(1); // Задержка для стабильности, если шаг не развернулся из-за тормозов
                if (!kraken.detect().isCheckoutStepActive(step)) {
                    log.info("> не открывается {} шаг - {}", step.getPosition(), step.getName());
                    return false;
                } else return true;
            }
        } else {
            if (kraken.detect().isElementDisplayed(Elements.Checkout.Step.panel(CheckoutSteps.deliveryStep()))) {
                //message("Шаг " + step.getPosition() + " - " + step.getName());
                return true;
            } else {
                log.info("> шаг {} - {}", step.getPosition(), step.getName());
                log.info("> слот доставки уже выбран");
                return false;
            }
        }
    }
}
