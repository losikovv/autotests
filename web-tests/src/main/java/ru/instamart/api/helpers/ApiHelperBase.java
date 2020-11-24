package instamart.api.helpers;

import java.util.HashMap;
import java.util.List;

import static instamart.core.helpers.HelperBase.verboseMessage;

abstract class ApiHelperBase {

    /**
     * Зеленый текст
     */
    void printSuccess(String string) {
        verboseMessage(greenText(string));
    }

    String greenText(String string) {
        return "\u001b[32m" + string + "\u001B[0m";
    }

    /**
     * Красный текст
     */
    void printError(String string) {
        verboseMessage(redText(string));
    }

    private String redText(String string) {
        return "\u001b[31m" + string + "\u001B[0m";
    }

    /**
     * Определяем, есть ли в списке указанный инт
     */
    public boolean listContainsInt(int integer, List<Integer> list) {
        for (int intFromList : list)
            if (integer == intFromList) {
                return true;
            }
        return false;
    }

    /**
     * Определяем, есть ли в хэшэ указанная пара из инта и стринга
     */
    public boolean hashMapContainsIntAndString(int integer, String string, HashMap<Integer, String> hashMap) {
        if (hashMap.containsKey(integer)) {
            return hashMap.get(integer).contains(string);
        }
        return false;
    }
}
