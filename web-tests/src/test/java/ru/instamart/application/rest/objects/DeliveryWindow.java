package ru.instamart.application.rest.objects;

import java.util.StringJoiner;

public class DeliveryWindow extends BaseObject {

    private Integer id;
    private String starts_at;
    private String ends_at;

    /**
     * No args constructor for use in serialization
     *
     */
    public DeliveryWindow() {
    }

    /**
     *
     * @param starts_at
     * @param id
     * @param ends_at
     */
    public DeliveryWindow(Integer id, String starts_at, String ends_at) {
        super();
        this.id = id;
        this.starts_at = starts_at;
        this.ends_at = ends_at;
    }

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

    @Override
    public String toString() {
        return new StringJoiner(
                "\n",
                "Получена информация о слоте: ",
                "\n")
                .add("id: " + id)
                .add("starts_at: " + starts_at)
                .add("  ends_at: " + ends_at)
                .toString();
    }
}
