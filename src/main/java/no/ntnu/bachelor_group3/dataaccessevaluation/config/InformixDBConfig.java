package no.ntnu.bachelor_group3.dataaccessevaluation.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        basePackages = { "no.ntnu.bachelor_group3.dataaccessevaluation.informixrepositories" }
)
public class InformixDBConfig {

    @Primary
    @Bean(name="informixDSProps")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "informixDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource informixDataSource(@Qualifier("informixDSProps") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("informixDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("no.ntnu.bachelor_group3.dataaccessevaluation.data")
                .persistenceUnit("event")
                .build();
    }

}
