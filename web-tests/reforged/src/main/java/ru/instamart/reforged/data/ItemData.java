package ru.instamart.reforged.data;

/**
 * Класс с данными товара (инфа, относящаяся к конкретному товару)
 */
public class ItemData {

    private String count;
    private String name;
    private String packageSize;
    private String price;
    private String totalAmount;


    public String getCount() {
        return count;
    }

    public ItemData setCount(String count) {
        this.count = count;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemData setName(String name) {
        this.name = name;
        return this;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public ItemData setPackageSize(String packageSize) {
        this.packageSize = packageSize;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public ItemData setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public ItemData setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    @Override
    public String toString(){
        return "name: " + name + "; packageSize: " + packageSize + "; price: " + price + "; count:  " + count + "; totalAmount:  " + totalAmount + "\n";
    }
}
