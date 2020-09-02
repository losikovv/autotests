package ru.instamart.application.rest;

import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import ru.instamart.application.AppManager;
import ru.instamart.application.Config;

import java.util.UUID;

public abstract class RestBase {
    protected static final AppManager kraken = new AppManager();
    protected Response response;

    @BeforeSuite(groups = {"rest","rest-testing","rest-zones","api.v2"})
    public void start() throws Exception {
        kraken.riseRest();
    }

    public String email() {
        return UUID.randomUUID() + "@example.com";
    }
}
