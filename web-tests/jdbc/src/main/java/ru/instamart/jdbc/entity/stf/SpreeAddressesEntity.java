package ru.instamart.jdbc.entity.stf;

import lombok.Data;

@Data
public class SpreeAddressesEntity {
    private Long id;
    private String firstname;
    private String lastname;
    private String street;
    private String address2;
    private String city;
    private String zipcode;
    private String phone;
    private String company;
    private Long countryId;
    private String createdAt;
    private String updatedAt;
    private Long userId;
    private String deletedAt;
    private String building;
    private String block;
    private String apartment;
    private String floor;
    private String comments;
    private String businessCenter;
    private String elevator;
    private String pass;
    private String fullAddress;
    private String shipmentComment;
    private Double lat;
    private Double lon;
    private String cityKladrId;
    private String streetKladrId;
    private String buildingKladrId;
    private String kind;
    private String entrance;
    private String region;
    private String area;
    private String settlement;
    private String coordinates;
    private String doorPhone;
    private Long createdById;
    private Integer deliveryToDoor;
}
