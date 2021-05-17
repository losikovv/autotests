package ru.instamart.ui.data.pagesdata;

public final class PageData {

    private final String path;
    private final String description;

    public PageData(final String path){
        this.path = path;
        this.description = null;
    }

    public PageData(final String path, final String description){
        this.path = path;
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        if (path.contains("admin")) {
            return EnvironmentData.INSTANCE.getAdminUrlWithHttpAuth() + path.substring(6);
        } else return EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + path;
    }
}
