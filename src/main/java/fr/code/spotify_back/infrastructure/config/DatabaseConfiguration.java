package fr.code.spotify_back.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"fr.code.spotify_back.usercontext.repository", "fr.code.spotify_back.cataloguecontext.repository"})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {

}
