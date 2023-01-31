package ru.instamart.api.request.admin_gw;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;
import ru.instamart.api.endpoint.AdminGwEndpoints;
import ru.instamart.api.request.AdminRequestBase;

public class AssemblyAdminRequest extends AdminRequestBase {
    public static class FreePickers {
        @Step("{method} /" + AdminGwEndpoints.Assembly.FREE_PICKERS)
        public static Response GET(final String uuid) {
            return givenWithAuth()
                    .get(AdminGwEndpoints.Assembly.FREE_PICKERS, uuid);
        }
    }

    public static class Offer {
        @Step("{method} /" + AdminGwEndpoints.Assembly.OFFER)
        public static Response POST(final String pickerUUID, final String shipmentUUID) {
            var json = new JSONObject();
            json.put("picker_uuid", pickerUUID);
            json.put("shipment_uuid", shipmentUUID);

            return givenWithAuth()
                    .body(json)
                    .post(AdminGwEndpoints.Assembly.OFFER);
        }
    }

}
