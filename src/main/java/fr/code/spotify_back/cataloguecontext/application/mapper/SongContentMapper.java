package fr.code.spotify_back.cataloguecontext.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.code.spotify_back.cataloguecontext.application.dto.SaveSongDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SongContentDTO;
import fr.code.spotify_back.cataloguecontext.domain.SongContent;

@Mapper(componentModel = "spring")
public interface SongContentMapper {

	@Mapping(source = "song.publicId", target = "publicId")
	SongContentDTO songContentToSongContentDTO(SongContent songContent);
	
	SongContent saveSongDTOToSong(SaveSongDTO songDTO);
}
