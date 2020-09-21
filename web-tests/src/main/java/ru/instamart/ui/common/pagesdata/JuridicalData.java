package instamart.ui.common.pagesdata;

public class JuridicalData {

    private final String juridicalName;
    private final String juridicalAddress;
    private final String inn;
    private final String kpp;
    private final String accountNumber;
    private final String bik;
    private final String bankName;
    private final String correspondentAccountNumber;

    public JuridicalData(String juridicalName, String juridicalAddress, String inn, String kpp, String accountNumber, String bik, String bankName, String correspondentAccountNumber) {
        this.juridicalName = juridicalName;
        this.juridicalAddress = juridicalAddress;
        this.inn = inn;
        this.kpp = kpp;
        this.accountNumber = accountNumber;
        this.bik = bik;
        this.bankName = bankName;
        this.correspondentAccountNumber = correspondentAccountNumber;
    }

    public String getJuridicalName() {
        return juridicalName;
    }
    public String getJuridicalAddress() {
        return juridicalAddress;
    }
    public String getInn() {
        return inn;
    }
    public String getKpp() {
        return kpp;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public String getBik() {
        return bik;
    }
    public String getBankName() {
        return bankName;
    }
    public String getCorrespondentAccountNumber() {
        return correspondentAccountNumber;
    }
}
