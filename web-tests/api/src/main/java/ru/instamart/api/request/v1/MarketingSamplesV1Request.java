package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static ru.instamart.kraken.util.TimeUtil.getDateWithoutTime;
import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;

public class MarketingSamplesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.MARKETING_SAMPLES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.MARKETING_SAMPLES);
    }

    @Step("{method} /" + ApiV1Endpoints.MARKETING_SAMPLES)
    public static Response POST() {
        Map<String, String> params = new HashMap<>();
        params.put("marketing_sample[description]", "Test description" + RandomUtils.nextInt(1, 100));
        params.put("marketing_sample[comment]", "Test comment" + RandomUtils.nextInt(1, 100));
        params.put("marketing_sample[starts_at]", getDateWithoutTime());
        params.put("marketing_sample[expires_at]", getFutureDateWithoutTime(5L));
        params.put("marketing_sample[store_ids][]", String.valueOf(EnvironmentProperties.DEFAULT_SID));
        params.put("marketing_sample[tenant_ids][]", "metro");
        params.put("marketing_sample[name]", "Test marketing sample" + RandomUtils.nextInt(1, 100));
        return givenWithAuth()
                .formParams(params)
                .multiPart("marketing_sample[uuids]", new File("src/test/resources/data/users.csv"), "text/csv")
                .multiPart("marketing_sample[image_attributes][attachment]", new File("src/test/resources/data/sample.jpg"), "image/jpeg")
                .post(ApiV1Endpoints.MARKETING_SAMPLES);
    }

    @Step("{method} /" + ApiV1Endpoints.MARKETING_SAMPLE)
    public static Response PUT(Long sampleId) {
        Map<String, String> params = new HashMap<>();
        params.put("marketing_sample[description]", "Test description" + RandomUtils.nextInt(1, 100));
        params.put("marketing_sample[comment]", "Test comment" + RandomUtils.nextInt(1, 100));
        params.put("marketing_sample[starts_at]", getDateWithoutTime());
        params.put("marketing_sample[expires_at]", getFutureDateWithoutTime(5L));
        params.put("marketing_sample[store_ids][]", String.valueOf(EnvironmentProperties.DEFAULT_SID));
        params.put("marketing_sample[tenant_ids][]", "metro");
        params.put("marketing_sample[name]", "Updated Test marketing sample" + RandomUtils.nextInt(1, 100));
        return givenWithAuth()
                .formParams(params)
                .multiPart("marketing_sample[uuids]", new File("src/test/resources/data/users.csv"), "text/csv")
                .multiPart("marketing_sample[image_attributes][attachment]", new File("src/test/resources/data/sample.jpg"), "image/jpeg")
                .put(ApiV1Endpoints.MARKETING_SAMPLE, sampleId);
    }

    @Step("{method} /" + ApiV1Endpoints.MARKETING_SAMPLE)
    public static Response GET(Long sampleId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.MARKETING_SAMPLE, sampleId);
    }

    @Step("{method} /" + ApiV1Endpoints.MARKETING_SAMPLE)
    public static Response DELETE(Long sampleId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.MARKETING_SAMPLE, sampleId);
    }
}
