package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OptionTypesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.OPTION_TYPES)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.OPTION_TYPES);
    }

    @Step("{method} /" + AdminEndpoints.OPTION_TYPES)
    public static Response POST(String name, String presentation) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("option_type[name]", name);
        formParams.put("option_type[presentation]", presentation);
        return givenWithAuth()
                .formParams(formParams)
                .post(AdminEndpoints.OPTION_TYPES);
    }

    public static Response PATCH(String optionTypeId, String name, String presentation) {
        return PATCH(optionTypeId, name, presentation, null, null, null, null, null);
    }

    @Step("{method} /" + AdminEndpoints.OPTION_TYPE)
    public static Response PATCH(String optionTypeId, String optionName, String optionPresentation, String localValueId,
                                String valueId, String valueName, String valuePresentation, Boolean valueDestroy) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("option_type[name]", optionName);
        formParams.put("option_type[presentation]", optionPresentation);
        if (Objects.nonNull(localValueId)) {
            if (Objects.nonNull(valueId)) formParams.put("option_type[option_values_attributes][" + localValueId + "][id]", valueId);
            if (Objects.nonNull(valueName)) formParams.put("option_type[option_values_attributes][" + localValueId + "][name]", valueName);
            if (Objects.nonNull(valuePresentation)) formParams.put("option_type[option_values_attributes][" + localValueId + "][presentation]", valuePresentation);
            if (Objects.nonNull(valueDestroy)) formParams.put("option_type[option_values_attributes][" + localValueId + "][_destroy]", valueDestroy);
        }
        return givenWithAuth()
                .formParams(formParams)
                .patch(AdminEndpoints.OPTION_TYPE, optionTypeId);
    }

    @Step("{method} /" + AdminEndpoints.OPTION_TYPE)
    public static Response DELETE(String optionTypeId) {
        return givenWithAuth()
                .delete(AdminEndpoints.OPTION_TYPE, optionTypeId);
    }

    public static class OptionValue {

        @Step("{method} /" + AdminEndpoints.OPTION_VALUE)
        public static Response DELETE(String optionValueId) {
            return givenWithAuth()
                    .delete(AdminEndpoints.OPTION_VALUE, optionValueId);
        }
    }
}
