package instamart.ui.modules;

import instamart.core.common.AppManager;
import instamart.core.helpers.HelperBase;
import instamart.core.testdata.ui.PaymentTypes;
import instamart.core.testdata.ui.BonusPrograms;
import instamart.ui.common.lib.CheckoutSteps;
import instamart.ui.common.lib.ReplacementPolicies;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.concurrent.TimeUnit;

import static instamart.core.settings.Config.CoreSettings.basicTimeout;
import static instamart.core.settings.Config.CoreSettings.waitingTimeout;
import static instamart.core.settings.Config.TestVariables.testOrderDetails;

public class Checkout extends Base {

    public Checkout(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public static void hitNext(CheckoutStepData step){
        HelperBase.verboseMessage("Жмем 'Продолжить' в шаге '" + step.getName() + "'\n");
        kraken.perform().click(Elements.Checkout.Step.nextButton(step));
        kraken.await().simply(1); // Ожидание сохранения данных в шаге чекаута после нажатия "Продолжить"
    }

    public static void hitChange(CheckoutStepData step){
        HelperBase.verboseMessage("Жмем 'Изменить' в шаге '" + step.getName() + "'\n");
        kraken.perform().click(Elements.Checkout.MinimizedStep.changeButton(step));
        kraken.await().simply(1); // Ожидание разворота шага чекаута после нажатия "Изменить"
    }

    public static void hitSave(CheckoutStepData step){
        HelperBase.verboseMessage("Жмем 'Сохранить' в шаге '" + step.getName() + "'\n");
        kraken.perform().click(Elements.Checkout.Step.saveButton(step));
        kraken.await().simply(1); // Ожидание сохранения данных в шаге чекаута после нажатия "Сохранить"
    }

    public static void sendOrderFromSidebar() {
        HelperBase.verboseMessage("Отправляем заказ...");
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Checkout.SideBar.sendOrderButton().getLocator()),
                            "Неактивна кнопка отправки заказа\n");
        kraken.perform().click(Elements.Checkout.SideBar.sendOrderButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Checkout.header().getLocator()),
                            "Превышено время ожидания отправки заказа\n");
        HelperBase.verboseMessage("✓ Заказ оформлен\n");
    }

    public static void sendOrderFromBottomPanel() {
        HelperBase.verboseMessage("Отправляем заказ ...");
        kraken.await().fluently(
                ExpectedConditions.elementToBeClickable(
                        Elements.Checkout.sendOrderButton().getLocator()),
                "Неактивна кнопка отправки заказа\n");
        kraken.perform().click(Elements.Checkout.sendOrderButton());
        kraken.await().fluently(
                ExpectedConditions.invisibilityOfElementLocated(
                        Elements.Checkout.header().getLocator()),
                "Превышено время отправки заказа\n");
        HelperBase.verboseMessage("✓ Заказ оформлен\n");
    }

    public static class AddressStep {

        public static void fill() {
            HelperBase.verboseMessage("\nЗаполняем адрес доставки дефолтными тестовыми значениями из конфига");
            fill(testOrderDetails().getAddressDetails());
        }

        public static void fill(AddressDetailsData addressDetails) {
            HelperBase.verboseMessage("Заполняем адрес доставки");
            setType(addressDetails.getType());
            fillApartment(addressDetails.getApartment());
            fillFloor(addressDetails.getFloor());
            setElevator(addressDetails.isElevatorAvailable());
            fillEntrance(addressDetails.getEntrance());
            fillDomofon(addressDetails.getDomofon());
            fillCommentaries(addressDetails.getCommentaries());
        }

        public static void clear() {
            HelperBase.verboseMessage("\nОчищаем все доступные поля адреса доставки и возвращаем настройки в дефолтное состояние");
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

        public static void save(){
            Checkout.hitSave(CheckoutSteps.addressStep());
        }

        public static void setType(){
            HelperBase.verboseMessage("Устанавливаем радиокнопку Тип дефолтным тестовым значением из конфига");
            setType(testOrderDetails().getAddressDetails().getType());
        }

        public static void setType(String type){
            switch (type){
                case "home": setTypeHome(); break;
                case "office": setTypeOffice(); break;
                default: HelperBase.verboseMessage("> не удалось выбрать тип квартира / офис"); break;
            }
        }

        public static void setTypeHome(){
            HelperBase.verboseMessage("> тип: квартира");
            kraken.perform().click(Elements.Checkout.AddressStep.homeRadioButton());
        }

        public static void setTypeOffice(){
            HelperBase.verboseMessage("> тип: офис");
            kraken.perform().click(Elements.Checkout.AddressStep.officeRadioButton());
        }

        public static void fillApartment(){
            HelperBase.verboseMessage("Заполняем поле Номер квартиры/офиса дефолтным тестовым значением из конфига");
            fillApartment(testOrderDetails().getAddressDetails().getApartment());
        }

        public static void fillApartment(String number){
            HelperBase.verboseMessage("> номер: " + number);
            kraken.perform().fillField(Elements.Checkout.AddressStep.apartmentInputField(), number);
        }

        public static void fillFloor(){
            HelperBase.verboseMessage("Заполняем поле Этаж дефолтным тестовым значением из конфига");
            fillFloor(testOrderDetails().getAddressDetails().getFloor());
        }

        public static void fillFloor(String number){
            HelperBase.verboseMessage("> этаж: " + number);
            kraken.perform().fillField(Elements.Checkout.AddressStep.floorInputField(), number);
        }

        public static void setElevator(){
            HelperBase.verboseMessage("Устанавливаем чекбокс Есть лифт дефолтным тестовым значением из конфига");
            setElevator(testOrderDetails().getAddressDetails().isElevatorAvailable());
        }

        public static void setElevator(boolean value){
            if (value) HelperBase.verboseMessage("> лифт: есть");
            else HelperBase.verboseMessage("> лифт: нет");
            kraken.perform().setCheckbox(Elements.Checkout.AddressStep.elevatorCheckbox(), value);
        }

        public static void fillEntrance(){
            HelperBase.verboseMessage("Заполняем поле Подъезд дефолтным тестовым значением из конфига");
            fillEntrance(testOrderDetails().getAddressDetails().getEntrance());
        }

        public static void fillEntrance(String number){
            HelperBase.verboseMessage("> подъезд: " + number);
            kraken.perform().fillField(Elements.Checkout.AddressStep.entranceInputField(), number);
        }

        public static void fillDomofon(){
            HelperBase.verboseMessage("Заполняем поле Домофон дефолтным тестовым значением из конфига");
            fillDomofon(testOrderDetails().getAddressDetails().getDomofon());
        }

        public static void fillDomofon(String number){
            HelperBase.verboseMessage("> домофон: " + number);
            kraken.perform().fillField(Elements.Checkout.AddressStep.domofonInputField(), number);
        }

        public static void fillCommentaries(){
            HelperBase.verboseMessage("Заполняем поле Комментарии дефолтным тестовым значением из конфига");
            fillCommentaries(testOrderDetails().getAddressDetails().getCommentaries());
        }

        public static void fillCommentaries(String text){
            HelperBase.verboseMessage("> комментарии по доставке: " + text);
            kraken.perform().fillField(Elements.Checkout.AddressStep.commentariesInputField(), text);
        }
    }

    public static class ContactsStep {

        public static void fill() {
            HelperBase.verboseMessage("\nЗаполняем контакты дефолтными тестовыми значениями из конфига");
            fill(testOrderDetails().getContactsDetails());
        }

        public static void fill(ContactsDetailsData contactsDetails) {

            // todo детектить пустые поля

            if(contactsDetails.changeFirstName()) {
                fillFirstName(contactsDetails.getFirstName());
            }

            if(contactsDetails.changeLastName()) {
                fillLastName(contactsDetails.getLastName());
            }

            if(contactsDetails.changeEmail()) {
                fillEmail(contactsDetails.getEmail());
            }

            if(kraken.detect().isElementPresent(Elements.Checkout.ContactsStep.phoneInputField())) {
                fillPhone(contactsDetails.getPhone());
            } else if(contactsDetails.addNewPhone()) {
                Phones.addNewPhone(contactsDetails.getPhone());
            }

            setSendEmails(contactsDetails.sendEmails());
        }

        public static void clear() {
            HelperBase.verboseMessage("\nОчищаем все доступные поля контактов, возвращаем настройки в дефолтное состояние и удаляем все добавленные телефоны");
            fillFirstName("");
            fillLastName("");
            fillEmail("");
            Phones.deleteAll();
            setSendEmails(false);
        }

        public static void next() {
            Checkout.hitNext(CheckoutSteps.contactsStep());
        }

        public static void change() {
            Checkout.hitChange(CheckoutSteps.contactsStep());
        }

        public static void save() {
            Checkout.hitSave(CheckoutSteps.contactsStep());
        }

        public static void fillFirstName(String firstName) {
            HelperBase.verboseMessage("> имя: " + firstName);
            kraken.perform().fillField(Elements.Checkout.ContactsStep.firstNameInputField(), firstName);
        }

        public static void fillLastName(String lastName) {
            HelperBase.verboseMessage("> фамилия: " + lastName);
            kraken.perform().fillField(Elements.Checkout.ContactsStep.firstNameInputField(), lastName);
        }

        public static void fillEmail(String email) {
            HelperBase.verboseMessage("> email: " + email);
            kraken.perform().fillField(Elements.Checkout.ContactsStep.emailInputField(), email);
        }

        public static void fillPhone(String number) {
            HelperBase.verboseMessage("> телефон: " + number);
            kraken.perform().fillField(Elements.Checkout.ContactsStep.phoneInputField(), number);
        }

        public static class Phones {

            //Todo public static class Modal > fill > submit > cancel > close

            public static class TopEntry {

                public static void edit(String newNumber) {
                    HelperBase.verboseMessage("Изменяем верхний добавленный номер телефона на " + newNumber);
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.TopEntry.changeButton());
                    kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),newNumber);
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
                }

                public static void delete() {
                    HelperBase.verboseMessage("Удаляем верхний добавленный номер телефона");
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.TopEntry.changeButton());
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
                }
            }

            public static class ActiveEntry {

                public static void edit(String newNumber) {
                    HelperBase.verboseMessage("Изменяем активный добавленный номер телефона на " + newNumber);
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.ActiveEntry.changeButton());
                    kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),newNumber);
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
                }

                public static void delete() {
                    HelperBase.verboseMessage("Удаляем активный добавленный номер телефона");
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.ActiveEntry.changeButton());
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
                }
            }

            public static class NotActiveEntry {

                public static void edit(String newNumber) {
                    HelperBase.verboseMessage("Изменяем неактивный добавленный номер телефона на " + newNumber);
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.NotActiveEntry.changeButton());
                    kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),newNumber);
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
                }

                public static void delete() {
                    HelperBase.verboseMessage("Удаляем неактивный добавленный номер телефона");
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.NotActiveEntry.changeButton());
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
                }
            }

            public static void addNewPhone(String number) {
                HelperBase.verboseMessage("Добавляем новый номер телефона: " + number);
                kraken.perform().click(Elements.Checkout.ContactsStep.Phones.addNewPhoneButton());
                kraken.perform().fillField(Elements.Checkout.ContactsStep.Phones.Modal.inputField(),number);
                kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.submitButton());
            }

            public static void deleteAll() {
                HelperBase.verboseMessage("Удаляем все добавленные номера телефонов");
                if(kraken.detect().isElementPresent(Elements.Checkout.ContactsStep.phoneInputField())) {
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.TopEntry.changeButton());
                    kraken.perform().click(Elements.Checkout.ContactsStep.Phones.Modal.deleteButton());
                    deleteAll();
                } else {

                }
            }
        }

        public static void setSendEmails(boolean value) {
            if(value) HelperBase.verboseMessage("> отправлять письма с апдейтами по заказу: да");
            else HelperBase.verboseMessage("> отправлять письма с апдейтами по заказу: нет");
            kraken.perform().setCheckbox(Elements.Checkout.ContactsStep.sendEmailsCheckbox(), value);
        }
    }


    public static class ReplacementsStep {

        public static void choose(ReplacementPolicyData policy) {
            choosePolicy(policy);
        }

        public static void choose(int policyPosition) {
            //todo
        }

        public static void clear() {
            HelperBase.verboseMessage("\nВозвращаем настройки в дефолтное состояние");
            choosePolicy(ReplacementPolicies.callAndReplace());
        }

        public static void next() {
            Checkout.hitNext(CheckoutSteps.replacementsStep());
        }

        public static void change() {
            Checkout.hitChange(CheckoutSteps.replacementsStep());
        }

        public static void save() {
            Checkout.hitSave(CheckoutSteps.replacementsStep());
        }
    }

    public static class PaymentStep {

        public static void next() {
            Checkout.hitNext(CheckoutSteps.paymentStep());
        }

        public static void change() {
            Checkout.hitChange(CheckoutSteps.paymentStep());
        }

        public static void save() {
            Checkout.hitSave(CheckoutSteps.paymentStep());
        }
    }

    public static class DeliveryStep {

        public static void next() {
            Checkout.hitNext(CheckoutSteps.deliveryStep());
        }

        public static void change() {
            Checkout.hitChange(CheckoutSteps.deliveryStep());
        }

        public static void save() {
            Checkout.hitSave(CheckoutSteps.deliveryStep());
        }
    }














    public void complete() {
        makeOrder(testOrderDetails());
    }

    public void complete(PaymentTypeData payment) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment);
        makeOrder(details);
    }

    public void complete(ReplacementPolicyData policy) {
        OrderDetailsData details = new OrderDetailsData();
        details.setReplacementPolicy(policy);
        makeOrder(details);
    }

    public void complete(boolean newPhone, String phone) {
        OrderDetailsData details = new OrderDetailsData();
        details.setContactsDetails(new ContactsDetailsData(newPhone, phone));
        makeOrder(details);
    }

    public void complete(PaymentTypeData payment, boolean newPaymentCard, PaymentCardData card) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newPaymentCard, card);
        makeOrder(details);
    }

    public void complete(PaymentTypeData payment, boolean newJuridical, JuridicalData juridical) {
        OrderDetailsData details = new OrderDetailsData();
        details.setPaymentDetails(payment, newJuridical, juridical);
        makeOrder(details);
    }

    public void makeOrder(OrderDetailsData orderDetails) {
        fillOrderDetails(orderDetails);
        if(orderDetails.getPromocode() != null) {Promocode.add(orderDetails.getPromocode());}
        if(orderDetails.getBonus() != null) {
            Checkout.Bonuses.add(orderDetails.getBonus());}
        if(orderDetails.getRetailerCard() != null) {RetailerCards.addCard(orderDetails.getRetailerCard());}
        sendOrderFromSidebar();
        if(orderDetails.getPaymentDetails().getPaymentType().getDescription().equalsIgnoreCase(PaymentTypes.cardOnline().getDescription())) {
            cloudpaymentsFlow();
        }
    }

    public void fillOrderDetails() {
        fillOrderDetails(testOrderDetails());
    }

    public void fillOrderDetails(OrderDetailsData orderDetails) {
        initCheckout();
        for (int position = 1; position <= 5; position++) {
            fillStep(position,orderDetails);
        }
    }

    private void fillStep(int position, OrderDetailsData orderDetails) {
        CheckoutStepData step = CheckoutSteps.getStepDetails(position);
        assert step != null;
        if (initStep(step)) {
            HelperBase.verboseMessage("Шаг " + step.getPosition() + " - " + step.getName());
        } else {
            hitChangeButton(step.getPosition());
        }
        switch (step.getName()) {
            case "Адрес" :
                AddressStep.fill(orderDetails.getAddressDetails());
                AddressStep.next();
                break;
            case "Контакты" :
                ContactsStep.fill(orderDetails.getContactsDetails());
                ContactsStep.next();
                break;
            case "Замены" :
                choosePolicy(orderDetails.getReplacementPolicy());
                ReplacementsStep.next();
                break;
            case "Оплата" :
                choosePaymentMethod(orderDetails.getPaymentDetails());
                PaymentStep.next();
                break;
            case "Доставка" :
                chooseDeliveryTime(orderDetails.getDeliveryTime());
                break;
        }
    }

    public static void choosePolicy(ReplacementPolicyData policy) {
        kraken.perform().click(Elements.Checkout.replacementPolicy(policy.getPosition()));
        HelperBase.verboseMessage("Выбираем способ замен #" + policy.getPosition() + " (" + policy.getUserDescription() + ")");
    }

    public void choosePaymentMethod (PaymentDetailsData paymentDetails) {

        String description = paymentDetails.getPaymentType().getDescription();
        if(kraken.detect().isPaymentTypeAvailable(description)){
            HelperBase.verboseMessage("Выбираем оплату " + description);
            kraken.perform().click(Elements.Checkout.paymentTypeSelector(description));
        } else throw new AssertionError("В чекауте недоступна оплата " + description);

        if (description.equalsIgnoreCase(PaymentTypes.cardOnline().getDescription())) {
            if (paymentDetails.isNewCreditCard()) {
                deleteAllExceptOnePaymentCard();
                if (paymentDetails.getCreditCard() != null) {
                    addNewPaymentCard(paymentDetails.getCreditCard());
                } else {
                    addNewPaymentCard(testOrderDetails().getPaymentDetails().getCreditCard());
                }
            } else {
                if (paymentDetails.getCreditCard() != null) {
                    selectPaymentCard(paymentDetails.getCreditCard());
                }
            }
        }

        if (description.equalsIgnoreCase(PaymentTypes.bankTransfer().getDescription())) {
            if (paymentDetails.isNewJuridical()) {
                deleteAllExceptOneJuridical();
                if (paymentDetails.getJuridical() != null) {
                    addNewJuridical(paymentDetails.getJuridical());
                } else {
                    addNewJuridical(testOrderDetails().getPaymentDetails().getJuridical());
                }
            } else {
                if (paymentDetails.getJuridical() != null) {
                selectJuridical(paymentDetails.getJuridical());
                }
            }
        }
    }

    public void chooseDeliveryTime(DeliveryTimeData deliveryTime) {
        chooseDeliveryTime(deliveryTime.getDay(),deliveryTime.getSlot());
    }

    public void chooseDeliveryTime(int day, int slot) {
        HelperBase.verboseMessage("Переключаемся на " + day + " день");
        kraken.perform().click(Elements.Checkout.deliveryDaySelector(day));
        kraken.await().implicitly(1); // Ожидание загрузки слотов дня в чекауте
        if (kraken.detect().isElementPresent(Elements.Checkout.deliveryWindowsPlaceholder())){
            throw new AssertionError("Нет доступных слотов доставки");
        }
        if(slot != 0) {
        HelperBase.verboseMessage("Выбираем " + slot + " слот ("
                + kraken.grab().text(Elements.Checkout.slotTime(day, slot))
                + " / "
                + kraken.grab().text(Elements.Checkout.slotPrice(day, slot))
                + ")\n");
        kraken.perform().click(Elements.Checkout.chooseSlotButton(day, slot));
        } else {
            HelperBase.verboseMessage("Выбираем первый доступный интервал доставки\n");
            kraken.perform().click(Elements.Checkout.chooseSlotButton());
        }
        // TODO заменить на fluent-ожидание исчезновения спиннера + 1 implicity
        kraken.await().implicitly(3); // Ожидание применения слота доставки в чекауте
    }

    /** Промокод */
    public static class Promocode {

        /** Применить промокод по акции */
        public static void add(PromoData promo) {
            HelperBase.verboseMessage("Акция " + promo.getDescription());
            add(promo.getCode());
        }

        /** Применить промокод */
        public static void add(String promocode) {
            if (kraken.detect().isPromocodeApplied()) {
                HelperBase.verboseMessage("Уже есть применённый промокод, поэтому сначала удаляем его... ");
                delete();
            }
            HelperBase.verboseMessage("Применяем промокод '" + promocode + "'...");
            Modal.open();
            Modal.fill(promocode);
            Modal.submit();
            kraken.await().implicitly(1); // Ожидание применения промокода в чекауте
            // TODO добавить fluent-ожидание
        }

        /** Удалить промокод */
        public static void delete() throws AssertionError {
            if (kraken.detect().isPromocodeApplied()) {
                HelperBase.verboseMessage("Удаляем промокод...");
                kraken.perform().click(Elements.Checkout.Promocode.deleteButton());
                kraken.await().implicitly(1); // Ожидание удаления промокода в чекауте
                // TODO добавить fluent-ожидание
            } else {
                throw new AssertionError("Невозможно удалить промокод, так как он не применен");
            }
        }

        /** Операции c модалкой промокода */
        public static class Modal {

            /** Открыть модалку */
            public static void open() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.addButton())) {
                    kraken.perform().click(Elements.Checkout.Promocode.addButton());
                } else {
                    throw new AssertionError("Невозможно открыть модалку ввода промокода, так как в данный момент применен промокод");
                }
            }

            /** Закрыть модалку */
            public static void fill(String promocode) {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().fillField(Elements.Checkout.Promocode.Modal.inputField(), promocode);
                } else {
                    throw new AssertionError("Невозможно ввести промокод, так как не открыта модалка промокода");
                }
            }

            /** Засубмитить модалку кнопкой "Добавить код" */
            public static void submit() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Promocode.Modal.submitButton());
                } else {
                    throw new AssertionError("Невозможно нажать кнопку \"Отмена\", так как не открыта модалка промокода");
                }
            }

            /** Закрыть модалку кнопкой "Отмена" */
            public static void cancel() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Promocode.Modal.cancelButton());
                } else {
                    throw new AssertionError("Невозможно нажать кнопку \"Отмена\", так как не открыта модалка промокода");
                }
            }

            /** Закрыть модалку */
            public static void close() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Promocode.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Promocode.Modal.closeButton());
                } else {
                    HelperBase.verboseMessage("Пропускаем закрытие модалки промокода, так как она не открыта");
                }
            }
        }
    }

    /** Бонусные программы */
    public static class Bonuses {

        /** Добавление бонусной программы */
        public static void add(LoyaltiesData bonus) {
            HelperBase.verboseMessage("Добавляем бонусную программу " + bonus.getName());
            kraken.perform().click(Elements.Checkout.Bonuses.Program.addButton(bonus.getName()));
            kraken.perform().fillField(Elements.Checkout.Bonuses.Modal.inputField(), bonus.getCardNumber());
            kraken.perform().click(Elements.Checkout.Bonuses.Modal.saveButton());
            kraken.await().implicitly(1); // Ожидание добавления бонусной программы в чекауте
            // TODO добавить fluent-ожидание
        }

        /** Редактирование бонусной программы */
        public static void edit(LoyaltiesData bonus) throws AssertionError {
            if (kraken.detect().isBonusAdded(bonus)) {
                HelperBase.verboseMessage("Редактируем бонусную программу " + bonus.getName());
                kraken.perform().click(Elements.Checkout.Bonuses.Program.addButton(bonus.getName()));
                kraken.perform().fillField(Elements.Checkout.Bonuses.Modal.inputField(), bonus.getCardNumber());
                kraken.perform().click(Elements.Checkout.Bonuses.Modal.saveButton());
                kraken.await().implicitly(1); // Ожидание редактирования бонусной программы в чекауте
                // TODO добавить fluent-ожидание
            } else {
                throw new AssertionError("Невозможно отредактировать бонусную программу " + bonus.getName() + ", так как она не добавлена");
            }
        }

        /** Выбор бонусной программы в списке добавленных */
        public static void select(LoyaltiesData bonus) throws AssertionError {
            if (kraken.detect().isBonusAdded(bonus)) {
                HelperBase.verboseMessage("Выбираем бонусную программу " + bonus.getName());
                kraken.perform().click(Elements.Checkout.Bonuses.Program.snippet(bonus.getName()));
                kraken.await().simply(1); // Ожидание выбора бонусной программы в чекауте
            } else {
                throw new AssertionError("Невозможно выбрать бонусную программу " + bonus.getName() + ", так как она не добавлена");
            }
        }

        /** Удаление бонусной программы */
        public static void delete(LoyaltiesData bonus) throws AssertionError {
            if (kraken.detect().isBonusAdded(bonus)) {
                HelperBase.verboseMessage("Удаляем бонусную программу " + bonus.getName());
                kraken.perform().click(Elements.Checkout.Bonuses.Program.editButton(bonus.getName()));
                kraken.perform().click(Elements.Checkout.Bonuses.Modal.deleteButton());
                kraken.await().implicitly(1); // Ожидание удаления программы лояльности в чекауте
                // TODO добавить fluent-ожидание
            } else {
                throw new AssertionError("Невозможно удалить бонусную программу " + bonus.getName() + ", так как она не добавлена");
            }
        }

        /** Удаление всех добавленных бонусных программ */
        public static void deleteAll() {
            HelperBase.verboseMessage("Удаляем все бонусные программы в чекауте\n");
            if (kraken.detect().isBonusAdded(BonusPrograms.mnogoru())) {
                delete(BonusPrograms.mnogoru());
            }
            if (kraken.detect().isBonusAdded(BonusPrograms.aeroflot())) {
                delete(BonusPrograms.aeroflot());
            }
        }

        /** Операции c модалкой бонусных мрограмм */
        public static class Modal {

            //TODO public void open() {bonus}

            //TODO public void fill() {}

            //TODO public void submit() {}

            //TODO public void cancel() {}

            /** Закрыть модалку */
            public void close() {
                if (kraken.detect().isElementPresent(Elements.Checkout.Bonuses.Modal.popup())) {
                    kraken.perform().click(Elements.Checkout.Bonuses.Modal.closeButton());
                } else {
                    HelperBase.verboseMessage("Пропускаем закрытие бонусной модалки, так как она не открыта");
                }
            }
        }
    }

    /** Карты ритейлера */
    public static class RetailerCards {

        /** Добавляем карту ритейлера */
        public static void addCard(LoyaltiesData card) {
            if (kraken.detect().isRetailerCardAdded()) {
                deleteCard();
            }
            HelperBase.verboseMessage("Добавляем карту ритейлера \"" + card.getName() + "\"");
            kraken.perform().click(Elements.Checkout.RetailerCard.input());
            kraken.perform().fillField(Elements.Checkout.RetailerCard.Modal.inputField(),card.getCardNumber() + "\uE007");
            kraken.perform().click(Elements.Checkout.RetailerCard.Modal.saveButton());
            kraken.await().implicitly(1); // Ожидание добавления карты ритейлера в чекауте
        }

        /** Удаляем карту ритейлера */
        public static void deleteCard() {
            HelperBase.verboseMessage("Удаляем карту ритейлера");
            kraken.perform().click(Elements.Checkout.RetailerCard.input());
            kraken.perform().click(Elements.Checkout.RetailerCard.Modal.deleteButton());
            kraken.await().implicitly(1); // Ожидание удаления карты ритейлера в чекауте
        }
    }

    /** Проверяем готовность чекаута перед заполнением */
    private void initCheckout() {
        new FluentWait<>(driver)
                .withTimeout(waitingTimeout, TimeUnit.SECONDS)
                .withMessage("Не открывается чекаут")
                .pollingEvery(basicTimeout, TimeUnit.SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(Elements.Checkout.header().getLocator()));
        HelperBase.verboseMessage("✓ Чекаут\n");
    }

    /** Проверяем готовность шага чекаута перед заполнением */
    private boolean initStep(CheckoutStepData step) {
        if (step.getPosition() != 5) { // костыль на случай если слот доставки остался выбраным в предыдущих тестах
            if (kraken.detect().isCheckoutStepActive(step)) {
                //message("Шаг " + stepNumber + " - " + stepName);
                return true;
            } else {
                kraken.await().implicitly(1); // Задержка для стабильности, если шаг не развернулся из-за тормозов
                if (!kraken.detect().isCheckoutStepActive(step)) {
                    HelperBase.verboseMessage("Не открывается " + step.getPosition() + " шаг - " + step.getName());
                    return false;
                } else return true;
            }
        } else {
            if (kraken.detect().isElementDisplayed(Elements.Checkout.Step.panel(CheckoutSteps.deliveryStep()))) {
                //message("Шаг " + step.getPosition() + " - " + step.getName());
                return true;
            } else {
                HelperBase.verboseMessage("Шаг " + step.getPosition() + " - " + step.getName());
                HelperBase.verboseMessage("Слот доставки уже выбран\n");
                return false;
            }
        }
    }

    /** Нажимаем кнопки "Изменить" в шагах чекаута */
    private void hitChangeButton(int step) {
        switch (step) {
            case 1 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 2 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 3 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 4 :
                kraken.perform().click(Elements.Checkout.changeStepButton(step));
                break;
            case 5 :
                kraken.perform().click(Elements.Checkout.changeStep5Button());
                break;
        }
        HelperBase.verboseMessage("Изменяем шаг " + step + "\n");
        kraken.await().implicitly(1); // Ожидание разворачивания шага в чекауте
    }

    /** Удалить все номера телефонов */
    private void deletePhoneNumbers() {
        if (kraken.detect().isNoPhonesAddedOnContactsStep()) {
            kraken.perform().click(Elements.Checkout.editPhoneButton());
            kraken.perform().click(Elements.Checkout.deletePhoneButton());
            HelperBase.verboseMessage("Удоляем номер телефона " + kraken.grab().text(Elements.Checkout.phoneNumber()));
            kraken.await().implicitly(1); // ожидание удаления предыдущего номера телефона
            deletePhoneNumbers();
        }
    }

    /** Добавить новую карту оплаты */
    private void addNewPaymentCard(PaymentCardData creditCardData) {
        HelperBase.verboseMessage("Добавляем карту оплаты " + creditCardData.getCardNumber());

        if (kraken.detect().isElementDisplayed(Elements.Checkout.addPaymentCardButton())) {
            kraken.perform().click(Elements.Checkout.addPaymentCardButton());
            fillPaymentCardDetails(creditCardData);
            kraken.perform().click(Elements.Checkout.PaymentCardModal.confirmButton());
            kraken.await().implicitly(1); // Ожидание добавления новой карты оплаты

            kraken.perform().click(Elements.Checkout.paymentCardTitle(
                    kraken.grab().listSize(Elements.Checkout.paymentCardsList())));
            kraken.await().implicitly(1); // Ожидание применения новой карты оплаты
        } else {
            fillPaymentCardDetails(creditCardData);
        }
    }

    /** Заполнить данные карты оплаты */
    private void fillPaymentCardDetails(PaymentCardData creditCardData) {
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.cardNumberField(), creditCardData.getCardNumber());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.monthField(), creditCardData.getExpiryMonth());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.yearField(), creditCardData.getExpiryYear());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.cvvField(), creditCardData.getCvvNumber());
        kraken.perform().fillField(Elements.Checkout.PaymentCardModal.nameField(), creditCardData.getCardholderName());
    }

    /** Удалить все карты оплаты, кроме одной */
    private void deleteAllExceptOnePaymentCard() {
        if (kraken.detect().isSecondPaymentCardEntered()) {
            deletePaymentCard();
            deleteAllExceptOnePaymentCard();
        }
    }

    /** Удалить карту оплаты */
    private void deletePaymentCard() {
        kraken.perform().click(Elements.Checkout.changePaymentCardButton());
        HelperBase.verboseMessage(
                "Удаляем карту оплаты •••• " + kraken.grab().text(Elements.Checkout.PaymentCardModal.cardNumber()));
        kraken.perform().click(Elements.Checkout.PaymentCardModal.deleteButton());
        kraken.await().implicitly(1); // Ожидание удаления карты оплаты
    }

    /** Выбрать карту оплаты */
    private void selectPaymentCard(PaymentCardData creditCardData) {
        ElementData title = Elements.Checkout.paymentCardTitle(creditCardData);
        if (kraken.detect().isElementDisplayed(title)) {
            HelperBase.verboseMessage("Выбираем карту оплаты " + kraken.grab().text(title));
            kraken.perform().click(title);
            kraken.await().implicitly(1); // Ожидание применения выбранной карты оплаты
        } else {
            addNewPaymentCard(creditCardData);
        }
    }

    /** Вести 3ds код на странице cloudpayments */
    private void cloudpaymentsFlow() {
        if (kraken.detect().isElementDisplayed(Elements.Checkout.Cloudpayments.answerField())) {
            kraken.perform().fillField(Elements.Checkout.Cloudpayments.answerField(), "4");
            kraken.perform().click(Elements.Checkout.Cloudpayments.confirmButton());
            kraken.await().implicitly(1); // Ожидание перехода со страницы cloudpayments
        }
    }

    /** Добавить новое юр. лицо */
    private void addNewJuridical(JuridicalData juridicalData) {
        HelperBase.verboseMessage(
                "Добавляем данные юр. лица " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        if (kraken.detect().isElementDisplayed(Elements.Checkout.addJuridicalButton())) {
            kraken.perform().click(Elements.Checkout.addJuridicalButton());
            fillJuridicalDetails(juridicalData);
            kraken.perform().click(Elements.Checkout.JuridicalModal.confirmButton());
            kraken.await().implicitly(1); // Ожидание добавления нового юрлица
        } else {
            fillJuridicalDetails(juridicalData);
        }
    }

    /** Заполнить данные юр. лица */
    private void fillJuridicalDetails(JuridicalData companyRequisites) {
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.innField(), companyRequisites.getInn());
        kraken.await().simply(1); // Ожидание подгрузки юрлица по ИНН
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.nameField(), companyRequisites.getJuridicalName());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.addressField(), companyRequisites.getJuridicalAddress());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.kppField(), companyRequisites.getKpp());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.operatingAccountField(), companyRequisites.getAccountNumber());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.bikField(), companyRequisites.getBik());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.bankField(), companyRequisites.getBankName());
        kraken.perform().fillField(Elements.Checkout.JuridicalModal.correspondentAccountField(), companyRequisites.getCorrespondentAccountNumber());
    }

    /** Удалить всех юр. лиц, кроме одного */
    private void deleteAllExceptOneJuridical() {
        if (kraken.detect().isSecondJuridicalEntered()) {
            deleteJuridical();
            deleteAllExceptOneJuridical();
        }
    }

    /** Удалить юр. лицо */
    private void deleteJuridical() {
        kraken.perform().click(Elements.Checkout.changeJuridicalButton());
        HelperBase.verboseMessage(
                "Удаляем данные юр. лица " + kraken.grab().value(Elements.Checkout.JuridicalModal.nameField())
                        + ", ИНН: " + kraken.grab().value(Elements.Checkout.JuridicalModal.innField()));
        kraken.perform().click(Elements.Checkout.JuridicalModal.deleteButton());
        kraken.await().implicitly(1); // Ожидание удаления юрлица
    }

    /** Выбрать юр. лицо */
    private void selectJuridical(JuridicalData juridicalData) {
        ElementData title = Elements.Checkout.juridicalTitle(juridicalData);
        if (kraken.detect().isElementDisplayed(title)) {
            HelperBase.verboseMessage("Выбираем данные юр. лица " + kraken.grab().text(title));
            kraken.perform().click(title);
            kraken.await().implicitly(1); // Ожидание применения выбранного юрлица
        } else if (kraken.detect().isJuridicalEntered()) {
            changeJuridical(juridicalData);
        } else {
            addNewJuridical(juridicalData);
        }
    }

    /** Изменить юр. лицо */
    private void changeJuridical(JuridicalData juridicalData) {
        kraken.perform().click(Elements.Checkout.changeJuridicalButton());
        HelperBase.verboseMessage("Меняем данные юр. лица\nС : " + kraken.grab().value(Elements.Checkout.JuridicalModal.nameField())
                + ", ИНН: " + kraken.grab().value(Elements.Checkout.JuridicalModal.innField())
                + "\nНА: " + juridicalData.getJuridicalName() + ", ИНН: " + juridicalData.getInn());
        fillJuridicalDetails(juridicalData);
        kraken.perform().click(Elements.Checkout.JuridicalModal.confirmButton());
        kraken.await().implicitly(1); // Ожидание сохранения изменений юрлица
    }
}
