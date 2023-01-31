package ru.instamart.api.request.assembly;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AssemblyEndpoints;
import ru.instamart.api.request.AssemblyRequestBase;

public class AssemblyV1Request extends AssemblyRequestBase {

    public static class Offer {

        @Step("{method} /" + AssemblyEndpoints.OFFER)
        public static Response GET() {
            return givenWithAuth()
                    .get(AssemblyEndpoints.OFFER);
        }

        public static class Accept {
            @Step("{method} /" + AssemblyEndpoints.Offer.ACCEPT)
            public static Response PUT(final String uuid) {
                return givenWithAuth()
                        .put(AssemblyEndpoints.Offer.ACCEPT, uuid);
            }
        }
    }
}
