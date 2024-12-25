package fr.code.spotify_back.cataloguecontext.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import fr.code.spotify_back.cataloguecontext.domain.Song;

public interface SongRepository extends JpaRepository <Song, Long>{

}
