package ru.instamart.api.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;
import ru.instamart.api.common.Specification;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.listener.ExecutionListener;
import ru.instamart.kraken.service.BannerService;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.kraken.util.Crypt;

import java.util.Map;

import static ru.instamart.kraken.helper.AllureHelper.allureEnvironmentWriter;

@Slf4j
public final class ApiExecutionListener extends ExecutionListener {

    // Нужно инициализировать в конструкторе, что бы гарантировать наличие конфигов до запуска чего либо
    public ApiExecutionListener() {
        super();
    }

    @Override
    public void onExecutionStart() {
        super.onExecutionStart();
        log.info("Init rest specification");
        Specification.INSTANCE.initSpec();
    }

    @Override
    public void onExecutionFinish() {
        // Тут может быть код для очистки окружения после прогона тестов
        log.info("We create {} new users", UserManager.getUserDataList().size());
        log.info("We have {} open sessions", SessionFactory.getSessionMap().size());
    }
}
