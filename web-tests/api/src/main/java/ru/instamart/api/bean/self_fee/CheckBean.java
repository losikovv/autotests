package ru.instamart.api.bean.self_fee;

import com.opencsv.bean.CsvBindByName;

public class CheckBean {

    @CsvBindByName(column = "Check", required = true)
    public String check;
    @CsvBindByName(column = "Surname", required = true)
    public String surname;
    @CsvBindByName(column = "Patronymic", required = true)
    public String patronymic;
    @CsvBindByName(column = "Sum", required = true)
    public String sum;

}
