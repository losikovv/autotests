package ru.instamart.reforged.core.listener.allure;

import java.util.UUID;

public final class Current {

    private final String uuid;
    private CurrentStage currentStage;

    public Current() {
        this.uuid = UUID.randomUUID().toString();
        this.currentStage = CurrentStage.BEFORE;
    }

    public void test() {
        this.currentStage = CurrentStage.TEST;
    }

    public void after() {
        this.currentStage = CurrentStage.AFTER;
    }

    public boolean isStarted() {
        return this.currentStage != CurrentStage.BEFORE;
    }

    public boolean isAfter() {
        return this.currentStage == CurrentStage.AFTER;
    }

    public String getUuid() {
        return uuid;
    }
}
