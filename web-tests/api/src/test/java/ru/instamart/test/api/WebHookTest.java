package ru.instamart.test.api;

import io.qameta.allure.Epic;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.helper.WebHookSiteHelper;
import ru.instamart.api.request.webhook_site.TokenRequest;
import ru.instamart.api.response.webhook_site.RequestsResponse;
import ru.instamart.jdbc.dao.stf.ApiClientHooksDao;
import ru.instamart.kraken.util.ThreadUtil;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.helper.K8sHelper.sendMessageFromWebhook;


@Epic("Webhook")
public class WebHookTest {

    @BeforeClass(alwaysRun = true)
    public void before() {
        //Инициализация
        String site = WebHookSiteHelper.getInstance().getSite();
        //Обновляем через БД
        ApiClientHooksDao.INSTANCE.updateUrlHook(7L, site);
        //или добавляем через консоль
        //addWebhookUrl("metro_marketplace", site);

        //Конфиг для webhook сервиса. Говорим что сервис должен вернуть 422
        TokenRequest.TokenRequestData requestData = TokenRequest.TokenRequestData.builder()
                .defaultStatus(422)
                .build();
        WebHookSiteHelper.getInstance().updateServiceConfig(requestData);
    }

    @Test(groups = {"api-instamart-regress"}, description = "Проверка webhook")
    public void webHookTest() {
        //Отправка сообщения
        sendMessageFromWebhook("metro_marketplace", "Test message");
        ThreadUtil.simplyAwait(10);
        //Получаем все сообщения. Так же можно в метод передавать WebhookSiteRequests.RequestParams с параметрами поиска
        RequestsResponse allRequest = WebHookSiteHelper.getInstance().getAllRequest(null);
        assertTrue(allRequest.getData().size() > 0, "Тело вернулось пустым");
    }

}
