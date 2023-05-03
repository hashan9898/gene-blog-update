package com.gene.base.infrastructure.configuration;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(entityManagerFactoryRef = "domainsEntityManager",
        transactionManagerRef = "domainsTransactionManager", basePackages = {"com.gene.base.infrastructure.entity"})
public class MySqlConfiguration {
}
