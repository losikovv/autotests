package ru.instamart.reforged.stf.page;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.reforged.stf.block.AuthoredHeader;

@Slf4j
public final class Shop implements Page {

    private final AuthoredHeader header = new AuthoredHeader();

    public AuthoredHeader useHeader() {
        return header;
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
