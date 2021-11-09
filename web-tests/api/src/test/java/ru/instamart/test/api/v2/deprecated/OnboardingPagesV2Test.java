package ru.instamart.test.api.v2.deprecated;

import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.OnboardingPagesV2Request;
import ru.instamart.api.response.v2.OnboardingPagesV2Response;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Deprecated
public class OnboardingPagesV2Test extends RestBase {

    @Deprecated
    @Test(  description = "Получаем экраны онбординга",
            groups = {})
    public void getOnboardingPages() {
        response = OnboardingPagesV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(OnboardingPagesV2Response.class).getOnboardingPages(),
                "Не вернулись экраны онборлдинга");
    }
}
