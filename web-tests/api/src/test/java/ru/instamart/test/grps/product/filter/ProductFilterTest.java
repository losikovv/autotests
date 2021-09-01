package ru.instamart.test.grps.product.filter;

import io.qameta.allure.Epic;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import product_filter.ProductFilterGrpc;
import product_filter.ProductFilterOuterClass;
import ru.instamart.api.common.GrpcBase;

import static org.testng.Assert.assertEquals;

@Epic("Product Filter Microservice")
@Slf4j
public class ProductFilterTest extends GrpcBase {
    private ProductFilterGrpc.ProductFilterBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void createClient() {
        channel = grpcStep.createChannel("paas-content-product-filter.k-stage.sbmt.io", 443);
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

        grpcStep.showRequestInAllure(request);

        var response = client.getCategoryFacetsByCategoryIDs(request);

        grpcStep.showResponseInAllure(response);

        response.getFacetsList().forEach(facet ->
                assertEquals(facet.getCategoryId(), "175", "Вернулась категория с другим ID"));
    }
}
