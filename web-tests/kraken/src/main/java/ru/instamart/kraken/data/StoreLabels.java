package ru.instamart.kraken.data;

public final class StoreLabels {

    public static StoreLabelData newStoreLabel() {
        String random = Generate.literalString(5);
        return new StoreLabelData(
                "Auto_storeLabel_title_" + random,
                "Auto_storeLabel_subtitle_" + random,
                "Auto_storeLabel_code_" + random,
                "Auto_storeLabel_icon_" + random,
                Generate.integer(1),
                Generate.integer(1)
        );
    }

    private StoreLabels() {
    }
}
