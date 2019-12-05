package ru.instamart.application.rest.objects;

public class Icon extends BaseObject {

    private String mini_url;
    private String normal_url;

    /**
     * No args constructor for use in serialization
     *
     */
    public Icon() {
    }

    /**
     *
     * @param normal_url
     * @param mini_url
     */
    public Icon(String mini_url, String normal_url) {
        super();
        this.mini_url = mini_url;
        this.normal_url = normal_url;
    }

    public String getMini_url() {
        return mini_url;
    }

    public void setMini_url(String mini_url) {
        this.mini_url = mini_url;
    }

    public String getNormal_url() {
        return normal_url;
    }

    public void setNormal_url(String normal_url) {
        this.normal_url = normal_url;
    }

}