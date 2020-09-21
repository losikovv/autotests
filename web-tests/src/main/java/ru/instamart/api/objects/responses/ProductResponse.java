package instamart.api.objects.responses;

import instamart.api.objects.Product;

public class ProductResponse extends BaseResponseObject {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
