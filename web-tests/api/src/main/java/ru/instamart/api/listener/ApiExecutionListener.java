package ru.instamart.api.listener;

import io.restassured.http.Method;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.common.Specification;
import ru.instamart.api.factory.ArtifactFactory;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.listener.ExecutionListener;

import java.util.HashSet;

import static ru.instamart.api.factory.ArtifactFactory.filterCollect;

@Slf4j
public class ApiExecutionListener extends ExecutionListener {

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

        var list = ArtifactFactory.getArtifactList();
        log.debug("list size {}", list.size());
        filterCollect(list, Method.POST);
        filterCollect(list, Method.PUT);
        filterCollect(list, Method.PATCH);
        HashSet<String> orderSet = new HashSet<>(ArtifactFactory.getOrderList());
        log.debug("Order list using in test: ");
        orderSet.stream().forEach(log::debug);
        HashSet<String> shippingSet = new HashSet<>(ArtifactFactory.getShippingList());
        log.debug("Shipping list using in test: ");
        shippingSet.stream().forEach(log::debug);
    }
}
