package instamart.api.shopper;

import instamart.api.shopper.responses.SessionsResponse;

public class ShopperApiHelper extends ShopperApiRequests{

    public ShopperApiHelper() {
    }

    /**
     * Авторизация
     */
    public void authorisation(String userName, String password) {
        token.set(postSessions(userName, password)
                .as(SessionsResponse.class)
                .getData()
                .getAttributes()
                .getAccessToken());
        System.out.println("Авторизуемся: " + userName + " / " + password);
        System.out.println("access_token: " + token.get() + "\n");
    }
}
