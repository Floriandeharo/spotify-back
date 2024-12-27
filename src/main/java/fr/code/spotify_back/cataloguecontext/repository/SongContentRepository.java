package fr.code.spotify_back.cataloguecontext.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.code.spotify_back.cataloguecontext.domain.SongContent;

public interface SongContentRepository extends JpaRepository<SongContent, Long>{

	Optional<SongContent> findOneBySongPublicId(UUID publicId);
}
