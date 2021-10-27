package ru.instamart.kraken.data;

import lombok.Data;

@Data
public final class Juridical {

    private final String juridicalName;
    private final String juridicalAddress;
    private final String inn;
    private final String kpp;
    private final String accountNumber;
    private final String bik;
    private final String bankName;
    private final String correspondentAccountNumber;
}
