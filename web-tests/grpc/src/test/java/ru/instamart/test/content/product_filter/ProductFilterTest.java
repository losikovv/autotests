package ru.instamart.test.content.product_filter;

import io.qameta.allure.Epic;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_filter.ProductFilterGrpc;
import product_filter.ProductFilterOuterClass;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcHosts;

import static org.testng.Assert.assertEquals;

@Epic("Product Filter Microservice")
@Slf4j
public class ProductFilterTest extends GrpcBase {
    private ProductFilterGrpc.ProductFilterBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpc.createChannel(GrpcHosts.PAAS_CONTENT_PRODUCT_FILTER);
        client = ProductFilterGrpc.newBlockingStub(channel);
    }

    @Test(  description = "Get category facets by category IDs",
            groups = "grpc-product-hub")
    public void getCategoryFacetsByCategoryIds() {
        var request = ProductFilterOuterClass
                .GetCategoryFacetsByCategoryIDsRequest
                .newBuilder()
                .setStoreId("1")
                .setTenantId("sbermarket")
                .addCategoryIds("175")
                .setAvailable(true)
                .build();

        var response = client.getCategoryFacetsByCategoryIDs(request);

        response.getFacetsList().forEach(facet ->
                assertEquals(facet.getCategoryId(), "175", "Вернулась категория с другим ID"));
    }
}
