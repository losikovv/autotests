package ru.instamart.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.ExecutionListener;

@Slf4j
public final class KafkaExecutionListener extends ExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public KafkaExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        log.debug("Init rest specification");
       // Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        // Тут может быть код для очистки окружения после прогона тестов
        log.debug("We create {} new users", UserManager.userDataCount());
    }
}
