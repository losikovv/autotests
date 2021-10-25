package ru.instamart.kraken.data.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRoles {

    /**
     *  Список ролей доступен по  https://instamart.atlassian.net/wiki/spaces/FMCG/pages/2097774867/feature-stage
     */
    ADMIN(1, "admin"),
    USER(2, "user"),
    COLLECTOR(3, "collector"),
    KUPISLOVA(4, "kupislova"),
    CONTENT(5,"content"),
    MARKETING(6,"marketing"),
    B2B_LEADER(7,"b2b_leader"),
    OPERATIONS(8,"operations"),
    SHOPPER_BACKEND(9,"shopper_backend"),
    MARKETING_BONUS_MANAGER(10, "marketing_bonus_manager"),
    CALL_CENTER(11,"call_center"),
    B2B_MANAGER(12, "b2b_manager"),
    EXTERNAL_CALL_CENTER(13, "external_call_center");

    Integer id;
    String role;
}
