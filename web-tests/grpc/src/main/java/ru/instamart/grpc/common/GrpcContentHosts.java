package ru.instamart.grpc.common;

import java.util.Optional;

public final class GrpcContentHosts {
    public static final String PAAS_CONTENT_PRODUCT_HUB_FRONT = "paas-content-product-hub.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_PRODUCT_HUB_BACK = "paas-content-product-hub-back.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_PRODUCT_FILTER = "paas-content-product-filter.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_CATALOG = "paas-content-catalog.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_CATALOG_NAVIGATION = "paas-content-catalog-navigation.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_CATALOG_SHELF = "paas-content-catalog-shelf.k-stage.sbmt.io";
    //public static final String PAAS_CONTENT_SALUT_TOKEN = "paas-content-salut-token.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_OPERATIONS_WORKFLOW = "paas-content-operations-workflow.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_ANALYTICS_ORDER_PRICING = "paas-content-analytics-order-pricing.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_OPERATIONS_ETA = "paas-content-operations-eta.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_OPERATIONS_CANDIDATES = "paas-content-operations-candidates.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_OPERATIONS_TAG_MANAGER = "paas-content-operations-tag-manager.k-stage.sbmt.io";
    public static final String PAAS_CONTENT_OPERATIONS_SHIPPINGCALC = Optional.ofNullable(System.getProperty("url_paas_shippingcalc")).orElse("paas-content-operations-shippingcalc") + ".k-stage.sbmt.io";
    public static final String PAAS_CONTENT_OPERATIONS_ORDER_SERVICE = "paas-content-operations-order-service.k-stage.sbmt.io";

}
