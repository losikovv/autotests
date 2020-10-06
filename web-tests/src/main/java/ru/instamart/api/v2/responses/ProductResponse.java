package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Product;

public class ProductResponse extends BaseResponseObject {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
