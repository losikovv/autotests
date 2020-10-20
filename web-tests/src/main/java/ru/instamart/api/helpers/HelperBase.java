package instamart.api.helpers;

import java.util.HashMap;
import java.util.List;

public class HelperBase {

    /**
     * Зеленый текст
     */
    static void printSuccess(String string) {
        System.out.println(greenText(string));
    }

    static String greenText(String string) {
        return "\u001b[32m" + string + "\u001B[0m";
    }

    /**
     * Красный текст
     */
    static void printError(String string) {
        System.err.println(string);
    }

    /**
     * Определяем, есть ли в списке указанный инт
     */
    public static boolean listContainsInt(int integer, List<Integer> list) {
        for (int intFromList : list)
            if (integer == intFromList) {
                return true;
            }
        return false;
    }

    /**
     * Определяем, есть ли в хэшэ указанная пара из инта и стринга
     */
    public static boolean hashMapContainsIntAndString(int integer, String string, HashMap<Integer, String> hashMap) {
        if (hashMap.containsKey(integer)) {
            return hashMap.get(integer).contains(string);
        }
        return false;
    }


}
