package ru.instamart.kraken.helper;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

@Slf4j
public class PhoneNumberHelper {

    private static String phoneMask = "### ###-##-##";

    public static String getHumanPhoneNumber(@NonNull String phoneNumber) {
        MaskFormatter maskFormatter;
        try {
            maskFormatter = new MaskFormatter(phoneMask);
            maskFormatter.setValueContainsLiteralCharacters(false);
            return "+7 " + maskFormatter.valueToString(phoneNumber);

        } catch (ParseException e) {
            log.error("Ошибка валидации номера телефона. ", e);
            e.printStackTrace();
        }
        return null;
    }

}
