package ru.instamart.kraken.data;

import lombok.Builder;
import lombok.Data;
import ru.instamart.kraken.data.enums.ShoppersRole;
import ru.instamart.kraken.util.StringUtil;

import java.util.Set;

@Data
@Builder
public final class Shoppers {

    private String name;
    private String phone;
    private String login;
    private String currentShop;
    private Set<ShoppersRole> roles;
    private String inn;
    private String password;

    public String getRolesName() {
        return StringUtil.arrayToString(roles.stream().map(ShoppersRole::getData).toArray(String[]::new));
    }

    public Integer[] getRolesId() {
        return roles.stream().map(ShoppersRole::getValue).toArray(Integer[]::new);
    }
}
