package com.vivek.jpa.config;

import com.vivek.jpa.dao.ds2.UserRepository;
import org.hibernate.engine.transaction.jta.platform.internal.AtomikosJtaPlatform;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(
        basePackageClasses = UserRepository.class,
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "transactionManager")
public class DS2Config {

    @Bean(name = "dataSourceTwo")
    @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.two")
    public DataSource dataSourceTwo() {
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        return atomikosDataSourceBean;
    }

    @Bean(name = "userEntityManager")
    @DependsOn({"transactionManager"})
    public LocalContainerEntityManagerFactoryBean userEntityManager() throws Throwable {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");

        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(dataSourceTwo());
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setPackagesToScan("com.vivek.jpa.entity.ds2");
        entityManager.setPersistenceUnitName("userPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);

        return entityManager;

    }
}
