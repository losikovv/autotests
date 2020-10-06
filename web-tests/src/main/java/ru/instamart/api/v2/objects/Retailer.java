package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

import java.util.StringJoiner;

public class Retailer extends BaseObject {

    private Integer id;
    private String name;
    private String slug;
    private String color;
    private String logo_background_color;
    private String logo;
    private Boolean available;
    private String environment;
    private String key;

    /**
     * No args constructor for use in serialization
     *
     */
    public Retailer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLogo_background_color() {
        return logo_background_color;
    }

    public void setLogo_background_color(String logo_background_color) {
        this.logo_background_color = logo_background_color;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return new StringJoiner(
                ", ",
                "",
                "")
                .add(name)
                .add("id: " + id)
                .toString();
    }
}
