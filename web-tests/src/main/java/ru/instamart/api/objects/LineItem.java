package instamart.api.objects;

import java.util.StringJoiner;

public class LineItem extends BaseObject {

    private Integer id;
    private Integer quantity;
    private Integer packs;
    private Double price;
    private Double total;
    private Double discount;
    private Object customer_comment;
    private Boolean product_in_stock;
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Object getCustomer_comment() {
        return customer_comment;
    }

    public void setCustomer_comment(Object customer_comment) {
        this.customer_comment = customer_comment;
    }

    public Boolean getProduct_in_stock() {
        return product_in_stock;
    }

    public void setProduct_in_stock(Boolean product_in_stock) {
        this.product_in_stock = product_in_stock;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getPacks() {
        return packs;
    }

    public void setPacks(Integer packs) {
        this.packs = packs;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "В корзину добавлен товар:\n",
                "\n")
                .add(getProduct().getName())
                .add("id: " + getProduct().getId())
                .add("quantity: " + quantity)
                .add("total: " + total)
                .toString();
    }
}
