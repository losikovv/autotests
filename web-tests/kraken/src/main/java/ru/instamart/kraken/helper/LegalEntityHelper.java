package ru.instamart.kraken.helper;

public class LegalEntityHelper {

    /**
     * Генератор ИНН юр лица
     *
     * @return ИНН
     */
    public static String generateInnUL() {
        //NNNN (4 знака): для российских организаций и физических лиц - код налогового органа, который присвоил ИНН
        Integer region = (int) (Math.random() * 92) + 1;
        Integer inspection = (int) (Math.random() * 99) + 1;

        // XXXXX (XXXXXX): для российской организации (физического лица) - порядковый номер записи о лице в территориальном разделе Единого государственного реестра налогоплательщиков (далее - ЕГРН) налогового органа, который присвоил ИНН, - 5 знаков - для организации (6 знаков - для физического лица)
        Integer numba = (int) (Math.random() * 99999) + 1;

        String result = String.format("%02d", region) + String.format("%02d", inspection) + String.format("%05d", numba);

        // C (CC) - контрольное число (1 знак - для организации, 2 знака - для физического лица), рассчитанное по специальному алгоритму, установленному Федеральной налоговой службой
        String kontr = String.valueOf(((
                2 * Character.digit(result.charAt(0), 10) + 4 * Character.digit(result.charAt(1), 10) + 10 * Character.digit(result.charAt(2), 10) +
                        3 * Character.digit(result.charAt(3), 10) + 5 * Character.digit(result.charAt(4), 10) + 9 * Character.digit(result.charAt(5), 10) +
                        4 * Character.digit(result.charAt(6), 10) + 6 * Character.digit(result.charAt(7), 10) + 8 * Character.digit(result.charAt(8), 10)
        ) % 11) % 10);
        return result + (kontr.equals("10") ? "0" : kontr);
    }

    /**
     * Генератор ИНН ФЛ
     */
    public static String generateInnFL() {
        // NNNN (4 знака): для российских организаций и физических лиц - код налогового органа, который присвоил ИНН
        Integer region = (int) (Math.random() * 92) + 1;
        Integer inspection = (int) (Math.random() * 48) + 52;

        // XXXXX (XXXXXX): для российской организации (физического лица) - порядковый номер записи о лице в территориальном разделе Единого государственного реестра налогоплательщиков (далее - ЕГРН) налогового органа, который присвоил ИНН, - 5 знаков - для организации (6 знаков - для физического лица)
        Integer numba = (int) (Math.random() * 999999) + 1;

        var result = String.format("%02d", region) + String.format("%02d", inspection) + String.format("%06d", numba);

        // C (CC) - контрольное число (1 знак - для организации, 2 знака - для физического лица), рассчитанное по специальному алгоритму, установленному Федеральной налоговой службой
        String kontr = String.valueOf(((
                7 * Character.digit(result.charAt(0), 10) + 2 * Character.digit(result.charAt(1), 10) + 4 * Character.digit(result.charAt(2), 10) +
                        10 * Character.digit(result.charAt(3), 10) + 3 * Character.digit(result.charAt(4), 10) + 5 * Character.digit(result.charAt(5), 10) +
                        9 * Character.digit(result.charAt(6), 10) + 4 * Character.digit(result.charAt(7), 10) + 6 * Character.digit(result.charAt(8), 10) +
                        8 * Character.digit(result.charAt(9), 10)
        ) % 11) % 10);

        if (kontr.equals("10")) {
            kontr = "0";
        }
        result = result + kontr;
        kontr = String.valueOf(((
                3 * Character.digit(result.charAt(0), 10) + 7 * Character.digit(result.charAt(1), 10) + 2 * Character.digit(result.charAt(2), 10) +
                        4 * Character.digit(result.charAt(3), 10) + 10 * Character.digit(result.charAt(4), 10) + 3 * Character.digit(result.charAt(5), 10) +
                        5 * Character.digit(result.charAt(6), 10) + 9 * Character.digit(result.charAt(7), 10) + 4 * Character.digit(result.charAt(8), 10) +
                        6 * Character.digit(result.charAt(9), 10) + 8 * Character.digit(result.charAt(10), 10)
        ) % 11) % 10);
        if (kontr.equals("10")) {
            kontr = "0";
        }
        return result + kontr;
    }

    public static String generateKpp() {
        //NNNN (4 знака) - код налогового органа, который осуществил постановку на учет организации по месту ее нахождения, месту нахождения обособленного подразделения организации, расположенного на территории Российской Федерации, месту нахождения принадлежащих ей недвижимого имущества и транспортных средств, а также по иным основаниям, предусмотренным Налоговым кодексом Российской Федерации
        Integer region = (int) (Math.random() * 92) + 1;
        Integer inspection = (int) (Math.random() * 99) + 1;
        //PP (2 знака) - причина постановки на учет (учета сведений). Символ P представляет собой цифру или заглавную букву латинского алфавита от A до Z.
        //	Числовое значение символов PP может принимать значение:
        //	- для российской организации от 01 до 50 (01 - по месту ее нахождения);
        //	- для иностранной организации от 51 до 99;
        String prichina = String.valueOf((int) (Math.random() * 4) + 1);
        switch (prichina) {
            case "1":
                prichina = "01";
                break;
            case "2":
                prichina = "43";
                break;
            case "3":
                prichina = "44";
                break;
            case "4":
                prichina = "45";
                break;
            default:
                prichina = "01";
                break;
        }
        Integer numba = (int) (Math.random() * 999) + 1;
        return String.format("%02d", region) + String.format("%02d", inspection) + prichina + String.format("%03d", numba);
    }

    public static String generateRS() {
        return "40702" //балансовый счет
                + "810" //код валюты (810 для рубля)
                + "675" // контрольная цифра
                + String.format("%09o", (long) (Math.random() * 99_999_999L + 1));
    }
}
