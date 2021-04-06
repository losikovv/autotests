package instamart.api.requests.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import instamart.api.endpoints.ApiV2EndPoints;
import instamart.core.service.MapperService;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class AddressesRequest {

    @Step("{method} /" + ApiV2EndPoints.ADDRESSES)
    public static Response GET() {
        return givenWithAuthApiV2()
                .get(ApiV2EndPoints.ADDRESSES);
    }

    @Step("{method} /" + ApiV2EndPoints.ADDRESSES)
    public static Response POST(final Addresses addresses) {
        return givenWithAuthApiV2()
                .formParams(MapperService.INSTANCE.objectToMap(addresses))
                .post(ApiV2EndPoints.ADDRESSES);
    }

    @Step("{method} /" + ApiV2EndPoints.Addresses.ID)
    public static Response DELETE(final int id) {
        return givenWithAuthApiV2()
                .delete(ApiV2EndPoints.Addresses.ID, id);
    }

    @Step("{method} /" + ApiV2EndPoints.Addresses.ID)
    public static Response PUT(final int id, final Addresses addresses) {
        return givenWithAuthApiV2()
                .formParams(MapperService.INSTANCE.objectToMap(addresses))
                .put(ApiV2EndPoints.Addresses.ID, id);
    }

    /**
     * address[first_name]	-	Имя пользователя/контактого лица
     * address[last_name]	-	Фамилия пользоватя/контактного лица
     * address[full_address]	Да (временно)	Полный адрес (в ближайшие дни уберем это поле)
     * address[city]	Да	Город
     * address[street]	Да	Улица
     * address[building]	Да	Номер дома
     * address[block]	-	Строение
     * address[entrance]	-	Подъезд
     * address[floor]	-	Этаж
     * address[apartment]	-	Номер квартиры
     * address[comments]	-	Комментарий к адресу
     * address[lon]	-	Долгота
     * address[lat]	-	Широта
     * address[door_phone]	-	Номер домофона
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class Addresses {

        @JsonProperty(value = "address[first_name]")
        private final String firstName;
        @JsonProperty(value = "address[last_name]")
        private final String lastName;
        @JsonProperty(value = "address[full_address]")
        private final String fullAddress;
        @JsonProperty(value = "address[city]")
        private final String city;
        @JsonProperty(value = "address[street]")
        private final String street;
        @JsonProperty(value = "address[building]")
        private final String building;
        @JsonProperty(value = "address[block]")
        private final String block;
        @JsonProperty(value = "address[entrance]")
        private final String entrance;
        @JsonProperty(value = "address[floor]")
        private final String floor;
        @JsonProperty(value = "address[apartment]")
        private final String apartment;
        @JsonProperty(value = "address[comments]")
        private final String comments;
        @JsonProperty(value = "address[lon]")
        private final String lon;
        @JsonProperty(value = "address[lat]")
        private final String lat;
        @JsonProperty(value = "address[door_phone]")
        private final String doorPhone;
    }
}
