package ru.instamart.application.rest.objects;

public class LineItem extends BaseObject {

    private Integer id;
    private Integer quantity;
    private Double price;
    private Double total;
    private Object customer_comment;
    private Boolean product_in_stock;
    private Product product;

    /**
     * No args constructor for use in serialization
     *
     */
    public LineItem() {
    }

    /**
     *
     * @param product_in_stock
     * @param total
     * @param product
     * @param quantity
     * @param price
     * @param id
     * @param customer_comment
     */
    public LineItem(Integer id, Integer quantity, Double price, Double total, Object customer_comment, Boolean product_in_stock, Product product) {
        super();
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.customer_comment = customer_comment;
        this.product_in_stock = product_in_stock;
        this.product = product;
    }

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

}
