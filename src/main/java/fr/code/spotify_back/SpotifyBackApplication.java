package fr.code.spotify_back;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpotifyBackApplication {

	public static void main(String[] args) {
        
		SpringApplication.run(SpotifyBackApplication.class, args);
	}

}
