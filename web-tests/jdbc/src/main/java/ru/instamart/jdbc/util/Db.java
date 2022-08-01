package ru.instamart.jdbc.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.common.Crypt;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
@ToString
public enum Db {

    PG_SHADOW_CAT(
            "paas-content-demand-shadowcat",
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_CANDIDATE(
            "paas-content-operations-candidates",
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_ETA(
            "paas-content-operations-eta",
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_ORDER(
            "paas-content-operations-order-service",
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_SHIFT(
            "paas-content-operations-shifts",
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_SHIPPING_CALC(
            System.getProperty("url_paas_shippingcalc", "paas-content-operations-shippingcalc"),
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_WORKFLOW(
            "paas-content-operations-workflow",
            "statefulset.kubernetes.io/pod-name=postgresql-0",
            5432,
            "jdbc:postgresql://localhost:%s/app",
            "org.postgresql.Driver",
            Crypt.INSTANCE.decrypt("O4On6ImtTAIvvUDOsqOHDw=="),
            Crypt.INSTANCE.decrypt("DQdUNB8CjrqEUiIrAaZlCg=="),
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    PG_SHP(
            EnvironmentProperties.K8S_NAME_SHP_SPACE,
            EnvironmentProperties.K8S_LABEL_SHP_SELECTOR,
            EnvironmentProperties.DB_PG_PORT,
            Optional.ofNullable(System.getenv("CI_PIPELINE_SOURCE")).orElse("local").equals("local") ? "jdbc:postgresql://localhost:%s/shopper_staging_kraken" : EnvironmentProperties.DB_PGSQL_URL,
            "org.postgresql.Driver",
            EnvironmentProperties.DB_PGSQL_USERNAME,
            EnvironmentProperties.DB_PGSQL_PASSWORD,
            EnvironmentProperties.DEFAULT_PGSQL_POOL_SIZE
    ),
    MYSQL_STF(
            EnvironmentProperties.K8S_NAME_STF_SPACE,
            EnvironmentProperties.K8S_LABEL_STF_SELECTOR,
            EnvironmentProperties.DB_PORT,
            EnvironmentProperties.DB_URL,
            "com.mysql.cj.jdbc.Driver",
            EnvironmentProperties.DB_MYSQL_USERNAME,
            EnvironmentProperties.DB_MYSQL_PASSWORD,
            CoreProperties.DEFAULT_MYSQL_POOL_SIZE
    );

    private final String namespace;
    private final String label;
    private final int containerPort;
    private final String url;
    private final String driver;
    private final String username;
    private final String password;
    private final int poolSize;
    @Setter
    private int internalPort;
}
