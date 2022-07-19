package ru.instamart.kafka.helper;

import estimator.Estimator;
import io.kubernetes.client.openapi.models.V1PodList;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.instamart.kafka.enums.Pods;
import ru.instamart.kafka.enums.StatusOrder;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.common.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static ru.instamart.k8s.K8sConsumer.getLogs;
import static ru.instamart.k8s.K8sConsumer.getPodList;
import static ru.instamart.kafka.helper.ProtoHelper.parseResponseToProto;
import static ru.instamart.kraken.helper.UUIDHelper.getFirstUUID;

@Slf4j
public class LogHelper {

    @Step("Логи пода для nameSpace:{nameSpace} label={label} ")
    public List<String> getLogsPods(String nameSpace, String label, String filterData) {
        V1PodList podList = getPodList(nameSpace, label);

        List<String> logs = getLogs(podList.getItems().get(0), "app", 0);
        Allure.addAttachment("Все логи", String.join("\r\n", logs));

        List<String> collect = logs.stream()
                .filter(str -> str.contains(filterData))
                .collect(Collectors.toList());
        Allure.addAttachment("Логи пода в которых есть упоминание UUID:  " + filterData, collect.stream().collect(Collectors.joining("\n")));
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
        for (int i = 0; i < 20; i++) {
            var logsList = getLogsPods(pods.getNameSpace(), pods.getLabel(), idMsg);
            collect = logsList.stream()
                    .filter(str -> StringUtils.containsIgnoreCase(str, waitString))
                    .collect(Collectors.toList());
            if (collect.size() > 0) break;
            else
                ThreadUtil.simplyAwait(2);
        }
        return collect;
    }

    @Step("Все исполнители полученные от сервиса кандидатов")
    public List<String> getPerformersUUID(List<String> logs) {
        List<String> buf = new ArrayList<>();
        logs.stream().forEach(
                item -> {
                    String uuid = getFirstUUID(item);
                    buf.add(uuid);
                }
        );
        Allure.addAttachment("Логи с исполнителями", buf.stream().collect(Collectors.joining("\n")));
        return buf;
    }
}
