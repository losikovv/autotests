package ru.instamart.kraken.helper;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class PhoneNumberHelper {

    private static String phoneMask= "### ###-##-##";


    public static String getHumanPhoneNumber(String phoneNumber){
        MaskFormatter maskFormatter;
        try {
            maskFormatter = new MaskFormatter(phoneMask);
            maskFormatter.setValueContainsLiteralCharacters(false);
            return "+7 "+maskFormatter.valueToString(phoneNumber) ;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
