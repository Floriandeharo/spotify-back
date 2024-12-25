package fr.code.spotify_back.cataloguecontext.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.code.spotify_back.cataloguecontext.application.dto.ReadSongInfoDTO;
import fr.code.spotify_back.cataloguecontext.application.dto.SaveSongDTO;
import fr.code.spotify_back.cataloguecontext.application.vo.SongAuthorVO;
import fr.code.spotify_back.cataloguecontext.application.vo.SongTitleVO;
import fr.code.spotify_back.cataloguecontext.domain.Song;



@Mapper(componentModel = "spring")
public interface SongMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "publicId", ignore = true)
	Song saveSongDTOToSong(SaveSongDTO saveSongDTO);
	
	@Mapping(target = "favorite", ignore = true)
	ReadSongInfoDTO songToReadSongInfoDTO(Song song);
	
	default SongTitleVO stringToSongTitleVO(String title) {
		return new SongTitleVO(title);
	}	
	
	default SongAuthorVO stringToSongAuthorVO(String author) {
		return new SongAuthorVO(author);
	}

	default String songTitleVOToString(SongTitleVO title) {
		return title.value();
	}
	default String songAuthorVOToString(SongAuthorVO author) {
		return author.value();
	}
	
}
