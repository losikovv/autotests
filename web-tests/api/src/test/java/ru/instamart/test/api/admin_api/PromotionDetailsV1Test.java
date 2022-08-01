package ru.instamart.test.api.admin_api;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.PromotionDetailsV1Request;
import ru.instamart.api.response.v1.admin.PromotionCodeV1Response;
import ru.instamart.jdbc.dao.stf.PromotionCodesDao;
import ru.instamart.jdbc.dto.stf.PromotionCodesFilters;
import ru.instamart.jdbc.entity.stf.PromotionCodesEntity;

import java.util.List;
import java.util.Objects;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Промокоды")
public class PromotionDetailsV1Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApiWithAdminNewRoles();
    }

    @Test(description = "Получение деталей промокода",
            groups = {"api-instamart-regress"})
    public void getPromotionDetails() {
        List<PromotionCodesEntity> promoCodes = PromotionCodesDao.INSTANCE.findAll(PromotionCodesFilters.builder()
                .value("auto%")
                .usageLimit(0)
                .limit(1)
                .build());
        if(Objects.equals(promoCodes.size(),0)){
           new SkipException("Промокод вернулся пустым");
        }
        final Response response = PromotionDetailsV1Request.POST(promoCodes.get(0).getId(), "test");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionCodeV1Response.class);
        var promotionCodeV1Response = response.as(PromotionCodeV1Response.class);
        Allure.step("Проверка промокода из БД",
                ()->assertEquals(promotionCodeV1Response.getPromotionCode().getValue(), promoCodes.get(0).getValue(), "Промокод не совпадает"));

    }
}
