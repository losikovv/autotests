package instamart.api.requests.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import instamart.api.endpoints.ApiV2EndPoints;
import instamart.core.service.MapperService;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class AddressesRequest {

    @Step("{method} /" + ApiV2EndPoints.ADDRESSES)
    public static Response POST(final Addresses addresses) {
        return givenWithAuthApiV2()
                .formParams(MapperService.INSTANCE.objectToMap(addresses))
                .post(ApiV2EndPoints.ADDRESSES);
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
    public static final class Addresses {

        @JsonProperty(value = "address[first_name]")
        private String firstName;
        @JsonProperty(value = "address[last_name]")
        private String lastName;
        @JsonProperty(value = "address[full_address]")
        private String fullAddress;
        @JsonProperty(value = "address[city]")
        private String city;
        @JsonProperty(value = "address[street]")
        private String street;
        @JsonProperty(value = "address[building]")
        private String building;
        @JsonProperty(value = "address[block]")
        private String block;
        @JsonProperty(value = "address[entrance]")
        private String entrance;
        @JsonProperty(value = "address[floor]")
        private String floor;
        @JsonProperty(value = "address[apartment]")
        private String apartment;
        @JsonProperty(value = "address[comments]")
        private String comments;
        @JsonProperty(value = "address[lon]")
        private String lon;
        @JsonProperty(value = "address[lat]")
        private String lat;
        @JsonProperty(value = "address[door_phone]")
        private String doorPhone;

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public String getCity() {
            return city;
        }

        public String getStreet() {
            return street;
        }

        public String getBuilding() {
            return building;
        }

        public String getBlock() {
            return block;
        }

        public String getEntrance() {
            return entrance;
        }

        public String getFloor() {
            return floor;
        }

        public String getApartment() {
            return apartment;
        }

        public String getComments() {
            return comments;
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }

        public String getDoorPhone() {
            return doorPhone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Addresses addresses = (Addresses) o;
            return Objects.equal(firstName, addresses.firstName) && Objects.equal(lastName, addresses.lastName) && Objects.equal(fullAddress, addresses.fullAddress) && Objects.equal(city, addresses.city) && Objects.equal(street, addresses.street) && Objects.equal(building, addresses.building) && Objects.equal(block, addresses.block) && Objects.equal(entrance, addresses.entrance) && Objects.equal(floor, addresses.floor) && Objects.equal(apartment, addresses.apartment) && Objects.equal(comments, addresses.comments) && Objects.equal(lon, addresses.lon) && Objects.equal(lat, addresses.lat) && Objects.equal(doorPhone, addresses.doorPhone);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(firstName, lastName, fullAddress, city, street, building, block, entrance, floor, apartment, comments, lon, lat, doorPhone);
        }

        @Override
        public String toString() {
            return "Addresses{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", fullAddress='" + fullAddress + '\'' +
                    ", city='" + city + '\'' +
                    ", street='" + street + '\'' +
                    ", building='" + building + '\'' +
                    ", block='" + block + '\'' +
                    ", entrance='" + entrance + '\'' +
                    ", floor='" + floor + '\'' +
                    ", apartment='" + apartment + '\'' +
                    ", comments='" + comments + '\'' +
                    ", lon='" + lon + '\'' +
                    ", lat='" + lat + '\'' +
                    ", doorPhone='" + doorPhone + '\'' +
                    '}';
        }

        public static final class AddressesBuilder {
            private String firstName;
            private String lastName;
            private String fullAddress;
            private String city;
            private String street;
            private String building;
            private String block;
            private String entrance;
            private String floor;
            private String apartment;
            private String comments;
            private String lon;
            private String lat;
            private String doorPhone;

            private AddressesBuilder() {
            }

            public static AddressesBuilder anAddresses() {
                return new AddressesBuilder();
            }

            public AddressesBuilder withFirstName(String firstName) {
                this.firstName = firstName;
                return this;
            }

            public AddressesBuilder withLastName(String lastName) {
                this.lastName = lastName;
                return this;
            }

            public AddressesBuilder withFullAddress(String fullAddress) {
                this.fullAddress = fullAddress;
                return this;
            }

            public AddressesBuilder withCity(String city) {
                this.city = city;
                return this;
            }

            public AddressesBuilder withStreet(String street) {
                this.street = street;
                return this;
            }

            public AddressesBuilder withBuilding(String building) {
                this.building = building;
                return this;
            }

            public AddressesBuilder withBlock(String block) {
                this.block = block;
                return this;
            }

            public AddressesBuilder withEntrance(String entrance) {
                this.entrance = entrance;
                return this;
            }

            public AddressesBuilder withFloor(String floor) {
                this.floor = floor;
                return this;
            }

            public AddressesBuilder withApartment(String apartment) {
                this.apartment = apartment;
                return this;
            }

            public AddressesBuilder withComments(String comments) {
                this.comments = comments;
                return this;
            }

            public AddressesBuilder withLon(String lon) {
                this.lon = lon;
                return this;
            }

            public AddressesBuilder withLat(String lat) {
                this.lat = lat;
                return this;
            }

            public AddressesBuilder withDoorPhone(String doorPhone) {
                this.doorPhone = doorPhone;
                return this;
            }

            public Addresses build() {
                final Addresses addresses = new Addresses();
                addresses.lastName = this.lastName;
                addresses.lon = this.lon;
                addresses.apartment = this.apartment;
                addresses.comments = this.comments;
                addresses.firstName = this.firstName;
                addresses.city = this.city;
                addresses.building = this.building;
                addresses.entrance = this.entrance;
                addresses.lat = this.lat;
                addresses.block = this.block;
                addresses.fullAddress = this.fullAddress;
                addresses.doorPhone = this.doorPhone;
                addresses.street = this.street;
                addresses.floor = this.floor;
                return addresses;
            }
        }
    }
}
