package instamart.api.responses.v1;

import instamart.api.objects.v1.ShoppersBackend;
import instamart.api.responses.BaseResponseObject;

public class TokensResponse extends BaseResponseObject {

    private ShoppersBackend shoppers_backend;

    public ShoppersBackend getShoppers_backend() {
        return shoppers_backend;
    }

    public void setShoppers_backend(ShoppersBackend shoppers_backend) {
        this.shoppers_backend = shoppers_backend;
    }

}
