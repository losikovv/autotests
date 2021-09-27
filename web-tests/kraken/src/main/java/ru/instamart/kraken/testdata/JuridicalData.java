package ru.instamart.kraken.testdata;

import lombok.Data;

@Data
public final class JuridicalData {

    private final String juridicalName;
    private final String juridicalAddress;
    private final String inn;
    private final String kpp;
    private final String accountNumber;
    private final String bik;
    private final String bankName;
    private final String correspondentAccountNumber;
}
