package instamart.ui.common.pagesdata;

import instamart.core.common.AppManager;

public class PageData {
    private String path;
    private String description;

    // Todo временный конструктор
    public PageData(String path){
        this.path = path;
        this.description = null;
    }

    public PageData(String path, String description){
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
            return AppManager.environment.getAdminUrlWithHttpAuth() + path.substring(6);
        } else return AppManager.environment.getBasicUrlWithHttpAuth() + path;
    }
}
