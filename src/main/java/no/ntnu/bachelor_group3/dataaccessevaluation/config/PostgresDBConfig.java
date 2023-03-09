package no.ntnu.bachelor_group3.dataaccessevaluation.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        //transactionManagerRef = "postgresTransactionManager",
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        basePackages = { "no.ntnu.bachelor_group3.dataaccessevaluation.postgresrepositories" }
)
public class PostgresDBConfig {
    @Bean(name="postgresDSProps")
    @ConfigurationProperties("postgres.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "postgresDataSource")
    @ConfigurationProperties("postgres.datasource")
    public DataSource dataSource(@Qualifier("postgresDSProps") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDataSource") DataSource postgresDataSource
    ) {
        return
                builder.dataSource(postgresDataSource)
                        .packages("no.ntnu.bachelor_group3.dataaccessevaluation.data")
                        .persistenceUnit("postgres")
                        .build();
    }

}

