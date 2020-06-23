package com.vivek.jpa.config;

import com.vivek.jpa.dao.ds1.BookRepository;
import org.flywaydb.core.Flyway;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(
        basePackageClasses = BookRepository.class,
        entityManagerFactoryRef = "bookEntityManager",
        transactionManagerRef = "transactionManager")
public class DS1Config {

    @Primary
    @Bean(name = "dataSourceOne")
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.one")
    public DataSource dataSourceOne() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        return atomikosDataSourceBean;
    }

    @Bean(name = "bookEntityManager")
    @DependsOn({"transactionManager"})
    public LocalContainerEntityManagerFactoryBean bookEntityManager() throws Throwable {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(dataSourceOne());
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setPackagesToScan("com.vivek.jpa.entity.ds1");
        entityManager.setPersistenceUnitName("bookPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);

        return entityManager;

    }
}
