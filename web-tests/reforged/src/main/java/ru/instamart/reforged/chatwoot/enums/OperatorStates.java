package ru.instamart.reforged.chatwoot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OperatorStates {
    OFFLINE("Оффлайн", "rgb(217, 217, 217)"),
    ONLINE("В сети", "rgb(160, 217, 17)"),
    INTERNSHIP("Стажировка", "rgb(235, 47, 150)"),
    HOTLINE("Горячая линия","rgb(215, 80, 0)"),
    WAITING("Ожидание", "rgb(250, 219, 20)"),
    DINNER("Обед", "rgb(114, 46, 209)"),
    TECH_BREAK("Тех. перерыв", "rgb(47, 84, 235)");

    private final String name;
    private final String colorCode;
}
