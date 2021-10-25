package ru.instamart.grpc.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.listener.ExecutionListener;
import ru.instamart.kraken.data.user.UserManager;

@Slf4j
public final class GrpcExecutionListener extends ExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public GrpcExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        // Тут может быть код для очистки окружения после прогона тестов
        log.debug("We create {} new users", UserManager.getUserDataList().size());
    }
}
