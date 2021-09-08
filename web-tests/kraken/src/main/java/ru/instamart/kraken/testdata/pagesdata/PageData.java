package ru.instamart.kraken.testdata.pagesdata;

import ru.instamart.kraken.config.EnvironmentProperties;

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
            return EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + path.substring(6);
        } else return EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + path;
    }
}
