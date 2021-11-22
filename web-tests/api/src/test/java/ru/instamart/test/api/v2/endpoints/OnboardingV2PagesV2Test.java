package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.OnboardingPageV2;
import ru.instamart.api.request.v2.OnboardingV2PagesV2Request;
import ru.instamart.api.response.v2.OnboardingPagesV2Response;
import ru.instamart.jdbc.dao.OnboardingV2PagesDao;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Получение списка страниц онбординга (Сториз)")
public class OnboardingV2PagesV2Test extends RestBase {

    @CaseId(821)
    @Story("Экраны онбординга")
    @Test(description = "Автоматическое получение экранов онбординга при старте приложения",
            groups = {"api-instamart-regress"})
    public void getOnboardingPages() {
        final Response response = OnboardingV2PagesV2Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OnboardingPagesV2Response.class);
        List<OnboardingPageV2> onboardingPages = response.as(OnboardingPagesV2Response.class).getOnboardingPages();
        compareTwoObjects(onboardingPages.size(), OnboardingV2PagesDao.INSTANCE.getCount());
    }
}
