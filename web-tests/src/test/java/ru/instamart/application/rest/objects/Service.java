package ru.instamart.application.rest.objects;

public class Service extends BaseObject {

    private Integer id;
    private String key;
    private String description;

    /**
     * No args constructor for use in serialization
     *
     */
    public Service() {
    }

    /**
     *
     * @param description
     * @param id
     * @param key
     */
    public Service(Integer id, String key, String description) {
        super();
        this.id = id;
        this.key = key;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
