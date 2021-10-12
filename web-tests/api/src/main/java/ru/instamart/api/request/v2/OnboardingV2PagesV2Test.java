package ru.instamart.api.request.v2;

import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.response.v2.OnboardingPagesV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class OnboardingV2PagesV2Test extends RestBase {

    @Test(  description = "Получаем экраны онбординга V2",
            groups = {"api-instamart-regress"})
    public void getOnboardingPages() {
        response = OnboardingV2PagesV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OnboardingPagesV2Response.class).getOnboardingPages(),
                "Не вернулись экраны онборлдинга");
    }
}
