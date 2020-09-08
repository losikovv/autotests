package ru.instamart.application.rest.objects;

public class SearchPhrase extends BaseObject {

    private String name;
    private Icon icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

}
