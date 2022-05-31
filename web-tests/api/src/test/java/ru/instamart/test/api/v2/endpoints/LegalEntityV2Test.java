package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.CompanyDocumentV2;
import ru.instamart.api.request.v2.CompanyDocumentsV2Request;
import ru.instamart.api.request.v2.LegalEntityV2Request;
import ru.instamart.api.response.v2.CompanyDocumentV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.helper.LegalEntityHelper.*;

@Epic(value = "ApiV2")
@Feature(value = "Реквизиты юридического лица")
public class LegalEntityV2Test extends RestBase {

    @CaseId(479)
    @Story("Запрос на получение данных юр. лица по ИНН")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "innFailedList",
            dataProviderClass = RestDataProvider.class,
            description = "Запрос на получение данных юр. лица по ИНН для несуществущего ИНН")
    @Parameters("inn")
    public void getINN400(String inn) {
        final Response response = LegalEntityV2Request.ByINN.GET(inn);
        checkStatusCode404(response);
         checkError(response, "Информация о компании не существует");
    }

    @CaseId(482)
    @Story("Создание нового реквизита для юр лица")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового реквизита для юр лица с заполнением обязательных полей")
    public void postCompanyDocuments200() {
        SessionFactory.makeSession(SessionType.API_V2);
        String name = "ООО \"Ромашка_" + (int) (Math.random() * 9999) + "\"";
        String inn = generateInnUL();
        String kpp = generateKpp();
        String bik = "043601607";
        String corAcc = "30101810200000000607";
        String rs = generateRS();

        CompanyDocumentsV2Request.CompanyDocument company = CompanyDocumentsV2Request.CompanyDocument.builder()
                .name(name)
                .inn(inn)
                .kpp(kpp)
                .bik(bik)
                .correspondent_account(corAcc)
                .operating_account(rs)
                .build();
        final Response response = CompanyDocumentsV2Request.POST(company);
        checkStatusCode200(response);
        CompanyDocumentV2 companyDocument = response.as(CompanyDocumentV2Response.class).getCompanyDocument();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(companyDocument.getName(), name, "Наименование не совпадает");
        softAssert.assertEquals(companyDocument.getInn(), inn, "ИНН не совпадает");
        softAssert.assertEquals(companyDocument.getKpp(), kpp, "КПП не совпадает");
        softAssert.assertEquals(companyDocument.getBik(), bik, "БИК не совпадает");
        softAssert.assertNull(companyDocument.getBank(), "Наименовние банка не пустое");
        softAssert.assertEquals(companyDocument.getCorrespondentAccount(), corAcc, "Корсчет не совпадает");
        softAssert.assertEquals(companyDocument.getOperatingAccount(), rs, "Расчетный счет не совпадает");
        softAssert.assertNull(companyDocument.getAddress(), "Адрес не пустой");
        softAssert.assertEquals(companyDocument.getPaymentTool().getName(), name + ", ИНН: " + inn, "Наименование платежа не совпадает");
        softAssert.assertEquals(companyDocument.getPaymentTool().getType(), "sber_bank_invoice", "Тип платежа не совпадает");
        softAssert.assertAll();
    }

    @CaseId(483)
    @Story("Создание нового реквизита для юр лица")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового реквизита для юр лица с не уникальным ИНН")
    public void postCompanyDocuments422() {
        SessionFactory.makeSession(SessionType.API_V2);
        String name = "ООО \"Ромашка_" + (int) (Math.random() * 9999) + "\"";
        String inn = "6828168540";
        String kpp = generateKpp();
        String bik = "043601607";
        String corAcc = "30101810200000000607";
        String rs = generateRS();

        CompanyDocumentsV2Request.CompanyDocument company = CompanyDocumentsV2Request.CompanyDocument.builder()
                .name(name)
                .inn(inn)
                .kpp(kpp)
                .bik(bik)
                .correspondent_account(corAcc)
                .operating_account(rs)
                .build();
        final Response response = CompanyDocumentsV2Request.POST(company);
        checkStatusCode422(response);
        checkError(response, "Вы не состоите в компании с таким ИНН. Проверьте ИНН или закрепите компанию на сайте СберМаркет во вкладке «Для бизнеса»");
    }

    @CaseId(484)
    @Story("Создание нового реквизита для юр лица")
    @Test(groups = {"api-instamart-regress"},
            description = "Создание нового реквизита для юр лица с заполнением всех полей")
    public void postCompanyAllDocuments200() {
        SessionFactory.makeSession(SessionType.API_V2);
        String name = "ООО \"Ромашка_" + (int) (Math.random() * 9999) + "\"";
        String inn = generateInnUL();
        String kpp = generateKpp();
        String bik = "043601607";
        String corAcc = "30101810200000000607";
        String rs = generateRS();
        String address = "Нижегородская область, городской округ Нижний Новгород, улица Бориса Панина, 7к3";
        String bank = "СберБанк";

        CompanyDocumentsV2Request.CompanyDocument company = CompanyDocumentsV2Request.CompanyDocument.builder()
                .name(name)
                .inn(inn)
                .kpp(kpp)
                .bik(bik)
                .correspondent_account(corAcc)
                .operating_account(rs)
                .address(address)
                .bank(bank)
                .build();
        final Response response = CompanyDocumentsV2Request.POST(company);
        checkStatusCode200(response);
        CompanyDocumentV2 companyDocument = response.as(CompanyDocumentV2Response.class).getCompanyDocument();
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(companyDocument.getName(), name, "Наименование не совпадает");
        softAssert.assertEquals(companyDocument.getInn(), inn, "ИНН не совпадает");
        softAssert.assertEquals(companyDocument.getKpp(), kpp, "КПП не совпадает");
        softAssert.assertEquals(companyDocument.getBik(), bik, "БИК не совпадает");
        softAssert.assertEquals(companyDocument.getBank(), bank, "Наименовние банка не совпадает");
        softAssert.assertEquals(companyDocument.getCorrespondentAccount(), corAcc, "Корсчет не совпадает");
        softAssert.assertEquals(companyDocument.getOperatingAccount(), rs, "Расчетный счет не совпадает");
        softAssert.assertEquals(companyDocument.getAddress(), address, "Адрес не совпадает");
        softAssert.assertEquals(companyDocument.getPaymentTool().getName(), name + ", ИНН: " + inn, "Наименование платежа не совпадает");
        softAssert.assertEquals(companyDocument.getPaymentTool().getType(), "sber_bank_invoice", "Тип платежа не совпадает");
        softAssert.assertAll();
    }


    @CaseId(482)
    @Story("Создание нового реквизита для юр лица")
    @Test(enabled = false, //TODO разобраться с данными по компаниям
            groups = {"api-instamart-regress"},
            description = "Создание нового реквизита для юр лица с заполнением обязательных полей")
    public void postCompanyAllDocuments200_1() {
        UserData defaultApiUser = UserManager.getDefaultApiUser();
        SessionFactory.createSessionToken(SessionType.API_V2, SessionProvider.PHONE, defaultApiUser);
        final Response response = LegalEntityV2Request.ByINN.GET("2453252070");
        checkStatusCode200(response);
    }


    @CaseId(856)
    @Story("Создание нового реквизита для юр лица")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "postCompanyDocuments",
            dataProviderClass = RestDataProvider.class,
            description = "Создание нового реквизита для юр лица с заполнением обязательных полей")
    @Parameters({"Компания", "Сообщение ошибки"})
    public void postCompanyDocuments422(CompanyDocumentsV2Request.CompanyDocument company, String errorMessage) {
        SessionFactory.makeSession(SessionType.API_V2);
        final Response response = CompanyDocumentsV2Request.POST(company);
        checkStatusCode422(response);
        checkError(response, errorMessage);
    }

}
