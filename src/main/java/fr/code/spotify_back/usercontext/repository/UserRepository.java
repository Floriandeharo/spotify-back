package fr.code.spotify_back.usercontext.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.code.spotify_back.usercontext.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findOneByEmail(String email);
}
