package ru.instamart.kraken.data;

public final class JuridicalData {

    public static Juridical juridical() {
        return new Juridical(
                "ЗАО \"Лидер-" + Generate.digitalString(4) + "\"",
                Generate.string(8),
                Generate.generateINN(10),
                Generate.digitalString(9),
                Generate.digitalString(20),
                Generate.digitalString(9),
                Generate.string(8),
                Generate.digitalString(20)
        );
    }
}
