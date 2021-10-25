package ru.instamart.api.listener;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.common.Specification;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.listener.ExecutionListener;

@Slf4j
public final class ApiExecutionListener extends ExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ApiExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        log.debug("Init rest specification");
        Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {
        super.onExecutionFinish();
        log.debug("We have {} open sessions", SessionFactory.getSessionMap().size());
    }
}
