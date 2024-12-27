package fr.code.spotify_back.cataloguecontext.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.code.spotify_back.cataloguecontext.domain.Favorite;
import fr.code.spotify_back.cataloguecontext.domain.FavoriteId;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId>{

	List<Favorite> findAllByUserEmailAndSongPublicIdIn(String email, List<UUID> songPublicIds);

}
