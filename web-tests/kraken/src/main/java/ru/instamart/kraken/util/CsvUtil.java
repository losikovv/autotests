package ru.instamart.kraken.util;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.List;
import java.nio.charset.Charset;



public class CsvUtil<T> {

    @Getter
    private final Class<T> beanClass;

    public CsvUtil(Class<T> beenClass){
        this.beanClass = beenClass;
    }

    public List<T> readByPosition(File file, Charset charset, int skipLines) throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), charset);
        final var csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(skipLines) //количество пропущенных строк
                .build();

        ColumnPositionMappingStrategy<T> ms = new ColumnPositionMappingStrategy<>();
        ms.setType(beanClass);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(beanClass)
                .withMappingStrategy(ms)
                .build();
        return csvToBean.parse();
    }

    public List<T> readByPosition(File file, Charset charset) throws FileNotFoundException {
        return readByPosition(file, charset, 0);
    }
}
