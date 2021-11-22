package ru.instamart.api.common;

import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static ru.instamart.api.common.HttpRequestAttachmentCustom.Builder.create;

public class AllureRestAssuredCustom implements OrderedFilter {
    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";
    private String requestAttachmentName = "Request";
    private String responseAttachmentName;

    private static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
        final Map<String, String> result = new HashMap<>();
        items.forEach(h -> result.put(h.getName(), h.getValue()));
        return result;
    }

    @Override
    public Response filter(final FilterableRequestSpecification requestSpec,
                           final FilterableResponseSpecification responseSpec,
                           final FilterContext filterContext) {
        final Prettifier prettifier = new Prettifier();
        final String url = requestSpec.getURI();
        String req = requestSpec.getMethod() + " : " + requestSpec.getURI();
        if (!req.equals(" : ")) {
            requestAttachmentName = requestSpec.getMethod() + " : " + requestSpec.getURI();
        }
        final HttpRequestAttachmentCustom.Builder requestAttachmentBuilder = create(requestAttachmentName, url)
                .setMethod(requestSpec.getMethod())
                .setHeaders(toMapConverter(requestSpec.getHeaders()))
                .setCookies(toMapConverter(requestSpec.getCookies()));

        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachmentBuilder.setBody(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }
        if (Objects.nonNull(requestSpec.getFormParams())) {
            requestAttachmentBuilder.setFormParams(requestSpec.getFormParams());
        }
        if (Objects.nonNull(requestSpec.getRequestParams())) {
            requestAttachmentBuilder.setFormParams(requestSpec.getRequestParams());
        }
        if (Objects.nonNull(requestSpec.getQueryParams())) {
            requestAttachmentBuilder.setFormParams(requestSpec.getQueryParams());
        }
        if (Objects.nonNull(requestSpec.getMultiPartParams())) {
            requestAttachmentBuilder.setMultiPartParams(requestSpec.getMultiPartParams());
        }

        final HttpRequestAttachmentCustom requestAttachment = requestAttachmentBuilder.build();

        new DefaultAttachmentProcessor().addAttachment(
                requestAttachment,
                new FreemarkerAttachmentRenderer(requestTemplatePath)
        );

        final Response response = filterContext.next(requestSpec, responseSpec);
        if (Objects.isNull(responseAttachmentName)) {
            responseAttachmentName = "Response";
        }
        responseAttachmentName = response.getStatusLine();
        final HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder.create(responseAttachmentName)
                .setResponseCode(response.getStatusCode())
                .setHeaders(toMapConverter(response.getHeaders()))
                .setBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody()))
                .build();

        new DefaultAttachmentProcessor().addAttachment(
                responseAttachment,
                new FreemarkerAttachmentRenderer(responseTemplatePath)
        );

        return response;
    }


    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
