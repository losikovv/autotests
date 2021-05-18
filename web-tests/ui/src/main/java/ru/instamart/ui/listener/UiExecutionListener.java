package ru.instamart.ui.listener;

import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;
import ru.instamart.api.common.Specification;
import ru.instamart.kraken.helper.AllureHelper;
import ru.instamart.kraken.listener.ExecutionListener;
import ru.instamart.kraken.service.BannerService;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.kraken.util.Crypt;

import java.util.Map;

@Slf4j
public final class UiExecutionListener extends ExecutionListener {

    public UiExecutionListener() {
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
    }
}
