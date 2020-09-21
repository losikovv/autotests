package instamart.api.objects;

import java.util.StringJoiner;

public class DeliveryWindow extends BaseObject {

    private Integer id;
    private String starts_at;
    private String ends_at;
    private String weight_balance;
    private String items_count_balance;
    private String kind;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStarts_at() {
        return starts_at;
    }

    public void setStarts_at(String starts_at) {
        this.starts_at = starts_at;
    }

    public String getEnds_at() {
        return ends_at;
    }

    public void setEnds_at(String ends_at) {
        this.ends_at = ends_at;
    }

    public String getWeight_balance() {
        return weight_balance;
    }

    public void setWeight_balance(String weight_balance) {
        this.weight_balance = weight_balance;
    }

    public String getItems_count_balance() {
        return items_count_balance;
    }

    public void setItems_count_balance(String items_count_balance) {
        this.items_count_balance = items_count_balance;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return new StringJoiner(
                "\n",
                "Получена информация о слоте: ",
                "\n")
                .add("                 id: " + id)
                .add("          starts_at: " + starts_at)
                .add("            ends_at: " + ends_at)
                .add("     weight_balance: " + weight_balance)
                .add("items_count_balance: " + items_count_balance)
                .add("               kind: " + kind)
                .toString();
    }
}
