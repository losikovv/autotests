package ru.instamart.application.rest;

import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import ru.instamart.application.AppManager;

import java.util.UUID;

public class RestBase {
    protected static final AppManager kraken = new AppManager();
    protected Response response;

    @BeforeSuite(alwaysRun = true)
    public void start() throws Exception {
        kraken.riseRest();
    }

    @AfterMethod(alwaysRun = true)
    public void printResponse() {
        System.out.println("\nResponse:\u001B[36m");
        response.prettyPeek();
        System.out.println("\u001B[0m");
    }

    public String email() {
        return UUID.randomUUID() + "@example.com";
    }
}
