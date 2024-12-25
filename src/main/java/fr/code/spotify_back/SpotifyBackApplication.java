package fr.code.spotify_back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpotifyBackApplication {

	 @Value("${AUTH0_CLIENT_ID}")
     private static String auth0ClientId;
	 
	public static void main(String[] args) {
        System.out.print("authclient "+ auth0ClientId+ "------");
		SpringApplication.run(SpotifyBackApplication.class, args);
	}

}
