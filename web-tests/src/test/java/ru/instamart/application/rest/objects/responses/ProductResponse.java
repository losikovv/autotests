package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Product;

public class ProductResponse extends BaseResponseObject {

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
