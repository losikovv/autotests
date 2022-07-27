package ru.instamart.api.request.webhook_site;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.*;
import ru.instamart.api.endpoint.WebhookSiteEndpoints;
import ru.instamart.api.request.WebhookSiteBase;
import ru.sbermarket.common.Mapper;

import java.util.Objects;

public class TokenRequest extends WebhookSiteBase {

    @Step("{method} /" + WebhookSiteEndpoints.TOKEN)
    public static Response POST() {
        return givenWithSpec()
                .post(WebhookSiteEndpoints.TOKEN);
    }

    @Step("{method} /" + WebhookSiteEndpoints.Token.REQUESTS)
    public static Response PUT(final String token, final TokenRequest.TokenRequestData params) {
        RequestSpecification requestSpecification = givenWithSpec();
        if (Objects.nonNull(params)) {
            requestSpecification.formParams(Mapper.INSTANCE.objectToMap(params));
        }
        return requestSpecification
                .put(WebhookSiteEndpoints.Token.TOKEN, token);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class TokenRequestData {
        /**
         * параметры задают ответ URL.
         * "default_status": 200
         */
        @JsonProperty("default_status")
        private int defaultStatus;
        /**
         * параметры задают ответ URL.
         * "default_content": "Hello world!"
         */
        @JsonProperty("default_content")
        private String defaultContent;
        /**
         * параметры задают ответ URL.
         * "default_content_type": "text/html"
         */
        @JsonProperty("default_content_type")
        private String defaultContentType;
        /**
         * ждет количество секунд, прежде чем вернуть ответ (предназначен для тестирования тайм-аутов)
         */
        private Integer timeout;
        /**
         * если установлено значение true, токен будет автоматически удален в течение 7 дней без активности,
         * даже если токен создается как пользователь Pro. Например, если вы используете токены для автоматического
         * тестирования, вы можете включить это, чтобы не заполнять свой профиль пользователя.
         */
        private Boolean expiry;
        /**
         * если установлено значение true, к запросу будут добавлены заголовки CORS, поэтому браузеры будут отправлять
         * междоменные запросы на URL-адрес.
         */
        private Boolean cors;
        /**
         * позволяет установить псевдоним токена.
         */
        private String alias;
        /**
         * указывает, включены ли настраиваемые действия и выполняются ли они для каждого
         * запроса/электронного письма (true) или отключены (false).
         */
        private Boolean actions;
        /**
         * указывает UUID токена (или псевдоним), который будет действовать как шаблон для нового токена.
         * Если указано, такие параметры, как содержимое по умолчанию, тайм-аут, пароль, а также настраиваемые действия,
         * копируются в новый токен.
         */
        @JsonProperty("clone_from")
        private String cloneFrom;
        /**
         * указывает, к какому идентификатору группы следует добавить токен.
         */
        @JsonProperty("group_id")
        private String groupId;
    }
}
