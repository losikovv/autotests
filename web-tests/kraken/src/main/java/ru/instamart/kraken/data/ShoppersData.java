package ru.instamart.kraken.data;

import ru.instamart.kraken.data.enums.ShoppersRole;

import java.util.Set;

public final class ShoppersData {

    public static Shoppers shoppers() {
        return Shoppers.builder()
                .name(Generate.literalString(7))
                .phone(Generate.phone())
                .login(Generate.literalString(6))
                .currentShop("METRO, Апаринки")
                .roles(Set.of(Generate.randomEnum(ShoppersRole.class)))
                .inn(Generate.generateINN(10))
                .password(Generate.literalString(7))
                .build();
    }

    public static Shoppers courier() {
        return Shoppers.builder()
                .name(Generate.literalString(7))
                .phone(Generate.phone())
                .login(Generate.literalString(6))
                .currentShop("METRO, Апаринки")
                .roles(Set.of(ShoppersRole.COURIER))
                .inn(Generate.generateINN(10))
                .password(Generate.literalString(7))
                .build();
    }

    private ShoppersData() {}
}
