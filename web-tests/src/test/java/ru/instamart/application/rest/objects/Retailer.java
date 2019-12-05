package ru.instamart.application.rest.objects;

public class Retailer extends BaseObject {

    private Integer id;
    private String name;
    private String slug;
    private String color;
    private String logo_background_color;
    private String logo;
    private Boolean available;
    private String environment;

    /**
     * No args constructor for use in serialization
     *
     */
    public Retailer() {
    }

    /**
     *
     * @param environment
     * @param color
     * @param logo_background_color
     * @param name
     * @param available
     * @param logo
     * @param id
     * @param slug
     */
    public Retailer(Integer id, String name, String slug, String color, String logo_background_color, String logo, Boolean available, String environment) {
        super();
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.color = color;
        this.logo_background_color = logo_background_color;
        this.logo = logo;
        this.available = available;
        this.environment = environment;
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

}
