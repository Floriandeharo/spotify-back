package fr.code.spotify_back.debug;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DebugEnvironment implements CommandLineRunner {

    @Value("${postgres_url:NOT_SET}")
    private String postgresUrl;

    @Value("${postgres_db:NOT_SET}")
    private String postgresDb;

    @Value("${postgres_username:NOT_SET}")
    private String postgresUsername;

    @Value("${postgres_password:NOT_SET}")
    private String postgresPassword;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Postgres URL: " + postgresUrl);
        System.out.println("Postgres DB: " + postgresDb);
        System.out.println("Postgres Username: " + postgresUsername);
        System.out.println("Postgres Password: " + postgresPassword);
    }
}
