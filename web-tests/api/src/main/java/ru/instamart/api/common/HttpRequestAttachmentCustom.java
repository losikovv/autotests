package ru.instamart.api.common;

import io.qameta.allure.attachment.AttachmentData;
import io.restassured.specification.MultiPartSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class HttpRequestAttachmentCustom implements AttachmentData {

    private final String name;

    private final String url;

    private final String method;

    private final String body;

    private final String curl;

    private final Map<String, Object> formParam;

    private final List<MultiPartSpecification> multiPartParam;

    private final Map<String, String> headers;

    private final Map<String, String> cookies;

    @Override
    public String getName() {
        return name;
    }

    public static final class Builder {

        private final String name;

        private final String url;
        private final Map<String, Object> formParam = new HashMap<>();
        private final Map<String, String> headers = new HashMap<>();
        private final Map<String, String> cookies = new HashMap<>();
        private final List<MultiPartSpecification> multiPartParam = new Stack<>();
        private String method;
        private String body;

        public Builder(final String name, final String url) {
            Objects.requireNonNull(name, "Name must not be null value");
            Objects.requireNonNull(url, "Url must not be null value");
            this.name = name;
            this.url = url;
        }

        public static Builder create(final String attachmentName, final String url) {
            return new Builder(attachmentName, url);
        }

        private static void appendHeader(final StringBuilder builder, final String key, final String value) {
            builder.append(" -H '")
                    .append(key)
                    .append(": ")
                    .append(value)
                    .append('\'');
        }

        private static void appendCookie(final StringBuilder builder, final String key, final String value) {
            builder.append(" -b '")
                    .append(key)
                    .append('=')
                    .append(value)
                    .append('\'');
        }

        private static void appendFormParam(final StringBuilder builder, final String key, final Object value) {
            builder.append(" -F '")
                    .append(key)
                    .append('=')
                    .append(value.toString())
                    .append('\'');
        }

        public Builder setMethod(final String method) {
            Objects.requireNonNull(method, "Method must not be null value");
            this.method = method;
            return this;
        }

        public Builder setHeader(final String name, final String value) {
            Objects.requireNonNull(name, "Header name must not be null value");
            Objects.requireNonNull(value, "Header value must not be null value");
            this.headers.put(name, value);
            return this;
        }

        public Builder setHeaders(final Map<String, String> headers) {
            Objects.requireNonNull(headers, "Headers must not be null value");
            this.headers.putAll(headers);
            return this;
        }

        public Builder setCookie(final String name, final String value) {
            Objects.requireNonNull(name, "Cookie name must not be null value");
            Objects.requireNonNull(value, "Cookie value must not be null value");
            this.cookies.put(name, value);
            return this;
        }

        public Builder setCookies(final Map<String, String> cookies) {
            Objects.requireNonNull(cookies, "Cookies must not be null value");
            this.cookies.putAll(cookies);
            return this;
        }

        public Builder setBody(final String body) {
            Objects.requireNonNull(body, "Body should not be null value");
            this.body = body;
            return this;
        }

        public Builder setFormParams(final String name, final String value) {
            Objects.requireNonNull(name, "Cookie name must not be null value");
            Objects.requireNonNull(value, "Cookie value must not be null value");
            this.formParam.put(name, value);
            return this;
        }

        public Builder setFormParams(final Map<String, String> formParam) {
            Objects.requireNonNull(formParam, "Body should not be null value");
            this.formParam.putAll(formParam);
            return this;
        }

        public Builder setMultiPartParams(final List<MultiPartSpecification> multiPart) {
            Objects.requireNonNull(multiPart, "Body should not be null value");
            this.multiPartParam.addAll(multiPart);
            return this;
        }

        public HttpRequestAttachmentCustom build() {
            return new HttpRequestAttachmentCustom(name, url, method, body, getCurl(), formParam, multiPartParam, headers, cookies);
        }

        private String getCurl() {
            final StringBuilder builder = new StringBuilder("curl -v");
            if (Objects.nonNull(method)) {
                builder.append(" -X ").append(method);
            }
            builder.append(" '").append(url).append('\'');
            headers.forEach((key, value) -> appendHeader(builder, key, value));
            cookies.forEach((key, value) -> appendCookie(builder, key, value));

            if (Objects.nonNull(body)) {
                builder.append(" -d '").append(body).append('\'');
            }

            if (Objects.nonNull(formParam)) {
                formParam.forEach((key, value) -> {
                    if (value instanceof Collection) {
                        ((Collection<?>) value).stream()
                                .forEach(val -> appendFormParam(builder, key, val));
                    } else {
                        appendFormParam(builder, key, value);
                    }
                });
            }
            return builder.toString();
        }

    }
}
