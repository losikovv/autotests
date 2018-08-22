package ru.instamart.autotests.configuration;



// Список страниц с урлами и названиями



public class Pages {
    static String pageTitle;
    static String pagePath;

    Pages(String title,String path) {
        pageTitle = title;
        pagePath = path;
    }

    public interface Site {
        static Pages checkout() {
            return new Pages("Checkout","/checkout/edit?");
        }
        // TODO
    }

    public interface Admin {
        // TODO
    }

    public static String getPageTitle() {
        return pageTitle;
    }

    public static String getPageUrl() {
        return pagePath;
    }

}
