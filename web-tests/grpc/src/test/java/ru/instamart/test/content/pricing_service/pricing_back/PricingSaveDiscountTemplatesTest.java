package ru.instamart.test.content.pricing_service.pricing_back;


import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pricing_back.PricingBackGrpc;
import ru.instamart.grpc.common.GrpcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.util.TimeUtil;
import static pricing_back.PricingBackOuterClass.*;


@Epic("Pricing service")
@Feature("Save discount templates")

public class PricingSaveDiscountTemplatesTest extends GrpcBase {
    private PricingBackGrpc.PricingBackBlockingStub client;

    @BeforeClass(alwaysRun = true)
    public void preconditions(){
        channel = grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_PRICING_BACK);
        client = PricingBackGrpc.newBlockingStub(channel);

    }


    @TmsLink("438")
    @Test(description = "Сохранение шаблона скидки только с обязательными полями (для productSku)",
            groups = {"pricing-save-discount-templates"})
    public void saveTemplateWithRequiredFieldsForProductSKU() {
        var request = SaveDiscountTemplatesRequest.newBuilder()
                .addDiscountTemplates(DiscountTemplate.newBuilder()
                        .setStartAt(TimeUtil.getTimestampFromString("2023-01-01T00:04:05.999+03:00"))
                        .setEndAt(TimeUtil.getTimestampFromString("2025-12-31T00:04:05.999+03:00"))
                        .setFilter(DiscountTemplateFilter.newBuilder()
                                .addProductSku(123)
                                .addDiscountValue(1)
                                .build())
                        .build())
                .build();

        var response = client.saveDiscountTemplates(request);

        Assert.assertEquals(request.getDiscountTemplates(0).getStartAt(), response.getDiscountTemplates(0).getStartAt());
        Assert.assertEquals(request.getDiscountTemplates(0).getEndAt(), response.getDiscountTemplates(0).getEndAt());
        Assert.assertEquals(request.getDiscountTemplates(0).getFilter().toString(), response.getDiscountTemplates(0).getFilter().toString());

        Assert.assertEquals(request.getDiscountTemplates(0).getProviderType().toString(), "MARKETING");
        Assert.assertEquals(request.getDiscountTemplates(0).getOperationType().toString(), "MARKUP_VALUE");
        Assert.assertEquals(request.getDiscountTemplates(0).getStatus().toString(), "DISABLE");
        Assert.assertFalse(request.getDiscountTemplates(0).getIsUseWithOthers());
    }
}


