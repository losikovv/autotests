package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;

import static io.restassured.RestAssured.given;

public class ShadowcatRequestBase {
    private static final String authToken = "Bearer eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjowfQ.h5sL3f7aqYxDHvqoGO3PHQRDhQ71rsJUEPviQGl1QllSw5dAvfWEu8Qy9x0e81psePk-VN6xZcJEdBH7oADdG8LVGjaR25potng-g7TeizM5qJQ0IhOJSHq8HUkZZVhUNdp4Hd4wl5e5Jdet5VFfVs8SvrfhrBbmFUkzQpYE3hSp_Z-zouJDlnD25nIJYK_6kAf-IXsscyLmklm7HRj5CyL_VgXkhrOpEF3LczpYvDy86UcYJZD8Kyq9j10I8bJMRio4goQpUBJ1wCH2cDa9tRwA4aFWhYvYzQmYANaeyv9f8WD523YV4lFxxCZupKEeXDA-VeGFV1ZzR7rD_u4fNdWeYkUk7ZgzfGVB8n7vqm13HcM7fLvQKcOpsL_IZJTudR8KgqueL8ky4dfR02kp-VL30OgHUauzyQS-YVImx-M7cCib-jZbavq86cKVHee28-jamaST8ORozT-j46yg1CnP5lozkHnDVoUjh-WIAIeMt8bFZvEnYQOQNAmE9qBfRzQSNgqatH32JPYA5j23dJf0XA53tBHg02g00AwQxpEhzlw1LGo2gY4k0lA-8AQ4nIfMIfy65W2wxCOULd4-DHVUJ_SWHm43nwIbfGHEzHYUhxjID7yj6OP05D2lYTAejcdN2UdutRXJK4oZOm-o8H6zOwyHvIvm7fcNt6AljjM";

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getShadowcatRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization",
                        authToken);
    }
}
