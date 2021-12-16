package ru.instamart.grpc.data_provider;

import catalog_api_v2.CatalogApiV2;
import org.testng.annotations.DataProvider;

public class GrpcDataProvider {

    @DataProvider(name = "catalogProductListData")
    public static Object[][] getCatalogProductListData() {
        return new Object[][]{
                {CatalogApiV2
                        .GetProductListRequest.newBuilder()
                        .setSid("1")
                        .setTid("22238")
                        .setPage(1)
                        .setPerPage(24)
                        .setSort("0")
                        .setTenantId("sbermarket")
                        .build()},
                {CatalogApiV2
                        .GetProductListRequest.newBuilder()
                        .setSid("1")
                        .setTid("22238")
                        .setPage(1)
                        .setPerPage(24)
                        .setSort("")
                        .setTenantId("sbermarket")
                        .build()},
                {CatalogApiV2
                        .GetProductListRequest.newBuilder()
                        .setSid("1")
                        .setTid("22238")
                        .setPage(1)
                        .setPerPage(24)
                        .setTenantId("sbermarket")
                        .build()}
        };
    }
}
