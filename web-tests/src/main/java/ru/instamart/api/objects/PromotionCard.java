package instamart.api.objects;

public class PromotionCard extends BaseObject {

    private Integer id;
    private String title;
    private String short_description;
    private String full_description;
    private String background_color;
    private String text_color;
    private String type;
    private Integer promotion_id;
    private Object mobile_link;
    private String tag;
    private Integer position;
    private Image background_image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getFull_description() {
        return full_description;
    }

    public void setFull_description(String full_description) {
        this.full_description = full_description;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(Integer promotion_id) {
        this.promotion_id = promotion_id;
    }

    public Object getMobile_link() {
        return mobile_link;
    }

    public void setMobile_link(Object mobile_link) {
        this.mobile_link = mobile_link;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Image getBackground_image() {
        return background_image;
    }

    public void setBackground_image(Image background_image) {
        this.background_image = background_image;
    }

}
