//package org.shoppingMall.config;
//
//
//import jakarta.persistence.EntityManagerFactory;
//import lombok.RequiredArgsConstructor;
//import org.shoppingMall.config.properties.DataSourceProperties;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Configuration
//@EntityScan
//@EnableConfigurationProperties(DataSourceProperties.class)
//@RequiredArgsConstructor
//@EnableJpaRepositories(
//        basePackages = {"org.shoppingMall.repository.cart",
//                "org.shoppingMall.repository.orderItem",
//                "org.shoppingMall.repository.orderTable",
//                "org.shoppingMall.repository.product",
//                "org.shoppingMall.repository.productOption",
//                "org.shoppingMall.repository.productPhoto",
//                "org.shoppingMall.repository.review",
//                "org.shoppingMall.repository.roles",
//                "org.shoppingMall.repository.userRoles",
//                "org.shoppingMall.repository.user",
//                "org.shoppingMall.repository.userDetails",
//                "org.shoppingMall.repository.userRoles"
//        },
//        entityManagerFactoryRef = "localContainerEntityManagerFactoryBean",
//        transactionManagerRef = "tm"
//)
//public class JpaConfig {
//    private final DataSourceProperties dataSourceProperties;
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        driverManagerDataSource.setUrl(dataSourceProperties.getUrl());
//        driverManagerDataSource.setUsername(dataSourceProperties.getUsername());
//        driverManagerDataSource.setPassword(dataSourceProperties.getPassword());
//        driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        return driverManagerDataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource datasource) {
//        LocalContainerEntityManagerFactoryBean lemfb = new LocalContainerEntityManagerFactoryBean();
//        lemfb.setDataSource(datasource);
//        lemfb.setPackagesToScan(
//                "org.shoppingMall.repository.cart",
//                "org.shoppingMall.repository.orderItem",
//                "org.shoppingMall.repository.orderTable",
//                "org.shoppingMall.repository.product",
//                "org.shoppingMall.repository.productOption",
//                "org.shoppingMall.repository.productPhoto",
//                "org.shoppingMall.repository.review",
//                "org.shoppingMall.repository.roles",
//                "org.shoppingMall.repository.userRoles",
//                "org.shoppingMall.repository.user",
//                "org.shoppingMall.repository.userDetails",
//                "org.shoppingMall.repository.userRoles"
//        );
//        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        lemfb.setJpaVendorAdapter(vendorAdapter);
//
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
//        properties.put("hibernate.format_sql", "true");
//        properties.put("hibernate.use_sql_comment", "true");
//
//        lemfb.setJpaPropertyMap(properties);
//
//        return lemfb;
//    }
//
//    @Bean(name = "tm")
//    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
