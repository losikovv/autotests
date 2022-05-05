package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.PromotionDetailsV1Request;
import ru.instamart.jdbc.dao.stf.PromotionCodesDao;
import ru.instamart.jdbc.dto.stf.PromotionCodesFilters;
import ru.instamart.jdbc.entity.stf.PromotionCodesEntity;

import java.util.List;

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
    public void getPromotionDetails(){
        List<PromotionCodesEntity> promoCodes = PromotionCodesDao.INSTANCE.findAll(PromotionCodesFilters.builder()
                .value("auto%")
                .usageLimit(0)
                .limit(1)
                .build());
        final Response response = PromotionDetailsV1Request.POST(promoCodes.get(0).getId(), "test");
        checkStatusCode200(response);
//TODO:добавить проверку схемы и проверку промокода (сравнить с полученным из БД)
    }
}
