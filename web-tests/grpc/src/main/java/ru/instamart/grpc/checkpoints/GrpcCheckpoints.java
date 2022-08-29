package ru.instamart.grpc.checkpoints;

import io.qameta.allure.Step;

import java.util.Objects;

import static org.testng.Assert.*;

public class GrpcCheckpoints {

    @Step("Проверка, что id больше 0")
    public void id(int id) {
        assertTrue(id > 0, "Вернулся id меньший или равный 0");
    }

    @Step("Сравнение двух объектов")
    public <T> void equals(T actual, T expected) {
        assertEquals(actual, expected, String.format("Объекты %s и %s не совпадают",
                Objects.isNull(actual) ? "null" : actual.getClass().getSimpleName(),
                Objects.isNull(expected) ? "null" : expected.getClass().getSimpleName()));
    }

    @Step("Проверка, что поле не null")
    public <T> void notNull(T field) {
        assertNotNull(field, "Поле равно null");
    }

    @Step("Проверка, что значение больше 0")
    public void value(float value) {
        assertTrue(value > 0, "Вернулось значение меньшее или равное 0");
    }
}
