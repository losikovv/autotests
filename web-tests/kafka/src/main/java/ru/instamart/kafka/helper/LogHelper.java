package ru.instamart.kafka.helper;

import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import ru.instamart.kafka.emum.Pods;
import ru.instamart.kafka.emum.StatusOrder;
import ru.instamart.kafka.log_model.Response;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.common.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static ru.instamart.k8s.K8sConsumer.*;

public class LogHelper {

    @Step("Логи пода для nameSpace:{nameSpace} label={label} ")
    public List<String> getLogsPods(String nameSpace, String label, String filterData) {
        V1PodList podList = getPodList(nameSpace, label);

        List<String> logs = getLogs(podList.getItems().get(0), "app", Integer.MAX_VALUE);
        AtomicReference<String> attach = new AtomicReference<>();
        logs.forEach(item -> attach.set(attach + "\r\n" + item));
        Allure.addAttachment("Все логи", String.valueOf(attach));

        List<String> collect = logs.stream()
                .filter(str -> str.contains(filterData))
                .collect(Collectors.toList());
        Allure.addAttachment("Логи пода в которых есть упоминание UUID заказа " + filterData, collect.stream().collect(Collectors.joining("\n")));
        assertTrue(collect.size() > 0, "Логов по UUID ордера нет");
        return collect;
    }

    @Step("Проверка изменения логово о изменении заказа status = {status.value}")
    public List<String> checkChangeStatusOrder(List<String> logList, StatusOrder status) {
        List<String> collect = logList.stream()
                .filter(str -> str.contains(status.getValue()))
                .collect(Collectors.toList());
        Allure.addAttachment("Логи пода в которых статус: " + status.getValue(), collect.stream().collect(Collectors.joining("\n")));
        assertTrue(collect.size() > 0, "Логов по status ордера нет");
        return collect;
    }

    @Step("Парсинг лога в объект")
    public <T> T parseFirstLog(List<String> logs, Class<T> clazz) {
        return Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(logs.get(0)), clazz);
    }

    public List<String> getLogsPodAndCheck(Pods pods, String filterString, StatusOrder statusOrder) {
        var logsPodsList = getLogsPods(pods.getNameSpace(), pods.getLabel(), filterString);
        var filterList = checkChangeStatusOrder(logsPodsList, statusOrder);
        return filterList;
    }

    @Step("Ожидание появления из пода nameSpace: {pods.nameSpace} логов с {idMsg} и {waitString}")
    public List<String> awaitLogsPod(Pods pods, String idMsg, String waitString) {
        List<String> collect = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            var logsList = getLogsPods(pods.getNameSpace(), pods.getLabel(), idMsg);
            collect = logsList.stream()
                    .filter(str -> str.contains(waitString))
                    .collect(Collectors.toList());
            if(collect.size()>0) break;
            else
                ThreadUtil.simplyAwait(2);
        }
        return collect;
    }

    @Step("Все исполнители полученные от сервиса кандидатов")
    public List<String> getPerformersUUID(List<String> log) {
        List<String> buf=new ArrayList<>();
        Pattern pattern = Pattern.compile("/^[0-9A-F]{8}-[0-9A-F]{4}-[1][0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$/i");
        log.stream().forEach(
                item-> {
                    String response = Mapper.INSTANCE.jsonToObject(Objects.requireNonNull(item), Response.class).getResponse();
                    Matcher matcher = pattern.matcher(response);
                    while(matcher.find()){
                        buf.add(matcher.group());
                    }
                }
        );
        return buf;
    }
}
