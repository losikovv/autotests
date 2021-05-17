package ru.instamart.core.testdata.pagesdata;

public class StaticPageData {
    private final String pageName;
    private final String pageURL;
    private final String text;
    private final String position;
    private final String description;

    public StaticPageData(String pageName, String pageURL, String text, String position, String description) {
        this.pageName = pageName;
        this.pageURL = pageURL;
        this.text = text;
        this.position = position;
        this.description = description;
    }

    public String getPageName() {
        return pageName;
    }
    public String getPageURL() {
        return pageURL;
    }
    public String getText() {
        return text;
    }
    public String getPosition() {
        return position;
    }
    public String getDescription() {
        return description;
    }
}
