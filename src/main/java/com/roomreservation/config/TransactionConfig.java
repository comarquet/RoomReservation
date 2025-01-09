package com.roomreservation.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Configuration class for transaction management in the room reservation system.
 * Provides configuration for JPA transactions using Spring's transaction management infrastructure.
 *
 * <p>This configuration:
 * - Sets up the JPA transaction manager as the primary transaction manager
 * - Enables declarative transaction management using @Transactional
 * - Configures transaction boundaries and rollback behavior
 *
 * @see org.springframework.transaction.annotation.EnableTransactionManagement
 * @see org.springframework.orm.jpa.JpaTransactionManager
 */
@Configuration
public class TransactionConfig {
  
  /**
   * Creates and configures the JPA transaction manager.
   * This bean is marked as @Primary to be the default transaction manager when multiple are present.
   *
   * @param entityManagerFactory The JPA entity manager factory
   * @return Configured JpaTransactionManager instance
   */
  @Primary
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
