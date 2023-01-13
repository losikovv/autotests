package ru.instamart.kraken.util;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import static ru.instamart.kraken.util.FileUtil.readBytes;
import static ru.instamart.kraken.util.FileUtil.readFile;

public class XlsUtil {
    @Getter
    private final String name;
    @Getter
    private final Workbook excel;

    private XlsUtil(String name, byte[] content) {
        this.name = name;
        try (InputStream inputStream = new ByteArrayInputStream(content)) {
            excel = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid XLS " + name, e);
        }
    }

    public XlsUtil(File xlsFile) {
        this(xlsFile.getAbsolutePath(), readFile(xlsFile));
    }

    public XlsUtil(byte[] content) {
        this("", content);
    }

    public XlsUtil(InputStream inputStream) {
        this(readBytes(inputStream));
    }
}
