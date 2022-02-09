package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.fail;

public class ImportsV1Request extends ApiV1RequestBase {

    public static class FilterFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.FILTERS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.FILTERS_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.FILTERS_FILES)
        public static Response POST(String filePath, String mode) {
            return givenWithAuth()
                    .formParam("mode", mode)
                    .multiPart("payload", new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.FILTERS_FILES);
        }
    }

    public static class PricesFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.PRICES_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.PRICES_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.PRICES_FILES)
        public static Response POST(String fileName, String filePath) {
            byte[] fileBytes = null;
            try {
                fileBytes = Files.readAllBytes(Paths.get(filePath));
            } catch (IOException e) {
                fail("Can't read a file. Error: " + e.getMessage());
            }
            return givenWithAuth()
                    .multiPart("payload", fileName, fileBytes,  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.PRICES_FILES);
        }
    }

    public static class ProductsImagesArchives {
        @Step("{method} /" + ApiV1Endpoints.Imports.PRODUCTS_IMAGES_ARCHIVES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.PRODUCTS_IMAGES_ARCHIVES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.PRODUCTS_IMAGES_ARCHIVES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/zip")
                    .post(ApiV1Endpoints.Imports.PRODUCTS_IMAGES_ARCHIVES);
        }
    }

    public static class EansFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.EANS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.EANS_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.EANS_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "text/xml")
                    .post(ApiV1Endpoints.Imports.EANS_FILES);
        }
    }

    public static class OffersFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.OFFERS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.OFFERS_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.OFFERS_FILES)
        public static Response POST(byte[] fileBytes) {
            return givenWithAuth()
                    .multiPart("payload", "offers.xlsx", fileBytes,  "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.OFFERS_FILES);
        }
    }

    public static class ProductsFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.PRODUCTS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.PRODUCTS_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.PRODUCTS_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.PRODUCTS_FILES);
        }
    }

    public static class OffersStocksFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.OFFERS_STOCKS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.OFFERS_STOCKS_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.OFFERS_STOCKS_FILES)
        public static Response POST(byte[] fileBytes) {
            return givenWithAuth()
                    .multiPart("payload", "offers.xlsx", fileBytes, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.OFFERS_STOCKS_FILES);
        }
    }

    public static class MasterCategoriesFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.MASTER_CATEGORIES_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.MASTER_CATEGORIES_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.MASTER_CATEGORIES_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.MASTER_CATEGORIES_FILES);
        }
    }

    public static class MasterCategoryAttributesFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.MASTER_CATEGORY_ATTRIBUTES_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.MASTER_CATEGORY_ATTRIBUTES_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.MASTER_CATEGORY_ATTRIBUTES_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.MASTER_CATEGORY_ATTRIBUTES_FILES);
        }
    }

    public static class BrandFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.BRAND_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.BRAND_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.BRAND_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.BRAND_FILES);
        }
    }

    public static class TaxonsFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.TAXONS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.TAXONS_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.TAXONS_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    .post(ApiV1Endpoints.Imports.TAXONS_FILES);
        }
    }

    public static class TaxonsImagesFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.TAXONS_IMAGES_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.TAXONS_IMAGES_FILES);
        }

        @Step("{method} /" + ApiV1Endpoints.Imports.TAXONS_IMAGES_FILES)
        public static Response POST(String filePath) {
            return givenWithAuth()
                    .multiPart("payload", new File(filePath), "application/zip")
                    .post(ApiV1Endpoints.Imports.TAXONS_IMAGES_FILES);
        }
    }

    public static class PageMetasFiles {
        @Step("{method} /" + ApiV1Endpoints.Imports.PAGE_METAS_FILES)
        public static Response GET() {
            return givenWithAuth()
                    .queryParam("per_page", 10000)
                    .get(ApiV1Endpoints.Imports.PAGE_METAS_FILES);
        }
    }
}
