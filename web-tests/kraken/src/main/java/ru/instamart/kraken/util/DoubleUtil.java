package ru.instamart.kraken.util;

import java.util.Random;

public final class DoubleUtil {

    private static final Random r = new Random();

    private DoubleUtil() {
    }

    //принимает мин и макс значение для генерации double со знаками после запятой
    public static double getRandomDoubleBetweenRange(double min, double max){
        return min + (max - min) * r.nextDouble();
    }

    //принимает double для обрезания его на количество places знаков после запятой
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
