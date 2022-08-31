package ru.instamart.kraken.data;

public final class VehiclesData {

    public static Vehicles car() {
        return Vehicles.builder()
                .kind(CarType.car)
                .model(Generate.literalString(7))
                .number(Generate.generateGosNumber(22))
                .volume(Generate.digitalString(1))
                .build();
    }

    public static Vehicles truck() {
        return Vehicles.builder()
                .kind(CarType.truck)
                .model(Generate.literalString(7))
                .number(Generate.generateGosNumber(22))
                .volume(Generate.digitalString(1))
                .build();
    }

    public static Vehicles bicycle() {
        return Vehicles.builder()
                .kind(CarType.bicycle)
                .model(Generate.literalString(7))
                .number(Generate.literalString(5))
                .volume(Generate.digitalString(1))
                .build();
    }

    public static Vehicles scooter() {
        return Vehicles.builder()
                .kind(CarType.scooter)
                .model(Generate.literalString(7))
                .number(Generate.literalString(5))
                .volume(Generate.digitalString(1))
                .build();
    }

    public static Vehicles electricbicycle() {
        return Vehicles.builder()
                .kind(CarType.electricbicycle)
                .model(Generate.literalString(7))
                .number(Generate.literalString(5))
                .volume(Generate.digitalString(1))
                .build();
    }

    public static Vehicles other() {
        return Vehicles.builder()
                .kind(CarType.other_transport)
                .model(Generate.literalString(7))
                .number(Generate.literalString(5))
                .volume(Generate.digitalString(1))
                .build();
    }
}
