package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAdminEndpoints;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

public class ScangoSHPRequest extends ShopperAppRequestBase {

    public static class Assemblies{
        @Step("{method} /" + ShopperAppEndpoints.Scango.Assemblies.CONFIG)
        public static Response GET(String assemblyId){
            return givenWithAuth()
                    .get(ShopperAppEndpoints.Scango.Assemblies.CONFIG, assemblyId);
        }
    }
}
