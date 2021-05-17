package ru.instamart.api.model.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RouteV1 extends BaseObject {
    private Integer id;
    private Integer position;
    private String state;
    private String humanStateName;
    private String planLoadingStartsAt;
    private String factLoadingStartsAt;
    private String planDepartureAt;
    private String factDepartureAt;
    private String planArrivalAt;
    private String factArrivalAt;
    private Object planDistance;
    private Integer shipmentsCount;
    private Integer assembledShipmentsCount;
    private Boolean isVisible;
    private Boolean lockedForAutomaticChange;
    private String warningMessage;
    private Boolean manualManagementAvailable;
    private Boolean destroyAvailable;
    private List<RoutePointV1> routePoints = null;
    private DriverV1 driver;
}
