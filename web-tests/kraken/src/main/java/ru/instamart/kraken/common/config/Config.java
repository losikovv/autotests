package ru.instamart.kraken.common.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для полей классов конфигурационных файлов
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {

    /**
     * @return - имя конфигурационного файла к которому относится пара ключ=значение
     */
    String configName();

    /**
     * @return - название ключа
     */
    String fieldName();

    /**
     * @return - определяет значение по умолчанию, в случае если по каким либо причинам,
     * не удалось получить значение из конфигурационного файла.
     * Может задать значение даже если такой пары ключ=значение, не существует в
     * конфигурационном файле.
     *
     */
    String defaultValue();

    /**
     * @return - если переменная зашифрована, то при установке в true, декриптует на лету при разборе конфига
     */
    boolean crypted() default false;

    /**
     * @return - аргументы, которые передаются при запуске прогона
     */
    String args() default "empty";
}
