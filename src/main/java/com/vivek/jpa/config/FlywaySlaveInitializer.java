package com.vivek.jpa.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@DependsOn({"dataSourceTwo"})
@Configuration
public class FlywaySlaveInitializer {
//
//
//    @Autowired
//    private DataSource dataSourceOne;
    @Autowired
    @Qualifier("dataSourceTwo")
    private DataSource dataSourceTwo;

    @PostConstruct
    public void migrateFlyway() {
//        Flyway.configure()
//                .dataSource(dataSourceOne)
//                .locations("classpath:/db/migration/ds1")
//                .load()
//                .migrate();

        Flyway.configure()
                .dataSource(dataSourceTwo)
                .locations("db/migration/ds2")
                .sqlMigrationSeparator("_")
                .validateOnMigrate(false)
                .load()
                .migrate();
    }
}
